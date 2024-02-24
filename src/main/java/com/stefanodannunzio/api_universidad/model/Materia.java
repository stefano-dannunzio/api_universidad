package com.stefanodannunzio.api_universidad.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Materia {


    private int materiaId;
    private static int lastId = 0;
    private String nombre;
    private int anio;
    private int cuatrimestre;

    private List<Integer> correlativas;
    private int carreraId;

    public Materia() {
        this.materiaId = getNextId();
        correlativas = new ArrayList<Integer>();
    }

    public Materia(String nombre, int anio, int cuatrimestre, List<Integer> correlativas, int carreraId, int materiaId) {
        this.materiaId = materiaId;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.correlativas = correlativas;
        this.carreraId = carreraId;
    }

    // Getters & Setters

    @JsonIgnore
    public int getMateriaId() {
        return this.materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return this.anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return this.cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public List<Integer> getCorrelativas() {
        return this.correlativas;
    }

    public void setCorrelativas(List<Integer> correlativas) {
        this.correlativas = correlativas;
    }

    public int getCarreraId() {
        return this.carreraId;
    }

    public void setCarreraId(int carreraId) {
        this.carreraId = carreraId;
    }

    // Otros metodos

    private int getNextId() {
        return ++lastId;
    }

    // Agregar correlativa
    public void agregarCorrelativa(int idCorrelativa) {
        correlativas.add(idCorrelativa);
    }
    
    // Quitar correlativa
    public void quitarCorrelativa(int idCorrelativa) {
        correlativas.remove(idCorrelativa);
    }

    // Equals

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Materia)) {
            return false;
        }
        Materia materia = (Materia) o;
        return materiaId == materia.materiaId && nombre.equals(materia.nombre) && anio == materia.anio && cuatrimestre == materia.cuatrimestre && correlativas.equals(materia.correlativas) && carreraId == materia.carreraId;
    }

    // Hashcode

    public int hashCode() {
        return Objects.hash(materiaId, nombre, anio, cuatrimestre, correlativas, carreraId);
    }
}
