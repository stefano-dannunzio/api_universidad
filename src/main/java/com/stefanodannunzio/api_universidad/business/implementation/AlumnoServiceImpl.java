package com.stefanodannunzio.api_universidad.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanodannunzio.api_universidad.business.AlumnoService;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoDao alumnoDao;

    @Override
    public Alumno crearAlumno(AlumnoDto inputData) throws IllegalArgumentException {
        Alumno a = new Alumno();
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.save(a);
        return a;
    }

    @Override
    public Alumno modificarAlumno(Integer id, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException {
        Alumno a = alumnoDao.findById(id);
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.update(id, a);
        return a;
    }

    @Override
    public void eliminarAlumno(Integer id) throws AlumnoNotFoundException {
        alumnoDao.delete(id);
    }

    @Override
    public void cursarAsignatura(Integer id, String nombre_materia) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findById(id);
        //Busco la asignatura, basandome en el nombre de la materia que tiene dentro
        Asignatura asig = a.getAsignaturas().stream().filter(asignatura -> asignatura.getNombreMateria().equals(nombre_materia)).findFirst().orElse(null);
        if (asig == null) {
            throw new AsignaturaNotFoundException("No se encontro la asignatura");
        }
        asig.cursar();;
    }

    @Override
    public void aprobarAsignatura(Integer id, String nombre_materia, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException {
        Alumno a = alumnoDao.findById(id);
        //Busco la asignatura, basandome en el nombre de la materia que tiene dentro
        Asignatura asig = a.getAsignaturas().stream().filter(asignatura -> asignatura.getNombreMateria().equals(nombre_materia)).findFirst().orElse(null);
        if (asig == null) {
            throw new AsignaturaNotFoundException("No se encontro la asignatura");
        }
        asig.aprobar(nota);

    }

    @Override
    public void perderAsignatura(Integer id, String nombre_materia) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findById(id);
        //Busco la asignatura, basandome en el nombre de la materia que tiene dentro
        Asignatura asig = a.getAsignaturas().stream().filter(asignatura -> asignatura.getNombreMateria().equals(nombre_materia)).findFirst().orElse(null);
        if (asig == null) {
            throw new AsignaturaNotFoundException("No se encontro la asignatura");
        }
        asig.perder();
    }
    
    
}
