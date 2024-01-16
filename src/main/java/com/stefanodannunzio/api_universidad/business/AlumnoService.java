package com.stefanodannunzio.api_universidad.business;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;

public interface AlumnoService {
        
        // Crear alumno
    
        Alumno crearAlumno(AlumnoDto inputData) throws IllegalArgumentException;
    
        // Modificar alumno
    
        Alumno modificarAlumno(Integer id, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException;
    
        // Eliminar alumno
    
        void eliminarAlumno(Integer id) throws AlumnoNotFoundException;

        // Cursar asignatura

        void cursarAsignatura(Integer id, Integer id_asignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        // Aprobar asignatura

        void aprobarAsignatura(Integer id, Integer id_asignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        // Perder regularidad de una asignatura

        void perderAsignatura(Integer id, Integer id_asignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;
    
        
}
