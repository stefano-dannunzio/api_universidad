package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoDaoImpl implements AlumnoDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Alumno save(Alumno a) {
        entityManager.persist(a);
        return a;
    }

    @Override
    public Alumno update(Integer idAlumno, Alumno a) throws AlumnoNotFoundException {
        Alumno alumno = entityManager.find(Alumno.class, idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }

        alumno.setNombre(a.getNombre());
        alumno.setApellido(a.getApellido());
        alumno.setDni(a.getDni());

        entityManager.merge(alumno);

        return alumno;
    }

    @Override
    public void delete(Integer idAlumno) throws AlumnoNotFoundException {
        Alumno alumno = entityManager.find(Alumno.class, idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }
        entityManager.remove(alumno);

    }

    @Override
    public Alumno findById(Integer idAlumno) throws AlumnoNotFoundException {
        Alumno alumno = entityManager.find(Alumno.class, idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }

        return alumno;
    }
}
