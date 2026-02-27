package org.example.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo.dto.HistorialDto;
import org.example.demo.model.Historial;
import org.example.demo.repository.HistorialRepository;
import org.example.demo.service.HistorialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistorialServiceImpl implements HistorialService {

    private final HistorialRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<HistorialDto> getAllDto() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialDto getDtoById(Integer id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialDto> getAllDtoByUsuario(Integer usuarioId) {
        return repo.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialDto> getAllDtoByCurso(Integer cursoId) {
        return repo.findByCurso_IdCurso(cursoId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Historial save(Historial historial) {
        return repo.save(historial);
    }

    private HistorialDto toDto(Historial h) {
        return HistorialDto.builder()
                .idHistorial(h.getIdHistorial())
                .fechapago(h.getFechapago() != null ? h.getFechapago().toString() : null)
                .pago(h.getPago())
                .usuario_idUsuario(
                        h.getUsuario() != null ? h.getUsuario().getIdUsuario() : null
                )
                .servicioId(
                        h.getServicio() != null ? h.getServicio().getIdServicios() : null
                )
                .cursoId(
                        h.getCurso() != null ? h.getCurso().getIdCurso() : null
                )
                .build();
    }
}
