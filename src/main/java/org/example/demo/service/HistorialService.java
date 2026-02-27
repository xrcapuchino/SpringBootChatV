package org.example.demo.service;

import org.example.demo.dto.HistorialDto;
import org.example.demo.model.Historial;

import java.util.List;

public interface HistorialService {

    List<HistorialDto> getAllDto();

    HistorialDto getDtoById(Integer id);

    List<HistorialDto> getAllDtoByUsuario(Integer usuarioId);


    List<HistorialDto> getAllDtoByCurso(Integer cursoId);


    // ÚNICO save
    Historial save(Historial historial);
}
