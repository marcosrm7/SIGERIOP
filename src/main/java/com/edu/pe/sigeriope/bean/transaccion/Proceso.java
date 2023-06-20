package com.edu.pe.sigeriope.bean.transaccion;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Proceso implements Serializable  {
    private static final long serialVersionUID = 1L;
    private int id;
    private String descripcion;
    private int tipoprocesoId;
    private String tipoprocesoNombre;
    private int categoriaId;
    private String categoriaNombre;
    private int usuarioId;
    private String nombre;
}
