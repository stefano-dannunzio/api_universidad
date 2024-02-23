package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AlumnoDaoImpl implements AlumnoDao{

    private Map<Integer, Alumno> alumnos = new HashMap<>();
    
    
    private MateriaDao materiaDao;

    @Autowired
    public void setMateriaDao(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    @Override
    public Alumno save(Alumno a) {
        alumnos.put(a.getDni(), a);
        return a;
    }

    @Override
    public Alumno update(Integer dni, Alumno a) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + dni);
        }

        alumno.setNombre(a.getNombre());
        alumno.setApellido(a.getApellido());
        alumno.setDni(a.getDni());
        alumnos.remove(alumno.getDni());
        alumnos.put(alumno.getDni(), alumno);

        return alumno;
    }

    @Override
    public void delete(Integer dni) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + dni);
        }
        alumnos.remove(dni);

    }

    @Override
    public Alumno findByDNI(Integer dni) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + dni);
        }

        return alumno;
    }

    



    
}
