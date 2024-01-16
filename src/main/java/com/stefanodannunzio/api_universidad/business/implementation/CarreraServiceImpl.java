package com.stefanodannunzio.api_universidad.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanodannunzio.api_universidad.business.CarreraService;
import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;

@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraDao carreraDao;

    @Override
    public Carrera crearCarrera(CarreraDto inputData) throws IllegalArgumentException {
        Carrera c = new Carrera();
        c.setNombre(inputData.getNombre());
        c.setDepartamentoId(inputData.getDepartamentoId());
        c.setCuatrimestres(inputData.getCuatrimestres());
        carreraDao.save(c);
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
    
}
