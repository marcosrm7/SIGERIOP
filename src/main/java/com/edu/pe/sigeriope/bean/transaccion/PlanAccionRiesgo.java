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
public class PlanAccionRiesgo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int planaccionId;
    private int riesgoId;

}
