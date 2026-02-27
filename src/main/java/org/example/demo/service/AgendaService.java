package org.example.demo.service;

import org.example.demo.dto.AgendaDto;
import org.example.demo.model.Agenda;

import java.util.List;

public interface AgendaService {

    List<AgendaDto> getDtoByServicio(Integer servicioId);

    List<AgendaDto> getDtoByProfesor(Integer profesorId);

    Agenda save(Agenda agenda);

    Agenda reservar(Integer idAgenda, Integer idAlumno);

    AgendaDto toDto(Agenda a);
}
