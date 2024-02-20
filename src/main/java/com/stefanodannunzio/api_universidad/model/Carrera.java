package com.stefanodannunzio.api_universidad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera {
    private String nombre;
    @Id
    private long id;
    private int id_departamento;
    private int cuatrimestres;
    @OneToMany
    private List<Materia> materias;

    public Carrera() {
        materias = new ArrayList<>();
    }

    public Carrera(String nombre, long id, int id_departamento, int cuatrimestres) {
        this.nombre = nombre;
        this.id = id;
        this.id_departamento = id_departamento;
        this.cuatrimestres = cuatrimestres;
        materias = new ArrayList<>();
    }

    // Getters & Setters

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getDepartamentoId() {
        return this.id_departamento;
    }

    public void setDepartamentoId(int id_departamento){
        this.id_departamento = id_departamento;
    }

    public int getCuatrimestres() {
        return this.cuatrimestres;
    }

    public void setCuatrimestres(int cuatrimestres){
        this.cuatrimestres = cuatrimestres;
    }

    public List<Materia> getMaterias() {
        return this.materias;
    }

    public void setMaterias(List<Materia> materias){
        this.materias = materias;
    }

    // Otros metodos

    public void agregarMateria(Materia materia) {
        materias.add(materia);
    }

    public void eliminarMateria(Materia materia) {
        materias.remove(materia);
    }

    

}
