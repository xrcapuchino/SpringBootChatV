package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.OpinionServicioDto;
import org.example.demo.model.OpinionServicio;
import org.example.demo.model.Servicio;
import org.example.demo.model.Usuario;
import org.example.demo.service.OpinionServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class OpinionServicioControler {

    private final OpinionServicioService service;

    @GetMapping("/opinion_servicio")
    public ResponseEntity<List<OpinionServicioDto>> lista() {
        List<OpinionServicio> opiniones = service.getAll();
        if (opiniones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                opiniones.stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("/opinion_servicio/{id}")
    public ResponseEntity<OpinionServicioDto> getById(@PathVariable Integer id) {
        OpinionServicio opinion = service.getById(id);
        if (opinion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(opinion));
    }

    @GetMapping("/opinion_servicio/servicio/{servicioId}")
    public ResponseEntity<List<OpinionServicioDto>> getByServicio(@PathVariable Integer servicioId) {
        List<OpinionServicio> opiniones = service.findByServicio(servicioId);
        if (opiniones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                opiniones.stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    @PostMapping("/opinion_servicio")
    public ResponseEntity<OpinionServicioDto> save(@RequestBody OpinionServicioDto dto) {
        if (dto.getUsuarioId() == null || dto.getServicioId() == null) {
            return ResponseEntity.badRequest().build();
        }

        OpinionServicio entidad = toEntity(dto);
        OpinionServicio saved = service.save(entidad);

        return ResponseEntity
                .created(URI.create("/api/opinion_servicio/" + saved.getIdOpiniones()))
                .body(toDto(saved));
    }

    @PutMapping("/opinion_servicio/{id}")
    public ResponseEntity<OpinionServicioDto> update(@PathVariable Integer id, @RequestBody OpinionServicioDto dto) {
        if (dto.getUsuarioId() == null || dto.getServicioId() == null) {
            return ResponseEntity.badRequest().build();
        }

        OpinionServicio cambios = toEntity(dto);
        OpinionServicio updated = service.update(id, cambios);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/opinion_servicio/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private OpinionServicioDto toDto(OpinionServicio o) {
        return OpinionServicioDto.builder()
                .id(o.getIdOpiniones())
                .comentario(o.getComentOps())
                .calificacion(o.getCalOps())
                .usuarioId(o.getUsuario() != null ? o.getUsuario().getIdUsuario() : null)
                .servicioId(o.getServicio() != null ? o.getServicio().getIdServicios() : null)
                .build();
    }

    private OpinionServicio toEntity(OpinionServicioDto dto) {
        return OpinionServicio.builder()
                .comentOps(dto.getComentario())
                .calOps(dto.getCalificacion())
                .usuario(Usuario.builder().idUsuario(dto.getUsuarioId()).build())
                .servicio(Servicio.builder().idServicios(dto.getServicioId()).build())
                .build();
    }
}
