package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AlumnoDaoImpl implements AlumnoDao {

    private Map<Long, Alumno> alumnos = new HashMap<>();

    @Override
    public Alumno save(Alumno a) {
        alumnos.put(a.getId(), a);
        return a;
    }

    @Override
    public Alumno update(Long idAlumno, Alumno a) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }

        alumno.setNombre(a.getNombre());
        alumno.setApellido(a.getApellido());
        alumno.setDni(a.getDni());

        alumnos.put(idAlumno, alumno);

        return alumno;
    }

    @Override
    public void delete(Long idAlumno) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }
        alumnos.remove(idAlumno);

    }

    @Override
    public Alumno findById(Long idAlumno) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }

        return alumno;
    }
}
