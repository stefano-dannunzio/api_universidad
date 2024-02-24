package com.stefanodannunzio.api_universidad.business.implementation;

import com.stefanodannunzio.api_universidad.business.implementation.AlumnoServiceImpl;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.EstadoAsignatura;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.EstadoIncorrectoException;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {


    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Mock
    private AlumnoDao alumnoDaoMock;

    @Mock
    private Alumno alumnoMockGeneral;


    // Test de crearAlumno
    @Test
    void crearAlumnoCorrectamenteTest() throws IllegalArgumentException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);

        assertNotNull(alumno);
        assertEquals(12345678, alumno.getDni());
        assertEquals("Juan", alumno.getNombre());
        assertEquals("Perez", alumno.getApellido());
    }

    // No hace falta crear test de crearAlumnoConDatosInvalidosTest porque AlumnoController no permite enviar datos invalidos

    // Test de modificarAlumno
    @Test
    void modificarAlumnoCorrectamenteTest() throws IllegalArgumentException, AlumnoNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juanita");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(new Alumno());
        Mockito.when(alumnoDaoMock.update(eq(12345678), any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.modificarAlumno(12345678, alumnoDto);

        assertNotNull(alumno);
        assertEquals(12345678, alumno.getDni());
        assertEquals("Juanita", alumno.getNombre());
        assertEquals("Perez", alumno.getApellido());
    }

    // No hace falta crear test de modificarAlumnoConDatosInvalidosTest porque AlumnoController no permite enviar datos invalidos

    @Test
    void modificarAlumnoConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juanita");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.modificarAlumno(12345678, alumnoDto));
    }

    // Test de eliminarAlumno
    @Test
    void eliminarAlumnoCorrectamenteTest() throws AlumnoNotFoundException {
        doNothing().when(alumnoDaoMock).delete(12345678);

        alumnoService.eliminarAlumno(12345678);
    }

    @Test
    void eliminarAlumnoConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678)).when(alumnoDaoMock).delete(12345678);

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.eliminarAlumno(12345678));
    }

    // Test de getNombreAsignatura
    @Test
    void getNombreAsignaturaCorrectamenteTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(new Alumno());
        String nombreAsignatura = alumnoService.getNombreAsignatura(12345678, 1);

        assertNotNull(nombreAsignatura);
        assertEquals("Matematica 2", nombreAsignatura);
    }

    @Test
    void getNombreAsignaturaConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.getNombreAsignatura(12345678, 1));
    }

    @Test
    void getNombreAsignaturaConAsignaturaNoEncontradaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(new Alumno());

        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.getNombreAsignatura(12345678, 4));
    }

    @Test
    void getNombreAsignaturaConDNIInvalidoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(123456789)).thenThrow(new IllegalArgumentException("El DNI es incorrecto. Debe ser un número positivo de 8 dígitos."));

        assertThrows(IllegalArgumentException.class, () -> alumnoService.getNombreAsignatura(123456789, 1));
    }

    // Test de getNotaAsignatura
    @Test
    void getNotaAsignaturaCorrectamenteConNotaNulaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
        alumnoService.cursarAsignatura(12345678, 1);
        Integer notaAsignatura = alumnoService.getNotaAsignatura(12345678, 1);

        assertNull(notaAsignatura);
    }

    @Test
    void getNotaAsignaturaConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.getNotaAsignatura(12345678, 1));
    }

    @Test
    void getNotaAsignaturaConAsignaturaNoEncontradaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(new Alumno());

        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.getNotaAsignatura(12345678, 4));
    }

    @Test
    void getNotaAsignaturaCorrectamenteConNotaNoNulaTest() throws IllegalArgumentException, AlumnoNotFoundException, NotaIncorrectaException, EstadoIncorrectoException, AsignaturaNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
        alumnoService.cursarAsignatura(12345678, 1);
        alumnoService.aprobarAsignatura(12345678, 1, 8);
        Integer notaAsignatura = alumnoService.getNotaAsignatura(12345678, 1);

        assertNotNull(notaAsignatura);
        assertEquals(8, notaAsignatura);
    }

    @Test
    void getNotaAsignaturaConDNIInvalidoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(123456789)).thenThrow(new IllegalArgumentException("El DNI es incorrecto. Debe ser un número positivo de 8 dígitos."));

        assertThrows(IllegalArgumentException.class, () -> alumnoService.getNotaAsignatura(123456789, 1));
    }


    @Test
    void cursarAsignaturaCorrectamenteTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
        alumnoService.cursarAsignatura(12345678, 1);
        assertEquals(EstadoAsignatura.CURSADA, alumno.getAsignatura(1).getEstado());


    }

    @Test
    void cursarAsignaturaConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.cursarAsignatura(12345678, 1));
    }

    @Test
    void cursarAsignaturaConAsignaturaNoEncontradaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.cursarAsignatura(12345678, 4));
    }

    @Test
    void cursarAsignaturaConDNIInvalidoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(123456789)).thenThrow(new IllegalArgumentException("El DNI es incorrecto. Debe ser un número positivo de 8 dígitos."));

        assertThrows(IllegalArgumentException.class, () -> alumnoService.cursarAsignatura(123456789, 1));
    }

    @Test
    void aprobarAsignaturaCorrectamenteTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
        alumnoService.cursarAsignatura(12345678, 1);
        alumnoService.aprobarAsignatura(12345678, 1, 8);
        assertEquals(EstadoAsignatura.APROBADA, alumno.getAsignatura(1).getEstado());
        assertEquals(8, alumnoService.getNotaAsignatura(12345678, 1));
    }

    @Test
    void aprobarAsignaturaConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.aprobarAsignatura(12345678, 1, 8));
    }

    @Test
    void aprobarAsignaturaConAsignaturaNoEncontradaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.aprobarAsignatura(12345678, 4, 8));
    }

    @Test
    void aprobarAsignaturaConDNIInvalidoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(123456789)).thenThrow(new IllegalArgumentException("El DNI es incorrecto. Debe ser un número positivo de 8 dígitos."));

        assertThrows(IllegalArgumentException.class, () -> alumnoService.aprobarAsignatura(123456789, 1, 8));
    }

    @Test
    void aprobarAsignaturaConNotaInvalidaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(new Alumno());

        assertThrows(NotaIncorrectaException.class, () -> alumnoService.aprobarAsignatura(12345678, 1, 3));
    }

    @Test
    void perderAsignaturaCorrectamenteTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Perez");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
        alumnoService.perderAsignatura(12345678, 1);
        assertEquals(EstadoAsignatura.DESAPROBADA, alumno.getAsignatura(1).getEstado());
    }

    @Test
    void perderAsignaturaConAlumnoNoEncontradoTest() throws AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(12345678)).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + 12345678));

        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.perderAsignatura(12345678, 1));
    }

    @Test
    void perderAsignaturaConAsignaturaNoEncontradaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.perderAsignatura(12345678, 4));
    }

    @Test
    void perderAsignaturaConDNIInvalidoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.findByDNI(123456789)).thenThrow(new IllegalArgumentException("El DNI es incorrecto. Debe ser un número positivo de 8 dígitos."));

        assertThrows(IllegalArgumentException.class, () -> alumnoService.perderAsignatura(123456789, 1));
    }
}