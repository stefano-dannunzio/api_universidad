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
    @PutMapping("/{dniAlumno}")
    public ResponseEntity<Alumno> modificarAlumno(@PathVariable int dniAlumno, @RequestBody AlumnoDto alumnoDto) throws IllegalArgumentException, AlumnoNotFoundException {
        return ResponseEntity.ok(alumnoService.modificarAlumno(dniAlumno, alumnoDto));
    }

    //Eliminar alumno
    @DeleteMapping("/{dniAlumno}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable int dniAlumno) throws AlumnoNotFoundException {
        alumnoService.eliminarAlumno(dniAlumno);
        return ResponseEntity.ok("Alumno eliminado");
    }

    //Cursar una asignatura
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<String> cursarAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        alumnoService.cursarAsignatura(dniAlumno, idAsignatura);

        return ResponseEntity.ok("La asignatura "+ alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + " fue cursada con éxito.");
    }

    //Aprobar una asignatura
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}/nota/{nota}")
    public ResponseEntity<String> aprobarAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura, @PathVariable int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException, EstadoIncorrectoException, NotaIncorrectaException, MateriaNotFoundException {
        alumnoService.aprobarAsignatura(dniAlumno, idAsignatura, nota);
        return ResponseEntity.ok("La asignatura "+ alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + " fue aprobada con " + alumnoService.getNotaAsignatura(dniAlumno, idAsignatura) + ".");
    }

    //Perder regularidad de una asignatura
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}/perder")
    public ResponseEntity<String> perderAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        alumnoService.perderAsignatura(dniAlumno, idAsignatura);
        return ResponseEntity.ok("Se ha perdido la regularidad de la asignatura " + alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + ".");
        
    }

    
}
