package com.edu.pe.sigeriope.repository.acceso.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UsuarioListaDTO {

    private int usuarioId;
    private String usuarioNombre;
    private String clave;
    private int usuarioEstadoId;
    private LocalDateTime usuarioFechaReg;
    private int perfilesId;
    private int nroSesion;
    private int sesionActiva;
    private boolean resetPassword;
    private String perfilNombre;
    private int perfilEstadoId;
    private LocalDateTime perfilFechaReg;
    private int tiempoSesion;
    private String personaNombre;
    private String personaApellido;
    private String correo;
    private String foto;

}
