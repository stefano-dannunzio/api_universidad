package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MateriaDaoImpl implements MateriaDao {

    private Map<Integer, Materia> materias = new HashMap<>();

    @Override
    public Materia save(Materia materia) {
        materias.put(materia.getMateriaId(), materia);
        return materia;
    }

    @Override
    public Materia update(Integer idMateria, Materia m) throws MateriaNotFoundException {
        Materia materia = materias.get(idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        materia.setAnio(m.getAnio());
        materia.setNombre(m.getNombre());
        materia.setCuatrimestre(m.getCuatrimestre());
        materia.setMateriaId(m.getMateriaId());

        materias.put(idMateria, materia);
        return materia;
    }

    @Override
    public void delete(Integer idMateria) throws MateriaNotFoundException {
        Materia materia = materias.get(idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        materias.remove(idMateria);

    }

    @Override
    public Materia findByName(String nombre) throws MateriaNotFoundException {
        for (Materia materia : materias.values()) {
            if (materia.getNombre().equals(nombre)) {
                return materia;
            }
        }

        throw new MateriaNotFoundException("Materia no encontrada con nombre: " + nombre);
    }

    @Override
    public Materia findById(Integer id) throws MateriaNotFoundException {
        Materia materia = materias.get(id);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + id);
        }
        return materia;
    }

    @Override
    public List<Materia> sortAll(String order) {
        List<Materia> materiasList = new ArrayList<>(materias.values());

        switch (order) {
            case "nombre_asc":
                materiasList.sort(Comparator.comparing(Materia::getNombre));
                break;
            case "nombre_desc":
                materiasList.sort(Comparator.comparing(Materia::getNombre).reversed());
                break;
            case "codigo_asc":
                materiasList.sort(Comparator.comparing(Materia::getMateriaId));
                break;
            case "codigo_desc":
                materiasList.sort(Comparator.comparing(Materia::getMateriaId).reversed());
        }


        return materiasList;


    }

}
