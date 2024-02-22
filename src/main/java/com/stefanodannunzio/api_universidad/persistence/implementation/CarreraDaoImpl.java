package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class CarreraDaoImpl implements CarreraDao {

    private Map<Integer, Carrera> carreras = new HashMap<>();

    @Override
    public Carrera save(Carrera carrera) {
        carreras.put(carrera.getId(), carrera);
        return carrera;
    }

    @Override
    public Carrera update(Integer idCarrera, Carrera c) throws CarreraNotFoundException {
        Carrera carrera = carreras.get(idCarrera);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + idCarrera);
        }
        carrera.setNombre(c.getNombre());
        carrera.setDepartamentoId(c.getDepartamentoId());
        carrera.setCuatrimestres(c.getCuatrimestres());

        carreras.put(idCarrera, carrera);

        return carrera;
    }

    @Override
    public void delete(Integer idCarrera) throws CarreraNotFoundException {
        Carrera carrera = carreras.get(idCarrera);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + idCarrera);
        }
        carreras.remove(idCarrera);

    }

    @Override
    public Carrera findById(Integer id) throws CarreraNotFoundException {
        Carrera carrera = carreras.get(id);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + id);
        }
        return carrera;
    }
}
