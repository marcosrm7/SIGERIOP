package com.edu.pe.sigeriope.bean.acceso;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Responsable implements Serializable {
    private static final long serialVersionUID = 1L;
    private  int id;
    private String nombreCompleto;
}
