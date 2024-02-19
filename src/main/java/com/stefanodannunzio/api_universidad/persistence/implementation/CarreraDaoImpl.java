package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarreraDaoImpl implements CarreraDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Carrera save(Carrera carrera) {
        entityManager.persist(carrera);
        return carrera;
    }

    @Override
    public Carrera update(Integer idCarrera, Carrera c) throws CarreraNotFoundException {
        Carrera carrera = entityManager.find(Carrera.class, idCarrera);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + idCarrera);
        }
        carrera.setNombre(c.getNombre());
        carrera.setDepartamentoId(c.getDepartamentoId());
        carrera.setCuatrimestres(c.getCuatrimestres());

        entityManager.merge(carrera);

        return carrera;
    }

    @Override
    public void delete(Integer idCarrera) throws CarreraNotFoundException {
        Carrera carrera = entityManager.find(Carrera.class, idCarrera);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + idCarrera);
        }
        entityManager.remove(carrera);

    }

    @Override
    public Carrera findById(Integer id) throws CarreraNotFoundException {
        Carrera carrera = entityManager.find(Carrera.class, id);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + id);
        }
        return carrera;
    }
}
