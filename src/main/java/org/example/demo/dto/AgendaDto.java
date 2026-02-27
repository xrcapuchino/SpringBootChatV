package org.example.demo.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDto {
    private Integer idAgenda;
    private String fecha;      // "yyyy-MM-dd"
    private String hora;       // "HH:mm:ss"
    private String estado;     // "DISPONIBLE" / "RESERVADO"
    private Integer servicioId;
    private Integer profesorId;
    private Integer alumnoId;
    private String tituloServicio;
    private String nombreAlumno;
}
