package com.stefanodannunzio.api_universidad.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


public class Carrera {
    private String nombre;

    private int id;
    private static int lastId = 0;
    private int id_departamento;
    private int cuatrimestres;

    private List<Materia> materias;

    public Carrera() {
        this.id = getNextId();
        materias = new ArrayList<>();
    }

    public Carrera(String nombre, int id, int id_departamento, int cuatrimestres) {
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

    @JsonIgnore
    public int getId() {
        return this.id;
    }

    public void setId(int id){
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

    private int getNextId() {
        return ++lastId;
    }

    public void agregarMateria(Materia materia) {
        materias.add(materia);
    }

    public void eliminarMateria(Materia materia) {
        materias.remove(materia);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Carrera other = (Carrera) obj;
        return id == other.id;

    }

    

}
