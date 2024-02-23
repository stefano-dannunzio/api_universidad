package com.stefanodannunzio.api_universidad.business.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanodannunzio.api_universidad.business.MateriaService;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.model.dto.MateriaDto;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

@Service
public class MateriaServiceImpl implements MateriaService {
    
    @Autowired
    private MateriaDao materiaDao;


    @Autowired
    private CarreraDao carreraDao;


    @Override
    public Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException {
        Materia m = new Materia();
        m.setNombre(inputData.getNombre());
        m.setAnio(inputData.getAnio());
        m.setCuatrimestre(inputData.getCuatrimestre());
        m.setCarreraId(inputData.getCarreraId());
        materiaDao.save(m);
        if (m.getNombre().contains("#")) {
            throw new IllegalArgumentException("No se pudo crear la materia");
        }
        carreraDao.agregarMateria(m.getCarreraId(), m.getMateriaId());
        return m;
        
    }

    @Override
    public Materia modificarMateria(Integer id, MateriaDto inputData) throws MateriaNotFoundException {
        Materia m = materiaDao.findById(id);
        m.setNombre(inputData.getNombre());
        m.setAnio(inputData.getAnio());
        m.setCuatrimestre(inputData.getCuatrimestre());
        m.setCarreraId(inputData.getCarreraId());
        materiaDao.update(id, m);
        if (m.getNombre().contains("#")) {
            throw new IllegalArgumentException("No se pudo modificar la materia");
        }
        return m;
    }

    @Override
    public void eliminarMateria(Integer id) throws MateriaNotFoundException {
        materiaDao.delete(id);
    }

    @Override
    public Materia obtenerMateriaPorNombre(String nombre) throws MateriaNotFoundException {
        return materiaDao.findByName(nombre);
    }

    @Override
    public List<Materia> listarMaterias(String order) throws MateriaNotFoundException {
        return materiaDao.sortAll(order);
    }

    @Override
    public void agregarCorrelativa(int idMateria, int idCorrelativa) {
        materiaDao.agregarCorrelativa(idMateria, idCorrelativa);
    }

    @Override
    public void eliminarCorrelativa(int idMateria, int idCorrelativa) {
        materiaDao.eliminarCorrelativa(idMateria, idCorrelativa);
    }
    
}
