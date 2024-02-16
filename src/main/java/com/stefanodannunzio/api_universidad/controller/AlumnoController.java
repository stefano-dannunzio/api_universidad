package com.stefanodannunzio.api_universidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stefanodannunzio.api_universidad.business.AlumnoService;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;

@RestController
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    //Crear alumno
    @PostMapping
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        return alumnoService.crearAlumno(alumnoDto);
    }

    //Modificar alumno
    @PutMapping("/{idAlumno}")
    public Alumno modificarAlumno(@PathVariable int idAlumno, @RequestBody AlumnoDto alumnoDto) throws IllegalArgumentException, AlumnoNotFoundException {
        return alumnoService.modificarAlumno(idAlumno, alumnoDto);
    }

    //Eliminar alumno
    @DeleteMapping("/{idAlumno}")
    public void eliminarAlumno(@PathVariable int idAlumno) throws AlumnoNotFoundException {
        alumnoService.eliminarAlumno(idAlumno);
    }

    //Cursar una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}")
    public void cursarAsignatura(@PathVariable int idAlumno, @PathVariable int idMateria) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        alumnoService.cursarAsignatura(idAlumno, idMateria);
    }

    //Aprobar una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}/nota/{nota}")
    public void aprobarAsignatura(@PathVariable int idAlumno, @PathVariable int idMateria, @PathVariable int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException, EstadoIncorrectoException, NotaIncorrectaException {
        alumnoService.aprobarAsignatura(idAlumno, idMateria, nota);
    }

    //Perder regularidad de una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}/perder")
    public void perderAsignatura(@PathVariable int idAlumno, @PathVariable int idMateria) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        alumnoService.perderAsignatura(idAlumno, idMateria);
    }

    
}
