package com.stefanodannunzio.api_universidad.business.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanodannunzio.api_universidad.business.CarreraService;
import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

@Service
public class CarreraServiceImpl implements CarreraService {

    
    private CarreraDao carreraDao;

    @Autowired
    public void setCarreraDao(CarreraDao carreraDao) {
        this.carreraDao = carreraDao;
    }

    @Override
    public Carrera crearCarrera(CarreraDto inputData) throws IllegalArgumentException {
        Carrera c = new Carrera();
        c.setNombre(inputData.getNombre());
        c.setDepartamentoId(inputData.getDepartamentoId());
        c.setCuatrimestres(inputData.getCuatrimestres());
        carreraDao.save(c);
        if (c.getNombre().contains("#")) {
            throw new IllegalArgumentException("No se pudo crear la carrera");
        }
        return c;
    }

    @Override
    public Carrera modificarCarrera(Integer id, CarreraDto inputData) throws IllegalArgumentException, CarreraNotFoundException {

        Carrera c = carreraDao.findById(id);
        c.setNombre(inputData.getNombre());
        c.setDepartamentoId(inputData.getDepartamentoId());
        c.setCuatrimestres(inputData.getCuatrimestres());
        carreraDao.update(id, c);
        return c;
    }

    @Override
    public void eliminarCarrera(Integer id) throws CarreraNotFoundException {
        carreraDao.delete(id);
    }

    @Override
    public List<Carrera> listarCarreras() {
        return carreraDao.listAll();
    }

    
    
}
