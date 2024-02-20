package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MateriaDaoImpl implements MateriaDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Materia save(Materia materia) {
        entityManager.persist(materia);
        return materia;
    }

    @Override
    public Materia update(Integer idMateria, Materia m) throws MateriaNotFoundException {
        Materia materia = entityManager.find(Materia.class, idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        materia.setAnio(m.getAnio());
        materia.setNombre(m.getNombre());
        materia.setCuatrimestre(m.getCuatrimestre());
        materia.setMateriaId(m.getMateriaId());

        entityManager.merge(materia);
        return materia;
    }

    @Override
    public void delete(Integer idMateria) throws MateriaNotFoundException {
        Materia materia = entityManager.find(Materia.class, idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        entityManager.remove(materia);

    }

    @Override
    public Materia findByName(String nombre) throws MateriaNotFoundException {
        Materia materia = entityManager.find(Materia.class, nombre);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el nombre: " + nombre);
        }
        return materia;
    }

    @Override
    public Materia findById(Integer id) throws MateriaNotFoundException {
        Materia materia = entityManager.find(Materia.class, id);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + id);
        }
        return materia;
    }

    @Override
    public List<Materia> sortAll(String order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Materia> criteriaQuery = criteriaBuilder.createQuery(Materia.class);
        Root<Materia> materiaRoot = criteriaQuery.from(Materia.class);

        switch (order) {
            case "nombreAsc":
                criteriaQuery.orderBy(criteriaBuilder.asc(materiaRoot.get("nombre")));
                break;
            case "nombreDesc":
                criteriaQuery.orderBy(criteriaBuilder.desc(materiaRoot.get("nombre")));
                break;
            case "codigoAsc":
                criteriaQuery.orderBy(criteriaBuilder.asc(materiaRoot.get("id")));
                break;
            case "codigoDesc":
                criteriaQuery.orderBy(criteriaBuilder.desc(materiaRoot.get("id")));
                break;
            default:
                throw new IllegalArgumentException("Orden no válida: " + order);
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
