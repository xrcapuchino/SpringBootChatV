package org.example.demo.service;

import org.example.demo.dto.ConversacionDto;
import org.example.demo.dto.MensajeCreateDto;
import org.example.demo.dto.MensajeDto;

import java.util.List;

public interface MensajeService {
    MensajeDto enviar(MensajeCreateDto dto);
    List<MensajeDto> conversacion(int user1, int user2);

    List<ConversacionDto> inbox(int userId);
}
