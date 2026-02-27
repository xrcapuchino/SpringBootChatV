package org.example.demo.service.impl;

import lombok.AllArgsConstructor;
import org.example.demo.model.Servicio;
import org.example.demo.repository.ServicioRepository;
import org.example.demo.service.ServicioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    @Override
    public List<Servicio> getAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio getById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public void delete(Integer id) {
        servicioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Servicio update(Integer id, Servicio servicio) {

        Servicio aux = servicioRepository.findById(id).orElse(null);
        if (aux == null) return null;

        aux.setTitSer(servicio.getTitSer());
        aux.setDescripcion(servicio.getDescripcion());
        aux.setPrecioSer(servicio.getPrecioSer());
        aux.setReqSer(servicio.getReqSer());
        aux.setFechaSer(servicio.getFechaSer());
        aux.setHora(servicio.getHora());

        // OJO: esto puede causar problemas si te mandan un usuario incompleto desde Postman.
        // Si tu servicio solo necesita cambiar el usuario por id, mejor hacemos el fix abajo (opcion B).
        aux.setUsuario(servicio.getUsuario());

        return servicioRepository.save(aux);
    }
}
