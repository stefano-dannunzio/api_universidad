package com.stefanodannunzio.api_universidad.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stefanodannunzio.api_universidad.business.MateriaService;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.model.dto.MateriaDto;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;


@RestController
@RequestMapping("materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    //Crear materia

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody MateriaDto materiaDto) {        
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.crearMateria(materiaDto));

    }

    //Modificar materia

    @PutMapping("/{idMateria}")
    public ResponseEntity<Materia> modificarMateria(@PathVariable int idMateria, @RequestBody MateriaDto materiaDto) throws IllegalArgumentException, MateriaNotFoundException {
        return ResponseEntity.ok(materiaService.modificarMateria(idMateria, materiaDto));
    }

    //Eliminar materia

    @DeleteMapping("/{idMateria}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable int idMateria) throws MateriaNotFoundException {
        materiaService.eliminarMateria(idMateria);
        return ResponseEntity.noContent().build();
    }

    //Obtener materia por nombre

    @GetMapping("/name")
    public ResponseEntity<Materia> obtenerMateriaPorNombre(@RequestParam String nombre) throws MateriaNotFoundException {
        return ResponseEntity.ok(materiaService.obtenerMateriaPorNombre(nombre));
    }

    //Listar todas las materias ordenadas por nombre ascendente o descendente y código ascendente o descendente

    @GetMapping()
    public ResponseEntity<List<Materia>> listarMaterias(@RequestParam String order) throws MateriaNotFoundException {
        return ResponseEntity.ok(materiaService.listarMaterias(order));
    }

    //Agregar correlativa

    @PutMapping("/{idMateria}/correlativas/{idCorrelativa}")
    public ResponseEntity<Void> agregarCorrelativa(@PathVariable int idMateria, @PathVariable int idCorrelativa) throws MateriaNotFoundException {
        materiaService.agregarCorrelativa(idMateria, idCorrelativa);
        return ResponseEntity.noContent().build();
    }

    //Eliminar correlativa

    @DeleteMapping("/{idMateria}/correlativas/{idCorrelativa}")
    public ResponseEntity<Void> eliminarCorrelativa(@PathVariable int idMateria, @PathVariable int idCorrelativa) throws MateriaNotFoundException {
        materiaService.eliminarCorrelativa(idMateria, idCorrelativa);
        return ResponseEntity.noContent().build();
    }
    
}
