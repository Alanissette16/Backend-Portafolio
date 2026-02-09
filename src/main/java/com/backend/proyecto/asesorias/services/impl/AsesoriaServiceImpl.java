package com.backend.proyecto.asesorias.services.impl;

import com.backend.proyecto.asesorias.dtos.CreateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.UpdateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.mappers.AsesoriaMapper;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.asesorias.services.AsesoriaService;
import com.backend.proyecto.asesorias.services.AsesoriaValidationService;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.notifications.services.NotificationService;
import com.backend.proyecto.exceptions.BadRequestException;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import com.backend.proyecto.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final ProgramadorRepository programadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsesoriaMapper asesoriaMapper;
    private final AsesoriaValidationService validationService;
    private final NotificationService notificationService;

    //Constructor para inyección de dependencias
    public AsesoriaServiceImpl(AsesoriaRepository asesoriaRepository,
            ProgramadorRepository programadorRepository,
            UsuarioRepository usuarioRepository,
            AsesoriaMapper asesoriaMapper,
            AsesoriaValidationService validationService,
            NotificationService notificationService) {
        this.asesoriaRepository = asesoriaRepository;
        this.programadorRepository = programadorRepository;
        this.usuarioRepository = usuarioRepository;
        this.asesoriaMapper = asesoriaMapper;
        this.validationService = validationService;
        this.notificationService = notificationService;
    }

    @Override
    public AsesoriaResponseDto solicitarAsesoria(CreateAsesoriaRequestDto dto, Long usuarioExternoId) {
        //Validar que el programador existe
        //Validar que el programador existe y obtener su perfil usando el ID de Usuario
        ProgramadorPerfilEntity programador = programadorRepository.findByUsuarioId(dto.getProgramadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "usuario_id", dto.getProgramadorId()));

        //Validar que el usuario externo existe
        UsuarioEntity usuarioExterno = usuarioRepository.findById(usuarioExternoId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioExternoId));

        //Validar que el usuario sea EXTERNAL (aunque el security ya lo hace, doble
        //check)
        if (usuarioExterno.getRole() != UsuarioEntity.RolUsuario.EXTERNAL) {
            throw new BadRequestException("Solo usuarios externos pueden solicitar asesorías");
        }

        //1. Validar horario disponible
        validationService.validarHorarioDisponible(programador.getId(), dto.getFecha(), dto.getHoraInicio(),
                dto.getHoraFin());

        //2. Validar solapamiento (con estado CONFIRMADA) - Las pendientes no bloquean
        validationService.validarNoSolapamiento(programador.getId(), dto.getFecha(), dto.getHoraInicio(),
                dto.getHoraFin(), null);

        //Crear entidad
        AsesoriaEntity entity = asesoriaMapper.toEntity(dto, programador, usuarioExterno);
        AsesoriaEntity savedEntity = asesoriaRepository.save(entity);

        //Notificar al programador
        notificationService.sendAsesoriaCreada(savedEntity);

        return asesoriaMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public AsesoriaResponseDto obtenerAsesoriaPorId(Long id) {
        AsesoriaEntity entity = asesoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asesoría", "id", id));
        return asesoriaMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsesoriaResponseDto> obtenerMisAsesorias(Long usuarioExternoId) {
        return asesoriaRepository.findByUsuarioExternoId(usuarioExternoId).stream()
                .map(asesoriaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsesoriaResponseDto> obtenerAsesoriasDeProgramador(Long programadorId) {
        return asesoriaRepository.findByProgramadorId(programadorId).stream()
                .map(asesoriaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AsesoriaResponseDto actualizarEstadoAsesoria(Long id, UpdateAsesoriaRequestDto dto, Long usuarioId) {
        AsesoriaEntity entity = asesoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asesoría", "id", id));

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario", "id", usuarioId);
        }

        //Validar que sea el programador dueño de la asesoría
        if (!entity.getProgramador().getUsuario().getId().equals(usuarioId)) {
            throw new UnauthorizedException("No tienes permiso para gestionar esta asesoría");
        }

        AsesoriaEntity.EstadoAsesoria nuevoEstado = dto.getEstado();

        if (nuevoEstado != null) {
            //Si intenta confirmar, volver a validar solapamiento (por si otra se confirmó
            //mientras tanto)
            if (nuevoEstado == AsesoriaEntity.EstadoAsesoria.CONFIRMADA) {
                validationService.validarNoSolapamiento(
                        entity.getProgramador().getId(),
                        entity.getFecha(),
                        entity.getHoraInicio(),
                        entity.getHoraFin(),
                        entity.getId());

                //Notificar confirmación
                notificationService.sendAsesoriaConfirmada(entity);
            } else if (nuevoEstado == AsesoriaEntity.EstadoAsesoria.RECHAZADA) {
                //Notificar rechazo
                notificationService.sendAsesoriaRechazada(entity);
            }

            entity.setEstado(nuevoEstado);
            entity.setFechaRespuesta(LocalDateTime.now());
        }

        if (dto.getNotasAdicionales() != null) {
            entity.setNotasAdicionales(dto.getNotasAdicionales());
        }

        if (dto.getFecha() != null)
            entity.setFecha(dto.getFecha());
        if (dto.getHoraInicio() != null)
            entity.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFin() != null)
            entity.setHoraFin(dto.getHoraFin());

        AsesoriaEntity updatedEntity = asesoriaRepository.save(entity);
        return asesoriaMapper.toResponseDto(updatedEntity);
    }

    @Override
    public void cancelarAsesoria(Long id, Long usuarioExternoId) {
        AsesoriaEntity entity = asesoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asesoría", "id", id));

        if (!entity.getUsuarioExterno().getId().equals(usuarioExternoId)) {
            throw new UnauthorizedException("No tienes permiso para cancelar esta asesoría");
        }

        if (entity.getEstado() != AsesoriaEntity.EstadoAsesoria.PENDIENTE &&
                entity.getEstado() != AsesoriaEntity.EstadoAsesoria.CONFIRMADA) {
            throw new BadRequestException("No se puede cancelar una asesoría en estado " + entity.getEstado());
        }

        entity.setEstado(AsesoriaEntity.EstadoAsesoria.CANCELADA);
        asesoriaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsesoriaResponseDto> obtenerTodasLasAsesorias() {
        return asesoriaRepository.findAll().stream()
                .map(asesoriaMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
