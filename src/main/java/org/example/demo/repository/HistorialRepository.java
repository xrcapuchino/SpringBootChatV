package org.example.demo.repository;

import org.example.demo.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    List<Historial> findByUsuario_IdUsuario(Integer usuarioId);
    List<Historial> findByCurso_IdCurso(Integer cursoId);
}
