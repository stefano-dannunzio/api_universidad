package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AlumnoDaoImpl implements AlumnoDao<Long, Asignatura>{

    private Map<Long, Alumno> alumnos = new HashMap<>();
    private Map<Long, Map<Long, Asignatura>> asignaturasPorAlumno = new HashMap<>();

    @Override
    public Alumno save(Alumno a) {
        alumnos.put(a.getId(), a);
        asignaturasPorAlumno.put(a.getId(), new HashMap<>());
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
        asignaturasPorAlumno.remove(idAlumno);

    }

    @Override
    public Alumno findById(Long idAlumno) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(idAlumno);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + idAlumno);
        }

        return alumno;
    }

    @Override
    public Map<Long, Asignatura> getAsignaturas(Long idAlumno) {
        return asignaturasPorAlumno.get(idAlumno);
    }

    @Override
    public Asignatura getAsignaturaByMateria(Long idAlumno, Materia materia) {
        Map<Long, Asignatura> asignaturas = getAsignaturas(idAlumno); // Obtener las asignaturas de la implementación
        for (Asignatura asignatura : asignaturas.values()) {
            if (asignatura.getMateria().equals(materia)) {
                return asignatura;
            }
        }
        return null;
    }

    @Override
    public EstadoAsignatura getEstadoAsignatura(Long idAlumno, Materia materia) {
        Asignatura asignatura = getAsignaturaByMateria(idAlumno, materia);
        if (asignatura == null) {
            return EstadoAsignatura.NO_CURSADA;
        }
        return asignatura.getEstado();
        
    }

    
}
