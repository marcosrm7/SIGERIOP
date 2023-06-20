package com.edu.pe.sigeriope.repository.acceso.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UsuarioDTO {

    //usuario_id, nombre, clave, estado_id, fecha_reg, perfil_id, nro_sesion, sesion_activa, reset_password
    private int usuarioId;
    private String usuarioNombre;
    private String clave;
    private int estadoId;
    private LocalDateTime usuarioFechaReg;
    private int perfilesId;
    private int nroSesion;
    private int sesionActiva;
    private boolean resetPassword;
    private String personaNombre;
    private String personaApellido;
    private String correo;
    private String foto;
    private int personaId;

}
