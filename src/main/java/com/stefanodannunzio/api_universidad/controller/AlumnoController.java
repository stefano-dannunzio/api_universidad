package com.stefanodannunzio.api_universidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stefanodannunzio.api_universidad.business.AlumnoService;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

@RestController
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    //Crear alumno
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crearAlumno(alumnoDto));
    }

    //Modificar alumno
    @PutMapping("/{idAlumno}")
    public ResponseEntity<Alumno> modificarAlumno(@PathVariable long idAlumno, @RequestBody AlumnoDto alumnoDto) throws IllegalArgumentException, AlumnoNotFoundException {
        return ResponseEntity.ok(alumnoService.modificarAlumno(idAlumno, alumnoDto));
    }

    //Eliminar alumno
    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable long idAlumno) throws AlumnoNotFoundException {
        alumnoService.eliminarAlumno(idAlumno);
        return ResponseEntity.noContent().build();
    }

    //Cursar una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}")
    public ResponseEntity<Void> cursarAsignatura(@PathVariable long idAlumno, @PathVariable long idMateria) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        alumnoService.cursarAsignatura(idAlumno, idMateria);
        return ResponseEntity.noContent().build();
    }

    //Aprobar una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}/nota/{nota}")
    public ResponseEntity<Void> aprobarAsignatura(@PathVariable long idAlumno, @PathVariable long idMateria, @PathVariable int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException, EstadoIncorrectoException, NotaIncorrectaException, MateriaNotFoundException {
        alumnoService.aprobarAsignatura(idAlumno, idMateria, nota);
        return ResponseEntity.noContent().build();
    }

    //Perder regularidad de una asignatura
    @PutMapping("/{idAlumno}/asignatura/{idMateria}/perder")
    public ResponseEntity<Void> perderAsignatura(@PathVariable long idAlumno, @PathVariable long idMateria) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        alumnoService.perderAsignatura(idAlumno, idMateria);
        return ResponseEntity.noContent().build();
    }

    
}
