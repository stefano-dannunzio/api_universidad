package com.stefanodannunzio.api_universidad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

public class Alumno {

    private long id;

    private static Long lastId = 0L;
    private String nombre;
    private String apellido;
    private long dni;

    @Autowired
    private AlumnoDao alumnoDao;
    
    @Autowired
    private MateriaDao materiaDao;


    public Alumno() {
        this.id = getNextId();
    }

    public Alumno(String nombre, String apellido, long dni) {
        this.id = getNextId();
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        
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



     // Otros metodos

    private Long getNextId() {
        return ++lastId;
    }

    public boolean chequearCorrelativas(Asignatura asignatura) throws MateriaNotFoundException {
        Materia materia = asignatura.getMateria();
        List<Integer> idsCorrelativas = materia.getCorrelativas();
        List<Materia> correlativas = new ArrayList<>();
        for (Integer idCorrelativa : idsCorrelativas) {
            correlativas.add(materiaDao.findById(idCorrelativa));
        }
        for (Materia correlativa : correlativas) {
            if (alumnoDao.getEstadoAsignatura(this.getId(), correlativa) != EstadoAsignatura.APROBADA) {
                return false;
            }
        }
        return true;
    }
    

    public void cursarAsignatura(Long asignaturaId) {
        Map<Long, Asignatura> asignaturas = alumnoDao.getAsignaturas(this.getId());
        Asignatura asignatura = asignaturas.get(asignaturaId);
        if (asignatura == null) {
            throw new IllegalArgumentException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        if (asignatura.getEstado() == EstadoAsignatura.CURSADA || asignatura.getEstado() == EstadoAsignatura.APROBADA) {
            throw new IllegalArgumentException("La asignatura ya fue cursada");
        }
        //No hay necesidad de chequear correlativas para cursar
        asignatura.cursar();
        
    }

    public void aprobarAsignatura(Long asignaturaId, int nota) throws MateriaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException {
        Map<Long, Asignatura> asignaturas = alumnoDao.getAsignaturas(this.getId());
        Asignatura asignatura = asignaturas.get(asignaturaId);
        if (asignatura == null) {
            throw new IllegalArgumentException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        if (asignatura.getEstado() != EstadoAsignatura.CURSADA) {
            throw new IllegalArgumentException("La asignatura no se encuentra cursada");
        }
        if (nota < 4 || nota > 10) {
            throw new IllegalArgumentException("La nota debe ser entre 4 y 10");
        }
        if (!chequearCorrelativas(asignatura)) {
            throw new IllegalArgumentException("No se cumplieron las correlativas");
        }
        asignatura.aprobar(nota);
    }

    public void perderAsignatura(Long asignaturaId) {
        Map<Long, Asignatura> asignaturas = alumnoDao.getAsignaturas(this.getId());
        Asignatura asignatura = asignaturas.get(asignaturaId);
        if (asignatura == null) {
            throw new IllegalArgumentException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        if (asignatura.getEstado() != EstadoAsignatura.NO_CURSADA) {
            throw new IllegalArgumentException("La asignatura ya fue cursada, desaprobada o aprobada");
        }
        //No hay necesidad de chequear correlativas para perder
        asignatura.perder();
    }

    

}
