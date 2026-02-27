package org.example.demo.repository;

import org.example.demo.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    List<Agenda> findByServicio_IdServicios(Integer idServicio);

    List<Agenda> findByProfesor_IdUsuario(Integer idProfesor);

    List<Agenda> findByAlumno_IdUsuario(Integer idAlumno);
}
