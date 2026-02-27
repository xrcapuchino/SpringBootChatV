package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.OpinionesCursoDto;
import org.example.demo.model.Curso;
import org.example.demo.model.OpinionesCurso;
import org.example.demo.model.Usuario;
import org.example.demo.service.OpinionesCursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class OpinionesCursoControler {

    private final OpinionesCursoService service;

    @GetMapping("/opiniones_curso")
    public ResponseEntity<List<OpinionesCursoDto>> lista() {
        List<OpinionesCurso> opiniones = service.getAll();
        if (opiniones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                opiniones.stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("/opiniones_curso/{id}")
    public ResponseEntity<OpinionesCursoDto> getById(@PathVariable Integer id) {
        OpinionesCurso opinion = service.getById(id);
        if (opinion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(opinion));
    }

    @GetMapping("/opiniones_curso/curso/{cursoId}")
    public ResponseEntity<List<OpinionesCursoDto>> getByCurso(@PathVariable Integer cursoId) {
        List<OpinionesCurso> opiniones = service.findByCurso(cursoId);
        if (opiniones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                opiniones.stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    @PostMapping("/opiniones_curso")
    public ResponseEntity<OpinionesCursoDto> save(@RequestBody OpinionesCursoDto dto) {
        if (dto.getUsuarioId() == null || dto.getCursoId() == null) {
            return ResponseEntity.badRequest().build();
        }

        OpinionesCurso entidad = toEntity(dto);
        OpinionesCurso saved = service.save(entidad);

        return ResponseEntity
                .created(URI.create("/api/opiniones_curso/" + saved.getIdOpiniones()))
                .body(toDto(saved));
    }

    @PutMapping("/opiniones_curso/{id}")
    public ResponseEntity<OpinionesCursoDto> update(@PathVariable Integer id, @RequestBody OpinionesCursoDto dto) {
        if (dto.getUsuarioId() == null || dto.getCursoId() == null) {
            return ResponseEntity.badRequest().build();
        }

        OpinionesCurso cambios = toEntity(dto);
        OpinionesCurso updated = service.update(id, cambios);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/opiniones_curso/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private OpinionesCursoDto toDto(OpinionesCurso o) {
        return OpinionesCursoDto.builder()
                .id(o.getIdOpiniones())
                .comentario(o.getComentOps())
                .calificacion(o.getCalOps())
                .usuarioId(o.getUsuario() != null ? o.getUsuario().getIdUsuario() : null)
                .cursoId(o.getCurso() != null ? o.getCurso().getIdCurso() : null)
                .build();
    }

    private OpinionesCurso toEntity(OpinionesCursoDto dto) {
        return OpinionesCurso.builder()
                .comentOps(dto.getComentario())
                .calOps(dto.getCalificacion())
                .usuario(Usuario.builder().idUsuario(dto.getUsuarioId()).build())
                .curso(Curso.builder().idCurso(dto.getCursoId()).build())
                .build();
    }
}
