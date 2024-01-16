package com.stefanodannunzio.api_universidad.persistence;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;

public interface AlumnoDao {

    Alumno save(Alumno a);

    Alumno update(Integer idAlumno, Alumno a) throws AlumnoNotFoundException;

    void delete(Integer idAlumno) throws AlumnoNotFoundException;

    Alumno findById(Integer idAlumno) throws AlumnoNotFoundException;
    
}
