package com.stefanodannunzio.api_universidad.model;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;
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





    public Alumno() {
        asignaturas = Arrays.asList(
                new Asignatura(new Materia("Matematica 1", 1, 1, Arrays.asList(), 1, 1)),
                new Asignatura(new Materia("Matematica 2", 1, 2, Arrays.asList(1), 1, 2)),
                new Asignatura(new Materia("Matematica 3", 2, 1, Arrays.asList(1, 2), 1, 3)),
                new Asignatura(new Materia("Matematica 4", 2, 2, Arrays.asList(1, 2, 3), 1, 4))

         );
        
    }

    public Alumno(String nombre, String apellido, int dni) {
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;


        asignaturas = Arrays.asList(
                new Asignatura(new Materia("Matematica 1", 1, 1, Arrays.asList(), 1, 1)),
                new Asignatura(new Materia("Matematica 2", 1, 2, Arrays.asList(1), 1, 2)),
                new Asignatura(new Materia("Matematica 3", 2, 1, Arrays.asList(1, 2), 1, 3)),
                new Asignatura(new Materia("Matematica 4", 2, 2, Arrays.asList(1, 2, 3), 1, 4))

        );
        
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

    public List<Asignatura> getAsignaturas() {
        return this.asignaturas;
    }

    public Asignatura getAsignatura(int id) {
        return asignaturas.get(id);
    }



     // Otros metodos


     public void cursarAsignatura(int id) {
         Asignatura asignatura = asignaturas.get(id);
         asignatura.cursar();         
     }


     public void aprobarAsignatura(int id, int nota) throws EstadoIncorrectoException, NotaIncorrectaException {
        if (nota < 4 || nota > 10) throw new NotaIncorrectaException("La nota ingresada no es válida");
         Asignatura asignatura = asignaturas.get(id);
         asignatura.aprobar(nota);
        
     }

     public void perderAsignatura(int id) {
        Asignatura asignatura = asignaturas.get(id);
        asignatura.perder();
    }

    public String getNombreAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        if (asignaturaId < 0 || asignaturaId > 3){
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        return asignaturas.get(asignaturaId).getNombreMateria();
    }

    public Integer getNotaAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        if (asignaturaId < 0 || asignaturaId > 3){
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        return asignaturas.get(asignaturaId).getNota().orElse(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Alumno other))
            return false;
        return dni == other.dni;
    }
}
