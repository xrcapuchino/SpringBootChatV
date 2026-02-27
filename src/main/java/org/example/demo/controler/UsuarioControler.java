package org.example.demo.controler;

import lombok.AllArgsConstructor;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;
import org.example.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UsuarioControler {

    private final UsuarioService usuarioService;

    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioDto>> lista(
            @RequestParam(name = "user", defaultValue = "", required = false) String user,
            @RequestParam(name = "correo", defaultValue = "", required = false) String correo
    ) {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var stream = usuarios.stream();
        if (user != null && !user.isEmpty()) {
            stream = stream.filter(u -> user.equals(u.getUsuUsu()));
        }
        if (correo != null && !correo.isEmpty()) {
            stream = stream.filter(u -> correo.equals(u.getCorreoUsu()));
        }

        return ResponseEntity.ok(stream.map(this::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Integer id) {
        Usuario u = usuarioService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(u));
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioDto> save(@RequestBody UsuarioDto dto) {
        Rol rolPorDefecto = Rol.builder()
                .idRol(1)
                .nombre("USUARIO")
                .build();

        Usuario u = Usuario.builder()
                .usuUsu(dto.getUser())
                .nomUsu(dto.getNombre())
                .apeUsu(dto.getApellido())
                .correoUsu(dto.getCorreo())
                .contraUsu(dto.getPassword())
                .telUsu(dto.getTelefono())
                .rol(rolPorDefecto)
                .build();

        usuarioService.save(u);
        return ResponseEntity.ok(toDto(u));
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable Integer id, @RequestBody UsuarioDto dto) {
        Usuario up = usuarioService.update(id, Usuario.builder()
                .usuUsu(dto.getUser())
                .nomUsu(dto.getNombre())
                .apeUsu(dto.getApellido())
                .correoUsu(dto.getCorreo())
                .contraUsu(dto.getPassword())
                .telUsu(dto.getTelefono())
                .build());

        if (up == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(up));
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDto toDto(Usuario u) {
        String nombreRol = "SIN_ROL";
        if (u.getRol() != null && u.getRol().getNombre() != null) {
            nombreRol = u.getRol().getNombre();
        }
        return UsuarioDto.builder()
                .id(u.getIdUsuario())
                .user(u.getUsuUsu())
                .nombre(u.getNomUsu())
                .apellido(u.getApeUsu())
                .correo(u.getCorreoUsu())
                .password(u.getContraUsu())
                .telefono(u.getTelUsu())
                .rol(nombreRol)
                .build();
    }
}
