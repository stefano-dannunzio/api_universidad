package com.stefanodannunzio.api_universidad.model;

import java.util.Optional;

import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;

public class Asignatura {
    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;
    private Profesor profesor;


    public Asignatura() {
        
    }

    public Asignatura(Materia materia) {
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
        this.nota = null;
    }

    //Getters & Setters

    public Optional<Integer> getNota() {
        return Optional.ofNullable(this.nota);
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getNombreMateria() {
        return this.materia.getNombre();
    }

    public void setNombreMateria(String nombre) {
        this.materia.setNombre(nombre);
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public EstadoAsignatura getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    // Otros metodos

    public void perder() {
        this.estado = EstadoAsignatura.DESAPROBADA;
    }

    public void cursar() {
        this.estado = EstadoAsignatura.CURSADA;
    }

    public void aprobar(Integer nota) throws EstadoIncorrectoException, NotaIncorrectaException {
        if (this.estado != EstadoAsignatura.CURSADA) {
            throw new EstadoIncorrectoException("La asignatura no se encuentra cursada");
        }
        if (nota < 4 || nota > 10) {
            throw new NotaIncorrectaException("La nota debe ser entre 4 y 10");
        }
        this.estado = EstadoAsignatura.APROBADA;
        this.nota = nota;
    } 

}


