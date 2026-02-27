package org.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;

    @Column(nullable = false)
    private java.time.LocalDate fecha;

    @Column(nullable = false)
    private java.time.LocalTime hora;

    @Column(nullable = false)
    private String estado; // DISPONIBLE / RESERVADA

    @ManyToOne
    @JoinColumn(name = "Servicio_idServicios", nullable = false)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "Profesor_idUsuario", nullable = false)
    private Usuario profesor;

    @ManyToOne
    @JoinColumn(name = "Alumno_idUsuario")
    private Usuario alumno;
}
