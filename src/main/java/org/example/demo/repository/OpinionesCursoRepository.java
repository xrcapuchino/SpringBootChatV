package org.example.demo.repository;

import org.example.demo.model.OpinionesCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpinionesCursoRepository extends JpaRepository<OpinionesCurso,Integer> {

    List<OpinionesCurso> findByCurso_IdCurso(Integer cursoId);
    List<OpinionesCurso> findByUsuario_IdUsuario(Integer usuarioId);

    @Query("SELECT AVG(o.calOps) FROM OpinionesCurso o WHERE o.curso.idCurso = :cursoId")
    Double promedioByCurso(@Param("cursoId") Integer cursoId);
}
