package com.stefanodannunzio.api_universidad.persistence;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

public interface CarreraDao {

    Carrera save(Carrera carrera);

    Carrera update(Integer idCarrera, Carrera c) throws CarreraNotFoundException;

    void delete(Integer idCarrera) throws CarreraNotFoundException;

    Carrera findById(Integer id) throws CarreraNotFoundException;
    
}
