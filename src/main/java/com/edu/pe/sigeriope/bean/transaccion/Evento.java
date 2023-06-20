package com.edu.pe.sigeriope.bean.transaccion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Evento  implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String descripcion;
    private String generaPerdida;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime fechaInicioEvento;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime fechaDescubrimientoEvento;
    private int tipoeventoId;
    private BigDecimal montoPerdida;
    private int criticidadId;
    private int usuarioId;
    private String responsable;
    private int procesoId;
    private String lugar;
    private String criticidad;
    private Integer eventoTipoCategoriaId;
}
