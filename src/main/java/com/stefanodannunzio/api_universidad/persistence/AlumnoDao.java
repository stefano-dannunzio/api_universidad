package com.stefanodannunzio.api_universidad.persistence;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;

public interface AlumnoDao {

    Alumno save(Alumno a);

    Alumno update(Long idAlumno, Alumno a) throws AlumnoNotFoundException;

    void delete(Long idAlumno) throws AlumnoNotFoundException;

    Alumno findById(Long idAlumno) throws AlumnoNotFoundException;
    
}
