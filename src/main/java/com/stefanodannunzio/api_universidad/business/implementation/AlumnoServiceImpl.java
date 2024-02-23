package com.stefanodannunzio.api_universidad.business.implementation;

import java.util.List;

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

    
    private AlumnoDao alumnoDao;

    @Autowired
    public void setAlumnoDao(AlumnoDao alumnoDao) {
        this.alumnoDao = alumnoDao;
    }

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
    public Alumno modificarAlumno(Integer dni, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException {
        Alumno a = alumnoDao.findByDNI(dni);
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.update(dni, a);
        return a;
    }

    @Override
    public void eliminarAlumno(Integer dni) throws AlumnoNotFoundException {
        alumnoDao.delete(dni);
    }

    @Override
    public String getNombreAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findByDNI(dni);
        return a.getNombreAsignatura(asignaturaId);
    }

    @Override
    public Integer getNotaAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findByDNI(dni);
        return a.getNotaAsignatura(asignaturaId);
    }


    @Override
    public void cursarAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        Alumno a = alumnoDao.findByDNI(dni);
        a.cursarAsignatura(asignaturaId);
    }


    @Override
    public void aprobarAsignatura(Integer dni, Integer asignaturaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException, CorrelativasNoAprobadasException, MateriaNotFoundException {
        Alumno a = alumnoDao.findByDNI(dni);

         if (nota < 4 || nota > 10){
                throw new NotaIncorrectaException("La nota debe ser mayor o igual a 4 y menor o igual a 10");
            } else {
                a.aprobarAsignatura(asignaturaId, nota);
            }

    }

    @Override
    public void perderAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.findByDNI(dni);
        a.perderAsignatura(asignaturaId);
    }


    
    
}
