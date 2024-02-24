package com.stefanodannunzio.api_universidad.business.implementation;

import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.model.dto.MateriaDto;
import com.stefanodannunzio.api_universidad.persistence.CarreraDao;
import com.stefanodannunzio.api_universidad.persistence.MateriaDao;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Mock
    private MateriaDao materiaDao;

    @Mock
    private CarreraDao carreraDao;

    @Mock
    private Materia materiaMockGeneral;


    // Test de crearMateria
    @Test
    void crearMateriaCorrectamenteTest() throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        when(carreraDao.findById(1)).thenReturn(new Carrera());
        when(materiaDao.save(any(Materia.class))).thenReturn(new Materia());

        Materia materia = materiaService.crearMateria(materiaDto);

        assertNotNull(materia);
        assertEquals("Matematica 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
        assertEquals(1, materia.getCarreraId());

    }

    // No hace falta crear test de crearMateriaConDatosInvalidosTest porque MateriaController no permite enviar datos invalidos

    @Test
    void crearMateriaConCarreraInexistenteTest() throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException {
        // Configurar el mock para devolver null cuando se busca la carrera por ID
        when(carreraDao.findById(2)).thenReturn(null);

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(2);

        when(materiaDao.save(any(Materia.class))).thenReturn(new Materia());

        assertThrows(CarreraNotFoundException.class, () -> {
            materiaService.crearMateria(materiaDto);
        });

    }

    // Test de modificarMateria
    @Test
    void modificarMateriaCorrectamenteTest() throws MateriaNotFoundException {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 2");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(2);
        materiaDto.setCarreraId(1);

        Materia materia = new Materia();
        materia.setNombre("Matematica 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
        materia.setMateriaId(1);

        when(materiaDao.findById(1)).thenReturn(materia);
        when(materiaDao.update(anyInt(), any(Materia.class))).thenReturn(new Materia());

        Materia materiaModificada = materiaService.modificarMateria(1, materiaDto);

        assertNotNull(materiaModificada);
        assertEquals("Matematica 2", materiaModificada.getNombre());
        assertEquals(1, materiaModificada.getAnio());
        assertEquals(2, materiaModificada.getCuatrimestre());
        assertEquals(1, materiaModificada.getCarreraId());
    }

    @Test
    void modificarMateriaConDatosInvalidosTest() throws MateriaNotFoundException {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 2#");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(2);
        materiaDto.setCarreraId(1);

        Materia materia = new Materia();
        materia.setNombre("Matematica 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
        materia.setMateriaId(1);

        when(materiaDao.findById(1)).thenReturn(materia);
        when(materiaDao.update(anyInt(), any(Materia.class))).thenReturn(new Materia());

        assertThrows(IllegalArgumentException.class, () -> {
            materiaService.modificarMateria(1, materiaDto);
        });

    }

    // Test de eliminarMateria
    @Test
    void eliminarMateriaCorrectamenteTest() throws MateriaNotFoundException {
        Materia materia = new Materia();
        materia.setNombre("Matematica 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
        materia.setMateriaId(1);

        materiaService.eliminarMateria(1);

        Mockito.verify(materiaDao, Mockito.times(1)).delete(1);
    }

    @Test
    void eliminarMateriaMateriaInexistenteTest() throws MateriaNotFoundException {
        Mockito.doThrow(MateriaNotFoundException.class).when(materiaDao).delete(1);

        assertThrows(MateriaNotFoundException.class, () -> {
            materiaService.eliminarMateria(1);
        });
    }

    // Test de obtenerMateriaPorNombre
    @Test
    void obtenerMateriaPorNombreCorrectamenteTest() throws MateriaNotFoundException {
        Materia materia = new Materia();
        materia.setNombre("Matematica 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
        materia.setMateriaId(1);

        when(materiaDao.findByName("Matematica 1")).thenReturn(materia);

        Materia materiaObtenida = materiaService.obtenerMateriaPorNombre("Matematica 1");

        assertNotNull(materiaObtenida);
        assertEquals("Matematica 1", materiaObtenida.getNombre());
        assertEquals(1, materiaObtenida.getAnio());
        assertEquals(1, materiaObtenida.getCuatrimestre());
        assertEquals(1, materiaObtenida.getCarreraId());
    }

    @Test
    void obtenerMateriaPorNombreMateriaInexistenteTest() throws MateriaNotFoundException {
        Mockito.doThrow(MateriaNotFoundException.class).when(materiaDao).findByName("Matematica 1");

        assertThrows(MateriaNotFoundException.class, () -> {
            materiaService.obtenerMateriaPorNombre("Matematica 1");
        });
    }

    // Test de listarMaterias

    @Test
    void listarMateriasCorrectamenteTest()  {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Matematica 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Matematica 2");
        materiaMock2.setAnio(2);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Mockito.when(materiaDao.sortAll("nombre_asc")).thenReturn(java.util.Arrays.asList(materiaMock1, materiaMock2));

        List<Materia> materias = materiaService.listarMaterias("nombre_asc");

        assertNotNull(materias);
        assertEquals(2, materias.size());
        assertEquals("Matematica 1", materias.get(0).getNombre());
        assertEquals("Matematica 2", materias.get(1).getNombre());
        assertEquals(1, materias.get(0).getAnio());
        assertEquals(2, materias.get(1).getAnio());
        assertEquals(1, materias.get(0).getCuatrimestre());
        assertEquals(1, materias.get(1).getCuatrimestre());
        assertEquals(1, materias.get(0).getCarreraId());
        assertEquals(1, materias.get(1).getCarreraId());


    }

    // Test de agregarCorrelativa

    @Test
    void agregarCorrelativa() {
    }

    @Test
    void eliminarCorrelativa() {
    }
}