package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.AgendaDto;
import org.example.demo.model.Agenda;
import org.example.demo.model.Servicio;
import org.example.demo.model.Usuario;
import org.example.demo.service.AgendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/Saber_Share/api/agenda")
@AllArgsConstructor
public class AgendaControler {

    private final AgendaService service;

    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<AgendaDto>> getSlotsPorServicio(@PathVariable Integer idServicio) {
        return ResponseEntity.ok(service.getDtoByServicio(idServicio));
    }

    @GetMapping("/profesor/{idProfesor}")
    public ResponseEntity<List<AgendaDto>> getSlotsPorProfesor(@PathVariable Integer idProfesor) {
        return ResponseEntity.ok(service.getDtoByProfesor(idProfesor));
    }

    @PostMapping
    public ResponseEntity<?> crearSlot(@RequestBody AgendaDto dto) {

        if (dto.getServicioId() == null || dto.getProfesorId() == null ||
                dto.getFecha() == null || dto.getHora() == null) {
            return ResponseEntity.badRequest().body("servicioId, profesorId, fecha y hora son obligatorios");
        }

        // Parseo correcto (DTO String -> Entity LocalDate/LocalTime)
        final LocalDate fecha;
        final LocalTime hora;
        try {
            // esperado: "yyyy-MM-dd"
            fecha = LocalDate.parse(dto.getFecha());

            // esperado: "HH:mm:ss" (si mandas "HH:mm", tambien lo soportamos)
            String h = dto.getHora();
            hora = (h.length() == 5) ? LocalTime.parse(h + ":00") : LocalTime.parse(h);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato invalido. fecha=yyyy-MM-dd, hora=HH:mm:ss (o HH:mm)");
        }

        Agenda entidad = Agenda.builder()
                .fecha(fecha)
                .hora(hora)
                .estado(dto.getEstado() != null && !dto.getEstado().isBlank() ? dto.getEstado() : "DISPONIBLE")
                .servicio(Servicio.builder().idServicios(dto.getServicioId()).build())
                .profesor(Usuario.builder().idUsuario(dto.getProfesorId()).build())
                .alumno(dto.getAlumnoId() != null ? Usuario.builder().idUsuario(dto.getAlumnoId()).build() : null)
                .build();

        Agenda saved = service.save(entidad);

        return ResponseEntity
                .created(URI.create("/Saber_Share/api/agenda/" + saved.getIdAgenda()))
                .body(service.toDto(saved));
    }

    @PutMapping("/reservar/{idAgenda}")
    public ResponseEntity<?> reservar(@PathVariable Integer idAgenda,
                                      @RequestParam("idAlumno") Integer idAlumno) {

        Agenda upd = service.reservar(idAgenda, idAlumno);
        if (upd == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(service.toDto(upd));
    }
}
