package com.stefanodannunzio.api_universidad.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanodannunzio.api_universidad.business.AlumnoService;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.Asignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.CorrelativasNoAprobadasException;
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
    public void cursarAsignatura(Integer id, Integer materiaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        Alumno a = alumnoDao.findById(id);
        a.cursarAsignatura(materiaId);
    }

    @Override
    public void aprobarAsignatura(Integer id, Integer materiaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException, CorrelativasNoAprobadasException {
        Alumno a = alumnoDao.findById(id);
        a.aprobarAsignatura(materiaId, nota);

    }

    @Override
    public void perderAsignatura(Integer id, Integer materiaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findById(id);
        a.perderAsignatura(materiaId);
    }
    
    
}
