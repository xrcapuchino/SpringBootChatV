package org.example.demo.repository;

import org.example.demo.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    @Query("""
        SELECT m FROM Mensaje m
        WHERE (m.emisorId = :u1 AND m.receptorId = :u2)
           OR (m.emisorId = :u2 AND m.receptorId = :u1)
        ORDER BY m.fechaEnvio ASC
    """)
    List<Mensaje> conversacion(@Param("u1") int u1, @Param("u2") int u2);
}
