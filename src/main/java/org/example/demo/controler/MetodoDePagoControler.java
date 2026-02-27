package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.MetodoDePagoDto;
import org.example.demo.model.MetodoDePago;
import org.example.demo.model.Usuario;
import org.example.demo.service.MetodoDePagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metodoPago")
@AllArgsConstructor
public class MetodoDePagoControler {

    private final MetodoDePagoService metodoDePagoService;

    @GetMapping
    public ResponseEntity<List<MetodoDePagoDto>> lista() {
        List<MetodoDePago> metodos = metodoDePagoService.getAll();
        if (metodos == null || metodos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                metodos.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoDePagoDto> getById(@PathVariable Integer id) {
        MetodoDePago m = metodoDePagoService.getById(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(m));
    }

    @PostMapping
    public ResponseEntity<MetodoDePagoDto> save(@RequestBody MetodoDePagoDto dto) {

        MetodoDePago m = MetodoDePago.builder()
                .compania(dto.getCompania())
                .numtar(dto.getNumeroTarjeta())
                .cvv(dto.getCvv())
                .venci(dto.getVencimiento() != null && !dto.getVencimiento().isEmpty()
                        ? LocalDate.parse(dto.getVencimiento())
                        : null)
                .usuario(dto.getUsuarioId() != null
                        ? Usuario.builder().idUsuario(dto.getUsuarioId()).build()
                        : null)
                .build();

        metodoDePagoService.save(m);
        return ResponseEntity.ok(toDto(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoDePagoDto> update(@PathVariable Integer id,
                                                  @RequestBody MetodoDePagoDto dto) {

        MetodoDePago nuevo = MetodoDePago.builder()
                .compania(dto.getCompania())
                .numtar(dto.getNumeroTarjeta())
                .cvv(dto.getCvv())
                .venci(dto.getVencimiento() != null && !dto.getVencimiento().isEmpty()
                        ? LocalDate.parse(dto.getVencimiento())
                        : null)
                .usuario(dto.getUsuarioId() != null
                        ? Usuario.builder().idUsuario(dto.getUsuarioId()).build()
                        : null)
                .build();

        MetodoDePago actualizado = metodoDePagoService.update(id, nuevo);
        if (actualizado == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(toDto(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        metodoDePagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private MetodoDePagoDto toDto(MetodoDePago m) {
        return MetodoDePagoDto.builder()
                .id(m.getIdMetodoDePago())
                .compania(m.getCompania())
                .numeroTarjeta(m.getNumtar())
                .cvv(m.getCvv())
                .vencimiento(m.getVenci() != null ? m.getVenci().toString() : null)
                .usuarioId(m.getUsuario() != null ? m.getUsuario().getIdUsuario() : null)
                .build();
    }
}
