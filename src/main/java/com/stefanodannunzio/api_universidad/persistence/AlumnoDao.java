package com.stefanodannunzio.api_universidad.persistence;

import java.util.Map;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

public interface AlumnoDao {

    Alumno save(Alumno a);

    Alumno update(Integer dni, Alumno a) throws AlumnoNotFoundException;

    void delete(Integer dni) throws AlumnoNotFoundException;

    Alumno findByDNI(Integer dni) throws AlumnoNotFoundException;

    
    
}
