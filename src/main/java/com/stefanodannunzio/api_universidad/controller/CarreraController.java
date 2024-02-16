package com.stefanodannunzio.api_universidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stefanodannunzio.api_universidad.business.CarreraService;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    //Crear carrera
    @PostMapping
    public void crearCarrera(@RequestBody CarreraDto carreraDto) {
        carreraService.crearCarrera(carreraDto);
    }

    //Modificar carrera
    @PutMapping("/{idCarrera}")
    public void modificarCarrera(@PathVariable int idCarrera, @RequestBody CarreraDto carreraDto) throws IllegalArgumentException, CarreraNotFoundException {
        carreraService.modificarCarrera(idCarrera, carreraDto);
    }

    //Eliminar carrera
    @DeleteMapping("/{idCarrera}")
    public void eliminarCarrera(@PathVariable int idCarrera) throws CarreraNotFoundException {
        carreraService.eliminarCarrera(idCarrera);
    }

    
}
