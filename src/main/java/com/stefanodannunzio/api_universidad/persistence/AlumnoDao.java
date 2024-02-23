package com.stefanodannunzio.api_universidad.persistence;

import java.util.Map;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;

public interface AlumnoDao<K, V> {

    Alumno save(Alumno a);

    Alumno update(Long idAlumno, Alumno a) throws AlumnoNotFoundException;

    void delete(Long idAlumno) throws AlumnoNotFoundException;

    Alumno findById(Long idAlumno) throws AlumnoNotFoundException;

    Map<K, V> getAsignaturas(K idAlumno);

    V getAsignaturaByMateria(K idAlumno, Materia materia);

    EstadoAsignatura getEstadoAsignatura(K idAlumno, Materia materia);
    
}
