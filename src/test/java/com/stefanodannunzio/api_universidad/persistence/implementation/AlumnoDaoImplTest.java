package com.stefanodannunzio.api_universidad.persistence.implementation;

import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.persistence.AlumnoDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlumnoDaoImplTest {

    @InjectMocks
    private AlumnoDaoImpl alumnoDao;

    @Mock
    private Alumno alumnoMock;

    @Mock
    private AlumnoDao alumnoDaoMock;

    @Mock
    private MateriaDao materiaDaoMock;

    @Mock
    private Map<Integer, Alumno> mapaAlumnosMock = new HashMap<>();

    @BeforeEach
    void setUp() throws AlumnoNotFoundException {
        Alumno alumno = new Alumno();
        alumno.setDni(12345678);
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");


        when(alumnoDaoMock.findByDNI(12345678)).thenReturn(alumno);
    }


    @Test
    void saveCorrectamenteTest() {
        Alumno alumno = new Alumno();
        alumno.setDni(12345678);
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");

        Alumno alumnoGuardado = alumnoDaoMock.save(alumno);

        assertNotNull(alumnoGuardado);
        assertEquals(alumno.getDni(), alumnoGuardado.getDni());
        assertEquals(alumno.getNombre(), alumnoGuardado.getNombre());
        assertEquals(alumno.getApellido(), alumnoGuardado.getApellido());
    }


    @Test
    void updateCorrectamenteTest() throws AlumnoNotFoundException {
        Alumno alumno = new Alumno();
        alumno.setDni(12345678);
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");

        Alumno alumnoGuardado = alumnoDao.save(alumno);

        assertNotNull(alumnoGuardado);
        assertEquals(alumno.getDni(), alumnoGuardado.getDni());
        assertEquals(alumno.getNombre(), alumnoGuardado.getNombre());
        assertEquals(alumno.getApellido(), alumnoGuardado.getApellido());

        Alumno alumnoModificado = new Alumno();
        alumnoModificado.setDni(12345678);
        alumnoModificado.setNombre("Juan");
        alumnoModificado.setApellido("Perez");
        alumnoModificado.setNombre("Juan Carlos");

        Alumno alumnoModificadoGuardado = alumnoDao.update(alumnoModificado.getDni(), alumnoModificado);

        assertNotNull(alumnoModificadoGuardado);
        assertEquals(alumnoModificado.getDni(), alumnoModificadoGuardado.getDni());
        assertEquals(alumnoModificado.getNombre(), alumnoModificadoGuardado.getNombre());
        assertEquals(alumnoModificado.getApellido(), alumnoModificadoGuardado.getApellido());
    }

    @Test
    void delete() {
    }

    @Test
    void findByDNI() {
    }
}