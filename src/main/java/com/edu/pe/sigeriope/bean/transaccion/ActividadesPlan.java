package com.edu.pe.sigeriope.bean.transaccion;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ActividadesPlan implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String descripcion;
    private int planaccionId;
    private int estadoId;
    private String estado;
}
