package com.stefanodannunzio.api_universidad.business;

import java.util.List;

import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.model.dto.MateriaDto;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

public interface MateriaService {

    // Crear materia

    Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException;

    // Modificar materia

    Materia modificarMateria(Integer id, MateriaDto inputData) throws IllegalArgumentException, MateriaNotFoundException;

    // Eliminar materia

    void eliminarMateria(Integer id) throws MateriaNotFoundException;

    // Obtener materia por nombre de la misma

    Materia obtenerMateriaPorNombre(String nombre) throws MateriaNotFoundException;

    // Listar todas las materias ordenadas por nombre ascendente o descendente y código ascendente o descendente

    List<Materia> listarMaterias(String order);

    void agregarCorrelativa(int idMateria, int idCorrelativa);

    void eliminarCorrelativa(int idMateria, int idCorrelativa);
    
}
