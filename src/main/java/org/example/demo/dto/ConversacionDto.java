package org.example.demo.dto;

import java.time.LocalDateTime;

public class ConversacionDto {
    private int otroId;
    private String otroNombre;
    private String ultimoMensaje;
    private LocalDateTime fechaUltimo;
    private int noLeidos;

    public ConversacionDto() {}

    public ConversacionDto(int otroId, String otroNombre, String ultimoMensaje, LocalDateTime fechaUltimo, int noLeidos) {
        this.otroId = otroId;
        this.otroNombre = otroNombre;
        this.ultimoMensaje = ultimoMensaje;
        this.fechaUltimo = fechaUltimo;
        this.noLeidos = noLeidos;
    }

    public int getOtroId() { return otroId; }
    public void setOtroId(int otroId) { this.otroId = otroId; }

    public String getOtroNombre() { return otroNombre; }
    public void setOtroNombre(String otroNombre) { this.otroNombre = otroNombre; }

    public String getUltimoMensaje() { return ultimoMensaje; }
    public void setUltimoMensaje(String ultimoMensaje) { this.ultimoMensaje = ultimoMensaje; }

    public LocalDateTime getFechaUltimo() { return fechaUltimo; }
    public void setFechaUltimo(LocalDateTime fechaUltimo) { this.fechaUltimo = fechaUltimo; }

    public int getNoLeidos() { return noLeidos; }
    public void setNoLeidos(int noLeidos) { this.noLeidos = noLeidos; }
}
