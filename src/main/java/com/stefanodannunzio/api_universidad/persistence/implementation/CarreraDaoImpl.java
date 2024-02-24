package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CarreraDaoImpl implements CarreraDao {

    @Autowired
    private MateriaDao materiaDao;

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

    @Override
    public List<Carrera> listAll() {
            return new ArrayList<>(carreras.values());
        }
        
    

    @Override
    public void agregarMateria(Integer idCarrera, Integer idMateria) throws MateriaNotFoundException {
        Carrera carrera = carreras.get(idCarrera);
        carrera.getMaterias().add(materiaDao.findById(idMateria));
    }

}



