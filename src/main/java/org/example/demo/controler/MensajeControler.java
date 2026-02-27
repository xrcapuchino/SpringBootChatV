package org.example.demo.controler;

import org.example.demo.dto.ConversacionDto;
import org.example.demo.dto.MensajeCreateDto;
import org.example.demo.dto.MensajeDto;
import org.example.demo.service.MensajeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeControler {

    private final MensajeService service;

    public MensajeControler(MensajeService service) {
        this.service = service;
    }

    @PostMapping
    public MensajeDto enviar(@RequestBody MensajeCreateDto dto) {
        return service.enviar(dto);
    }

    // OJO: params = user1 y user2 (asi lo tienes en controller)
    @GetMapping("/conversacion")
    public List<MensajeDto> conversacion(@RequestParam int user1, @RequestParam int user2) {
        return service.conversacion(user1, user2);
    }

    // NUEVO: inbox
    @GetMapping("/inbox")
    public List<ConversacionDto> inbox(@RequestParam int userId) {
        return service.inbox(userId);
    }
}
