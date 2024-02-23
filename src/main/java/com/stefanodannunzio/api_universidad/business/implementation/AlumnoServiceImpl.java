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
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;

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
    public Alumno modificarAlumno(Long id, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException {
        Alumno a = alumnoDao.findById(id);
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.update(id, a);
        return a;
    }

    @Override
    public void eliminarAlumno(Long id) throws AlumnoNotFoundException {
        alumnoDao.delete(id);
    }

    @Override
    public void cursarAsignatura(Long id, Long asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        Alumno a = alumnoDao.findById(id);
        a.cursarAsignatura(asignaturaId);
    }

    @Override
    public void aprobarAsignatura(Long id, Long asignaturaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException, CorrelativasNoAprobadasException, MateriaNotFoundException {
        Alumno a = alumnoDao.findById(id);
        a.aprobarAsignatura(asignaturaId, nota);

    }

    @Override
    public void perderAsignatura(Long id, Long asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findById(id);
        a.perderAsignatura(asignaturaId);
    }
    
    
}
