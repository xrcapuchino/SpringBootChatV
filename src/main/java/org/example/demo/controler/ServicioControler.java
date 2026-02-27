package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.ServicioDto;
import org.example.demo.model.Servicio;
import org.example.demo.model.Usuario;
import org.example.demo.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Saber_Share/api")
@RestController
@AllArgsConstructor
public class ServicioControler {

    private final ServicioService servicioService;

    // GET /Saber_Share/api/servicio
    @GetMapping("/servicio")
    public ResponseEntity<List<ServicioDto>> lista() {
        List<Servicio> servicios = servicioService.getAll();
        if (servicios == null || servicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                servicios.stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    // GET /Saber_Share/api/servicio/{id}
    @GetMapping("/servicio/{id}")
    public ResponseEntity<ServicioDto> getById(@PathVariable Integer id) {
        Servicio s = servicioService.getById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(s));
    }

    // POST /Saber_Share/api/servicio
    @PostMapping("/servicio")
    public ResponseEntity<ServicioDto> save(@RequestBody ServicioDto dto) {

        if (dto.getUsuarioId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Servicio entidad = Servicio.builder()
                .titSer(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .precioSer(dto.getPrecio())
                .reqSer(dto.getRequisitos())
                .fechaSer(dto.getFecha() != null && !dto.getFecha().isEmpty() ? LocalDate.parse(dto.getFecha()) : null)
                .hora(dto.getHora() != null && !dto.getHora().isEmpty() ? LocalTime.parse(dto.getHora()) : null)
                .usuario(Usuario.builder().idUsuario(dto.getUsuarioId()).build())
                .build();

        Servicio saved = servicioService.save(entidad);

        return ResponseEntity
                .created(URI.create("/Saber_Share/api/servicio/" + saved.getIdServicios()))
                .body(toDto(saved));
    }

    // PUT /Saber_Share/api/servicio/{id}
    @PutMapping("/servicio/{id}")
    public ResponseEntity<ServicioDto> update(@PathVariable Integer id, @RequestBody ServicioDto dto) {

        if (dto.getUsuarioId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Servicio cambios = Servicio.builder()
                .titSer(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .precioSer(dto.getPrecio())
                .reqSer(dto.getRequisitos())
                .fechaSer(dto.getFecha() != null && !dto.getFecha().isEmpty() ? LocalDate.parse(dto.getFecha()) : null)
                .hora(dto.getHora() != null && !dto.getHora().isEmpty() ? LocalTime.parse(dto.getHora()) : null)
                .usuario(Usuario.builder().idUsuario(dto.getUsuarioId()).build())
                .build();

        Servicio updated = servicioService.update(id, cambios);
        if (updated == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(toDto(updated));
    }

    // DELETE /Saber_Share/api/servicio/{id}
    @DeleteMapping("/servicio/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ServicioDto toDto(Servicio s) {
        String nombreAutor = "Desconocido";
        if (s.getUsuario() != null) {
            nombreAutor = s.getUsuario().getNomUsu() + " " + s.getUsuario().getApeUsu();
        }

        return ServicioDto.builder()
                .servicioId(s.getIdServicios())
                .titulo(s.getTitSer())
                .descripcion(s.getDescripcion())
                .precio(s.getPrecioSer())
                .requisitos(s.getReqSer())
                .fecha(s.getFechaSer() != null ? s.getFechaSer().toString() : null)
                .hora(s.getHora() != null ? s.getHora().toString() : null)
                .usuarioId(s.getUsuario() != null ? s.getUsuario().getIdUsuario() : null)
                .nombreUsuario(nombreAutor)
                .build();
    }
}
