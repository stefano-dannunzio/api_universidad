package com.stefanodannunzio.api_universidad.model;

import java.util.List;


public class Profesor {
    

    private long id;
    private static Long lastId = 0L;
    private String nombre;
    private String apellido;
    private String titulo;


    private List<Asignatura> AsignaturasDictadas;
    
    public Profesor() {
        this.id = getNextId();
    }

    public Profesor(String nombre, String apellido, String titulo) {
        this.id = getNextId();
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
    }

    // Getters & Setters

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public List<Asignatura> getAsignaturasDictadas() {
        return this.AsignaturasDictadas;
    }

    public void setAsignaturasDictadas(List<Asignatura> AsignaturasDictadas){
        this.AsignaturasDictadas = AsignaturasDictadas;
    }

    // Otros metodos

    private Long getNextId() {
        return ++lastId;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        this.AsignaturasDictadas.add(asignatura);
    }
}
