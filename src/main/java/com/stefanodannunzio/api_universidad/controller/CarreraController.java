package com.stefanodannunzio.api_universidad.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stefanodannunzio.api_universidad.business.CarreraService;
import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    //Crear carrera

    @PostMapping
    public ResponseEntity<Carrera> crearCarrera(@RequestBody CarreraDto carreraDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crearCarrera(carreraDto));
    }

    //Modificar carrera

    @PutMapping("/{idCarrera}")
    public ResponseEntity<Carrera> modificarCarrera(@PathVariable int idCarrera, @RequestBody CarreraDto carreraDto) throws IllegalArgumentException, CarreraNotFoundException {
        return ResponseEntity.ok(carreraService.modificarCarrera(idCarrera, carreraDto));
    }

    //Eliminar carrera

    @DeleteMapping("/{idCarrera}")
    public ResponseEntity<String> eliminarCarrera(@PathVariable int idCarrera) throws CarreraNotFoundException {
        carreraService.eliminarCarrera(idCarrera);
        return ResponseEntity.ok("Carrera eliminada");
    }

    // Listar carreras por id ascendentemente

    @GetMapping("/listar")
    public ResponseEntity<List<Carrera>> listarCarreras() {
        return ResponseEntity.ok(carreraService.listarCarreras());
    }
    
}
