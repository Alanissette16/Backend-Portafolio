package com.backend.proyecto.asesorias.services;

import com.backend.proyecto.Horarios.entities.HorarioEntity;
import com.backend.proyecto.Horarios.repositories.HorarioRepository;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AsesoriaValidationService {

    private final HorarioRepository horarioRepository;
    private final AsesoriaRepository asesoriaRepository;

    //Constructor para inyección de dependencias
    public AsesoriaValidationService(HorarioRepository horarioRepository,
            AsesoriaRepository asesoriaRepository) {
        this.horarioRepository = horarioRepository;
        this.asesoriaRepository = asesoriaRepository;
    }

    //Validar que la asesoría esté dentro del horario disponible del programador
    public void validarHorarioDisponible(Long programadorId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        //Convertir fecha a día de semana
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        HorarioEntity.DiaSemana diaSemana = convertirDiaSemana(dayOfWeek);

        //Buscar horarios activos del programador para ese día
        List<HorarioEntity> horarios = horarioRepository.findByProgramadorIdAndDiaSemanaAndActivoTrue(programadorId,
                diaSemana);

        if (horarios.isEmpty()) {
            throw new BadRequestException("El programador no tiene disponibilidad para ese día (" + diaSemana + ")");
        }

        //Verificar que el horario solicitado esté dentro de algún horario disponible
        boolean dentroDeHorario = horarios.stream()
                .anyMatch(h -> !horaInicio.isBefore(h.getHoraInicio()) &&
                        !horaFin.isAfter(h.getHoraFin()));

        if (!dentroDeHorario) {
            throw new BadRequestException("El horario solicitado no está dentro de la disponibilidad del programador");
        }
    }

    //Validar que no haya solapamiento con asesorías confirmadas
    public void validarNoSolapamiento(Long programadorId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
            Long asesoriaIdToExclude) {
        List<AsesoriaEntity> asesoriasConfirmadas = asesoriaRepository.findByProgramadorIdAndFechaAndEstado(
                programadorId, fecha, AsesoriaEntity.EstadoAsesoria.CONFIRMADA);

        for (AsesoriaEntity asesoria : asesoriasConfirmadas) {
            //Excluir la asesoría actual si estamos actualizando
            if (asesoriaIdToExclude != null && asesoria.getId().equals(asesoriaIdToExclude)) {
                continue;
            }

            //Verificar solapamiento
            // (InicioA < FinB) and (FinA > InicioB)
            boolean solapa = (horaInicio.isBefore(asesoria.getHoraFin())) &&
                    (horaFin.isAfter(asesoria.getHoraInicio()));

            if (solapa) {
                throw new BadRequestException("El horario solicitado se solapa con otra asesoría confirmada (" +
                        asesoria.getHoraInicio() + " - " + asesoria.getHoraFin() + ")");
            }
        }
    }

    private HorarioEntity.DiaSemana convertirDiaSemana(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return HorarioEntity.DiaSemana.LUNES;
            case TUESDAY:
                return HorarioEntity.DiaSemana.MARTES;
            case WEDNESDAY:
                return HorarioEntity.DiaSemana.MIERCOLES;
            case THURSDAY:
                return HorarioEntity.DiaSemana.JUEVES;
            case FRIDAY:
                return HorarioEntity.DiaSemana.VIERNES;
            case SATURDAY:
                return HorarioEntity.DiaSemana.SABADO;
            case SUNDAY:
                return HorarioEntity.DiaSemana.DOMINGO;
            default:
                throw new BadRequestException("Día inválido");
        }
    }
}
