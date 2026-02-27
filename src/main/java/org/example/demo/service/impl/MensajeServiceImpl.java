package org.example.demo.service.impl;

import org.example.demo.dto.ConversacionDto;
import org.example.demo.dto.MensajeCreateDto;
import org.example.demo.dto.MensajeDto;
import org.example.demo.service.MensajeService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    private final JdbcTemplate jdbc;

    public MensajeServiceImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public MensajeDto enviar(MensajeCreateDto dto) {
        jdbc.update(
                "INSERT INTO Mensaje(contenido, emisorId, receptorId, leido) VALUES(?,?,?,0)",
                dto.getContenido(), dto.getEmisorId(), dto.getReceptorId()
        );


        Integer id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        return jdbc.queryForObject(
                "SELECT idMensaje, emisorId, receptorId, contenido, fechaEnvio, leido FROM Mensaje WHERE idMensaje=?",
                (rs, rowNum) -> {
                    MensajeDto m = new MensajeDto();
                    m.setIdMensaje(rs.getInt("idMensaje"));
                    m.setEmisorId(rs.getInt("emisorId"));
                    m.setReceptorId(rs.getInt("receptorId"));
                    m.setContenido(rs.getString("contenido"));
                    m.setFechaEnvio(rs.getTimestamp("fechaEnvio").toString());
                    m.setLeido(rs.getBoolean("leido"));
                    return m;
                },
                id
        );
    }

    @Override
    public List<MensajeDto> conversacion(int user1, int user2) {
        return jdbc.query(
                """
                SELECT idMensaje, emisorId, receptorId, contenido, fechaEnvio, leido
                FROM Mensaje
                WHERE (emisorId=? AND receptorId=?) OR (emisorId=? AND receptorId=?)
                ORDER BY fechaEnvio ASC
                """,
                (rs, rowNum) -> {
                    MensajeDto m = new MensajeDto();
                    m.setIdMensaje(rs.getInt("idMensaje"));
                    m.setEmisorId(rs.getInt("emisorId"));
                    m.setReceptorId(rs.getInt("receptorId"));
                    m.setContenido(rs.getString("contenido"));
                    m.setFechaEnvio(rs.getTimestamp("fechaEnvio").toString());
                    m.setLeido(rs.getBoolean("leido"));
                    return m;
                },
                user1, user2, user2, user1
        );
    }

    @Override
    public List<ConversacionDto> inbox(int userId) {
        // MySQL 8+: usamos window function para sacar el ultimo mensaje por "otroId"
        return jdbc.query(
                """
                SELECT x.otroId,
                       CONCAT(u.Nom_usu,' ',u.Ape_usu) AS otroNombre,
                       x.contenido AS ultimoMensaje,
                       x.fechaEnvio AS fechaUltimo,
                       (
                         SELECT COUNT(*)
                         FROM Mensaje m2
                         WHERE m2.receptorId = ?
                           AND m2.emisorId = x.otroId
                           AND m2.leido = 0
                       ) AS noLeidos
                FROM (
                    SELECT
                        CASE WHEN m.emisorId = ? THEN m.receptorId ELSE m.emisorId END AS otroId,
                        m.contenido,
                        m.fechaEnvio,
                        ROW_NUMBER() OVER (
                            PARTITION BY CASE WHEN m.emisorId = ? THEN m.receptorId ELSE m.emisorId END
                            ORDER BY m.fechaEnvio DESC
                        ) AS rn
                    FROM Mensaje m
                    WHERE m.emisorId = ? OR m.receptorId = ?
                ) x
                JOIN Usuario u ON u.idUsuario = x.otroId
                WHERE x.rn = 1
                ORDER BY x.fechaEnvio DESC
                """,
                (rs, rowNum) -> {
                    int otroId = rs.getInt("otroId");
                    String otroNombre = rs.getString("otroNombre");
                    String ultimoMensaje = rs.getString("ultimoMensaje");
                    Timestamp ts = rs.getTimestamp("fechaUltimo");
                    LocalDateTime fechaUltimo = ts != null ? ts.toLocalDateTime() : null;
                    int noLeidos = rs.getInt("noLeidos");
                    return new ConversacionDto(otroId, otroNombre, ultimoMensaje, fechaUltimo, noLeidos);
                },
                userId, userId, userId, userId, userId
        );
    }
}
