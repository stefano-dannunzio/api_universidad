package com.stefanodannunzio.api_universidad.business;

import java.util.List;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

public interface CarreraService {
    
    // Crear carrera

    Carrera crearCarrera(CarreraDto inputData) throws IllegalArgumentException;

    // Modificar carrera

    Carrera modificarCarrera(Integer id, CarreraDto inputData) throws IllegalArgumentException, CarreraNotFoundException;

    // Eliminar carrera

    void eliminarCarrera(Integer id) throws CarreraNotFoundException;

    List<Carrera> listarCarreras();

    
}
