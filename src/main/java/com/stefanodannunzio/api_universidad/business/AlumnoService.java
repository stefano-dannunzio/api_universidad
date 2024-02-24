package com.stefanodannunzio.api_universidad.business;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

public interface AlumnoService {
        
        // Crear alumno
    
        Alumno crearAlumno(AlumnoDto inputData) throws IllegalArgumentException;
    
        // Modificar alumno
    
        Alumno modificarAlumno(Integer dni, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException;
    
        // Eliminar alumno
    
        void eliminarAlumno(Integer dni) throws AlumnoNotFoundException;

        //Obtener nombre de asignatura

        String getNombreAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        //Obtener nota de asignatura

        Integer getNotaAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        // Cursar asignatura

        void cursarAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        
        // Aprobar asignatura

        void aprobarAsignatura(Integer dni, Integer asignaturaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException;

        // Perder regularidad de una asignatura

        void perderAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

        
    
        
}
