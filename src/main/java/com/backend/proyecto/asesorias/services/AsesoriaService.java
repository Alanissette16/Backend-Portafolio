package com.backend.proyecto.asesorias.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.mappers.AsesoriaMapper;
import com.backend.proyecto.asesorias.models.Asesoria;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import com.backend.proyecto.utils.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsesoriaService {

    private final AsesoriaRepository asesoriaRepo;
    private final AsesoriaMapper asesoriaMapper;
    private final EmailService emailService; 

    // Retorna con las asesorias registradas
    public Page<Asesoria> getAllAsesorias(Pageable pageable) {
        return asesoriaRepo.findAll(pageable).map(asesoriaMapper::toModel);
    }
    // Busca asesoria por id unico
    public Asesoria getAsesoriaById(Long id) {
        AsesoriaEntity entity = asesoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asesoria no encontrada con id: " + id));
        return asesoriaMapper.toModel(entity);
    }
    // Retorna asesorias por programador
    public Page<Asesoria> getAsesoriasByProgramador(String programadorId, Pageable pageable){
        return asesoriaRepo.findByProgramadorId(programadorId, pageable).map(asesoriaMapper::toModel);
    }
    // Retorna asesorias por solicitante correo
    public Page<Asesoria> getAsesoriasBySolicitante(String email, Pageable pageable){
        return asesoriaRepo.findBySolicitanteEmail(email, pageable).map(asesoriaMapper::toModel);
    }
    // Filtra asesorias segun su estado
    public Page<Asesoria> getAsesoriasByStatus(AsesoriaEntity.Status status, Pageable pageable){
        return asesoriaRepo.findByStatus(status, pageable).map(asesoriaMapper::toModel);
    }

    @Transactional
    public Asesoria createAsesoria(Asesoria asesoriaModel){

        asesoriaModel.setStatus(Asesoria.Status.pending);

        AsesoriaEntity entity = asesoriaMapper.toEntity(asesoriaModel);
        AsesoriaEntity savedAsesoria = asesoriaRepo.save(entity);

        //Notifica al programador sobre nueva solicitud de asesoria
        try{
            emailService.sendAsesoriaNotificationToProgramador(
                savedAsesoria.getProgramadorEmail(),
                savedAsesoria.getProgramadorName(),
                savedAsesoria.getSolicitanteName(),
                //savedAsesoria.getSolicitanteEmail(),
                savedAsesoria.getDate(),
                savedAsesoria.getTime(),
                savedAsesoria.getNota()
            );
        }catch(Exception e){
            log.error("Error al enviar email al programador: {}",e.getMessage());
        }
        // Enviar email de confirmacion al solicitante
        try{
            emailService.sendAsesoriaConfirmationToSolicitante(
                savedAsesoria.getSolicitanteEmail(),
                savedAsesoria.getSolicitanteName(),
                savedAsesoria.getProgramadorName(),
                savedAsesoria.getDate(),
                savedAsesoria.getTime()
                //savedAsesoria.getNota()
            );
        } catch(Exception e){
            log.error("Error al enviar email al solicitante: {}",e.getMessage());
        }
        return asesoriaMapper.toModel(savedAsesoria);
    }
    // Valida que el usuario tenga permisos para gestionar asesorias
    private void validarPropiedad(AsesoriaEntity asesoria, String appUserUid){ //appUserUid ver si se cambia IMPORTANTE
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Role_ADMIN"));
        if(isAdmin || (asesoria.getProgramadorId() != null && asesoria.getProgramadorId().equals(appUserUid))){
            return;
        }
        throw new AccessDeniedException("No tienes permisos para gestionar esta asesoria");
    }
    // Actualiza el estado de una asesoria y notifica al solicitante sobre el cambio
    @Transactional
    public Asesoria updateAsesoriaStatus(Long id, Asesoria.Status status, String solicitante){
        AsesoriaEntity entity = asesoriaRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Asesoria no encontrada con id: " + id));
        // Verifica permisos
        validarPropiedad(entity, solicitante);
        // Actualiza estado
        entity.setStatus(AsesoriaEntity.Status.valueOf(status.name()));
        AsesoriaEntity updatedAsesoria = asesoriaRepo.save(entity);
        // Notifica al solicitante sobre el cambio de estado
        try{
            emailService.sendAsesoriaStatusUpdate(
                updatedAsesoria.getSolicitanteEmail(),
                updatedAsesoria.getSolicitanteName(),
                updatedAsesoria.getProgramadorName(),
                status.name().toLowerCase()
            );
        }catch(Exception e){
            log.error("Error al enviar email de actualización de estado: {}", e.getMessage());
        }
        return asesoriaMapper.toModel(updatedAsesoria);
    }
    // Aprueba solicitud
    @Transactional
    public Asesoria approveAsesoria(Long id, String solicitante){ // VER SI SE CAMBIA SOLICITANTE IMPORTANTE
        return updateAsesoriaStatus(id, Asesoria.Status.approved, solicitante);
    }
    // Rechaza solicitud
    @Transactional
    public Asesoria rejectAsesoria(Long id, String solicitante){
        return updateAsesoriaStatus(id, Asesoria.Status.rejected, solicitante);
    }
    //Elimina una solicitud de asesoria
    @Transactional
    public void deleteAsesoria(Long id, String solicitante){
        AsesoriaEntity entity = asesoriaRepo.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Asesoria no encontrada con id: " + id));
        // Validar permisos para eliminar
        validarPropiedad(entity, solicitante);
        asesoriaRepo.deleteById(id);
    }
}
