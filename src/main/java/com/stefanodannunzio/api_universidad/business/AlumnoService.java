package com.stefanodannunzio.api_universidad.business;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
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

        void cursarAsignatura(Integer id, Integer materiaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException;

        // Aprobar asignatura

        void aprobarAsignatura(Integer id, Integer materiaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException, CorrelativasNoAprobadasException;

        // Perder regularidad de una asignatura

        void perderAsignatura(Integer id, Integer materiaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;
    
        
}
