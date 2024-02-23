package com.stefanodannunzio.api_universidad.persistence;

import java.util.List;

import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

public interface MateriaDao {
    Materia save(Materia materia);

    Materia update(Integer idMateria, Materia m) throws MateriaNotFoundException;

    void delete(Integer idMateria) throws MateriaNotFoundException;

    Materia findByName(String name) throws MateriaNotFoundException;

    Materia findById(Integer id) throws MateriaNotFoundException;

    List<Materia> sortAll(String order);

    void agregarCorrelativa(int idMateria, int idCorrelativa);

    void eliminarCorrelativa(int idMateria, int idCorrelativa);
}
