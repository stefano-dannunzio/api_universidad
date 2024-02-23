package com.stefanodannunzio.api_universidad.model;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;

import jakarta.annotation.PostConstruct;


public class Alumno {


    private String nombre;
    private String apellido;
    private int dni;
    private List<Asignatura> asignaturas;


    private AlumnoDao alumnoDao;
    private MateriaDao materiaDao;
    
    @Autowired
    public void setAlumnoDao(AlumnoDao alumnoDao) {
        this.alumnoDao = alumnoDao;
    }

    @Autowired
    public void setMateriaDao(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }


    public Alumno() {
        
    }

    public Alumno(String nombre, String apellido, int dni) {
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        
    }

    // Getters & Setters

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

    public int getDni() {
        return this.dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }



     // Otros metodos

     @PostConstruct
     public void init() {
         asignaturas = Arrays.asList(
                new Asignatura(new Materia("Matematica 1", 1, 1, Arrays.asList(), 1, 1)),
                new Asignatura(new Materia("Matematica 2", 1, 2, Arrays.asList(1), 1, 2)),
                new Asignatura(new Materia("Matematica 3", 2, 1, Arrays.asList(1, 2), 1, 3)),
                new Asignatura(new Materia("Matematica 4", 2, 2, Arrays.asList(1, 2, 3), 1, 4))

         );
     }

     public void cursarAsignatura(int id) {
         Asignatura asignatura = asignaturas.get(id);
         asignatura.cursar();         
     }

     public void aprobarAsignatura(int id, int nota) throws EstadoIncorrectoException, NotaIncorrectaException {
         Asignatura asignatura = asignaturas.get(id);
         asignatura.aprobar(nota);
     }

     public void perderAsignatura(int id) {
        Asignatura asignatura = asignaturas.get(id);
        asignatura.perder();
    }


}
