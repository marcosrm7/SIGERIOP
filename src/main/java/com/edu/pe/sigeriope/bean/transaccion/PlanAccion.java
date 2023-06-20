package com.edu.pe.sigeriope.bean.transaccion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PlanAccion implements Serializable {
    private static final long serialVersionUID = 1L;
    private int  id;
    private String nombre;
    private String descripcion;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaInicio;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate  fechaFin;
    private int  usuarioId;
    private int estadoId;
    private List<Integer> riesgos;
    private List<ActividadesPlan> actividades;
    private String estado;
    private String responsable;
}
