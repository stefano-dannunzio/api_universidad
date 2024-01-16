package com.stefanodannunzio.api_universidad.model;

import java.util.ArrayList;
import java.util.List;

import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;

public class Alumno {
    private long id;
    private String nombre;
    private String apellido;
    private long dni;
    private List<Asignatura> asignaturas;

    public Alumno() {
    }

    public Alumno(long id, String nombre, String apellido, long dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        asignaturas = new ArrayList<>();
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return this.dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public List<Asignatura> getAsignaturas() {
        return this.asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

     // Otros metodos

    public void agregarAsignatura(Asignatura asignatura) {
        this.asignaturas.add(asignatura);
    }
    
    public void perderAsignatura(int materiaId) {
        Asignatura asignatura = this.asignaturas.stream().filter(a -> a.getMateria().getMateriaId() == materiaId)
                .findFirst().get();
        asignatura.perder();
    }

    public void cursarAsignatura(int materiaId) throws CorrelativasNoAprobadasException {
        Asignatura asignatura = this.asignaturas.stream().filter(a -> a.getMateria().getMateriaId() == materiaId)
                .findFirst().get();
        chequearCorrelativas(asignatura);
        asignatura.cursar();
    }

    private void chequearCorrelativas(Asignatura asignatura) throws CorrelativasNoAprobadasException {
        for (Materia correlativa : asignatura.getMateria().getCorrelativas()) {
            if (this.asignaturas.stream().filter(a -> a.getMateria().getMateriaId() == correlativa.getMateriaId())
                    .findFirst().get().getEstado() != EstadoAsignatura.APROBADA) {
                throw new CorrelativasNoAprobadasException("No se cumplen las correlativas");
            }
        }
    }

    public void aprobarAsignatura(Asignatura asignatura, Integer nota) throws CorrelativasNoAprobadasException, EstadoIncorrectoException, NotaIncorrectaException {
        chequearCorrelativas(asignatura);
        asignatura.aprobar(nota);
    }

}
