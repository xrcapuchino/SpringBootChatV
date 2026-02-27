package org.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMensaje;

    @Column(nullable = false, length = 500)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private Integer emisorId;

    @Column(nullable = false)
    private Integer receptorId;

    @Column(nullable = false)
    private Boolean leido;

    @PrePersist
    void prePersist() {
        if (fechaEnvio == null) fechaEnvio = LocalDateTime.now();
        if (leido == null) leido = false;
    }

    public Integer getIdMensaje() { return idMensaje; }
    public void setIdMensaje(Integer idMensaje) { this.idMensaje = idMensaje; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Integer getEmisorId() { return emisorId; }
    public void setEmisorId(Integer emisorId) { this.emisorId = emisorId; }

    public Integer getReceptorId() { return receptorId; }
    public void setReceptorId(Integer receptorId) { this.receptorId = receptorId; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}
