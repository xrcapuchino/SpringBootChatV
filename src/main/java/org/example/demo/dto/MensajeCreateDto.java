package org.example.demo.dto;

public class MensajeCreateDto {
    private int emisorId;
    private int receptorId;
    private String contenido;

    public MensajeCreateDto() {}

    public MensajeCreateDto(int emisorId, int receptorId, String contenido) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
    }

    public int getEmisorId() { return emisorId; }
    public void setEmisorId(int emisorId) { this.emisorId = emisorId; }

    public int getReceptorId() { return receptorId; }
    public void setReceptorId(int receptorId) { this.receptorId = receptorId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
