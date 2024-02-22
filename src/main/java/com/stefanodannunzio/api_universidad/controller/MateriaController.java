package com.stefanodannunzio.api_universidad.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
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
    public void crearMateria(@RequestBody MateriaDto materiaDto) {
        materiaService.crearMateria(materiaDto);
        
    }

    //Modificar materia

    @PutMapping("/{idMateria}")
    public void modificarMateria(@PathVariable int idMateria, @RequestBody MateriaDto materiaDto) throws IllegalArgumentException, MateriaNotFoundException {
        materiaService.modificarMateria(idMateria, materiaDto);
    }

    //Eliminar materia

    @DeleteMapping("/{idMateria}")
    public void eliminarMateria(@PathVariable int idMateria) throws MateriaNotFoundException {
        materiaService.eliminarMateria(idMateria);
    }

    //Obtener materia por nombre

    @GetMapping("/name")
    public Materia obtenerMateriaPorNombre(@RequestParam String nombre) throws MateriaNotFoundException {
        return materiaService.obtenerMateriaPorNombre(nombre);
    }

    //Listar todas las materias ordenadas por nombre ascendente o descendente y código ascendente o descendente

    @GetMapping()
    public List<Materia> listarMaterias(@RequestParam String order) throws MateriaNotFoundException {
        return materiaService.listarMaterias(order);
    }
    
}
