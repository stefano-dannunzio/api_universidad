package com.stefanodannunzio.api_universidad.model.dto;

public class AlumnoDto {
    private String nombre;
    private String apellido;
    private long dni;

    // Getters & Setters

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public long getDni() {
        return this.dni;
    }

    public void setDni(long dni){
        this.dni = dni;
    }


}
