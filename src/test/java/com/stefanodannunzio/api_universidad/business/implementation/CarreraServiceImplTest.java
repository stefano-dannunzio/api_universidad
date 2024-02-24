package com.stefanodannunzio.api_universidad.business.implementation;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarreraServiceImplTest {

    @InjectMocks
    private CarreraServiceImpl carreraService;

    @Mock
    private CarreraDao carreraDao;

    @Mock
    private MateriaDao materiaDao;

    // Test de crearCarrera

    @Test
    void crearCarreraCorrectamenteTest() throws IllegalArgumentException {
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingenieria en Sistemas");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        when(carreraDao.save(any(Carrera.class))).thenReturn(new Carrera());

        Carrera carrera = carreraService.crearCarrera(carreraDto);

        assertNotNull(carrera);
        assertEquals("Ingenieria en Sistemas", carrera.getNombre());
        assertEquals(1, carrera.getDepartamentoId());
        assertEquals(10, carrera.getCuatrimestres());
    }

    // No hace falta crear test de crearCarreraConDatosInvalidosTest porque CarreraController no permite enviar datos invalidos

    @Test
    void modificarCarreraCorrectamenteTest() throws CarreraNotFoundException {
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingenieria en Sistemas");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        Carrera carrera = new Carrera();
        carrera.setNombre("Ingenieria en Alimentos");
        carrera.setDepartamentoId(2);
        carrera.setCuatrimestres(8);

        when(carreraDao.findById(1)).thenReturn(carrera);


        when(carreraDao.update(eq(1), any(Carrera.class))).thenReturn(new Carrera());

        Carrera carreraModificada = carreraService.modificarCarrera(1, carreraDto);

        assertNotNull(carreraModificada);
        assertEquals("Ingenieria en Sistemas", carreraModificada.getNombre());
        assertEquals(1, carreraModificada.getDepartamentoId());
        assertEquals(10, carreraModificada.getCuatrimestres());
    }

    @Test
    void modificarCarreraConCarreraInexistenteTest() throws CarreraNotFoundException {

        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingenieria en Sistemas");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        Mockito.when(carreraDao.findById(1)).thenThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: " + 1));

        assertThrows(CarreraNotFoundException.class, () -> {
            carreraService.modificarCarrera(1, carreraDto);
        });
    }

    // Test de eliminarCarrera

    @Test
    void eliminarCarreraCorrectamenteTest() throws CarreraNotFoundException {
        Carrera carrera = new Carrera();
        carrera.setNombre("Ingenieria en Sistemas");
        carrera.setDepartamentoId(1);
        carrera.setCuatrimestres(10);
        carrera.setId(1);

        carreraService.eliminarCarrera(1);

        Mockito.verify(carreraDao).delete(1);
    }

    @Test
    void eliminarCarreraCarreraInexistenteTest() throws CarreraNotFoundException {
        Mockito.doThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: " + 1)).when(carreraDao).delete(1);

        assertThrows(CarreraNotFoundException.class, () -> {
            carreraService.eliminarCarrera(1);
        });
    }

    @Test
    void listarCarrerasCorrectamenteTest() {
        Carrera carreraMock1 = new Carrera();
        carreraMock1.setNombre("Ingenieria en Sistemas");
        carreraMock1.setDepartamentoId(1);
        carreraMock1.setCuatrimestres(10);
        carreraMock1.setId(1);

        Carrera carreraMock2 = new Carrera();
        carreraMock2.setNombre("Ingenieria en Alimentos");
        carreraMock2.setDepartamentoId(2);
        carreraMock2.setCuatrimestres(8);
        carreraMock2.setId(2);

        Mockito.when(carreraDao.listAll()).thenReturn(java.util.Arrays.asList(carreraMock1, carreraMock2));

        List<Carrera> carreras = carreraService.listarCarreras();

        assertNotNull(carreras);
        assertEquals(2, carreras.size());
        assertEquals("Ingenieria en Sistemas", carreras.get(0).getNombre());
        assertEquals("Ingenieria en Alimentos", carreras.get(1).getNombre());
        assertEquals(1, carreras.get(0).getDepartamentoId());
        assertEquals(2, carreras.get(1).getDepartamentoId());
        assertEquals(10, carreras.get(0).getCuatrimestres());
        assertEquals(8, carreras.get(1).getCuatrimestres());
    }
}