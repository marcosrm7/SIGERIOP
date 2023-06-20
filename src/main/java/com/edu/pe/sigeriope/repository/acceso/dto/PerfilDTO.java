package com.edu.pe.sigeriope.repository.acceso.dto;

import java.time.LocalDateTime;

public class PerfilDTO {

    private int perfilesId;
    private String nombre;
    private int estadoId;
    private LocalDateTime fechaReg;
    private int tiempoSesion;

    public int getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(int perfilesId) {
        this.perfilesId = perfilesId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public LocalDateTime getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(LocalDateTime fechaReg) {
        this.fechaReg = fechaReg;
    }

    public int getTiempoSesion() {
        return tiempoSesion;
    }

    public void setTiempoSesion(int tiempoSesion) {
        this.tiempoSesion = tiempoSesion;
    }
}
