package com.stefanodannunzio.api_universidad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Profesor {
    
    @Id
    private long id;
    private String nombre;
    private String apellido;
    private String titulo;

    @OneToMany
    private List<Asignatura> AsignaturasDictadas;
    
    public Profesor() {
    }

    public Profesor(long id, String nombre, String apellido, String titulo) {
        this.id = id;
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

    public void agregarAsignatura(Asignatura asignatura) {
        this.AsignaturasDictadas.add(asignatura);
    }
}
