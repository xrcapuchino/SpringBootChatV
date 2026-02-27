package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.HistorialDto;
import org.example.demo.model.Curso;
import org.example.demo.model.Historial;
import org.example.demo.model.Servicio;
import org.example.demo.model.Usuario;
import org.example.demo.service.HistorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/historial")
@AllArgsConstructor
public class HistorialControler {

    private final HistorialService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialDto>> list() {
        return ResponseEntity.ok(historialService.getAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialDto> getById(@PathVariable Integer id) {
        HistorialDto dto = historialService.getDtoById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<HistorialDto>> getByCurso(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(historialService.getAllDtoByCurso(cursoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistorialDto>> getByUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(historialService.getAllDtoByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<HistorialDto> save(@RequestBody HistorialDto dto) {
        if (dto.getUsuario_idUsuario() == null) {
            return ResponseEntity.badRequest().build();
        }

        Historial entidad = Historial.builder()
                .fechapago(dto.getFechapago() != null && !dto.getFechapago().isEmpty()
                        ? LocalDate.parse(dto.getFechapago())
                        : null)
                .pago(dto.getPago())
                .usuario(Usuario.builder().idUsuario(dto.getUsuario_idUsuario()).build())
                .curso(dto.getCursoId() != null ? Curso.builder().idCurso(dto.getCursoId()).build() : null)
                .servicio(dto.getServicioId() != null ? Servicio.builder().idServicios(dto.getServicioId()).build() : null)
                .build();

        Historial saved = historialService.save(entidad);
        HistorialDto dtoRespuesta = toDto(saved);

        return ResponseEntity
                .created(URI.create("/api/historial/" + saved.getIdHistorial()))
                .body(dtoRespuesta);
    }

    private HistorialDto toDto(Historial h) {
        return HistorialDto.builder()
                .idHistorial(h.getIdHistorial())
                .fechapago(h.getFechapago() != null ? h.getFechapago().toString() : null)
                .pago(h.getPago())
                .usuario_idUsuario(h.getUsuario() != null ? h.getUsuario().getIdUsuario() : null)
                .servicioId(h.getServicio() != null ? h.getServicio().getIdServicios() : null)
                .cursoId(h.getCurso() != null ? h.getCurso().getIdCurso() : null)
                .build();
    }
}
