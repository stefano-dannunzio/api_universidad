package com.stefanodannunzio.api_universidad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanodannunzio.api_universidad.business.MateriaService;
import com.stefanodannunzio.api_universidad.model.Materia;
import com.stefanodannunzio.api_universidad.model.dto.MateriaDto;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.MateriaNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(SpringExtension.class)
class MateriaControllerTest {

    @InjectMocks
    MateriaController materiaController;

    @Mock
    MateriaService materiaService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.materiaController).build();
    }

    @AfterEach
    void tearDown() {
    }

    // Testear el método crearMateria

    @Test
    void crearMateriaCorrectamenteTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Matematica 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDto.class))).thenReturn(materiaMock);
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(materiaDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Matematica 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());

    }

    @Test
    void crearMateriaArgumentosInvalidosTest() throws Exception {
        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDto.class))).thenReturn(new Materia());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\": \"Matematica 1\",\n" +
                        "    \"anio\": \"uno\",\n" +
                        "    \"cuatrimestre\": 1,\n" +
                        "    \"carreraId\": 1 \n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void crearMateriaCarreraNotFoundTest() throws Exception {
        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDto.class))).thenThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: 1"));
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"nombre\": \"Matematica 1\",\n" +
                            "    \"anio\": 1,\n" +
                            "    \"cuatrimestre\": 1,\n" +
                            "    \"carreraId\": 1 \n" +
                            "}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            Assertions.assertEquals(CarreraNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la carrera con el ID: 1", cause.getMessage());
        }
    }

    // Testear el método modificarMateria

    @Test
    void modificarMateriaCorrectamenteTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Matematica 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.modificarMateria(Mockito.anyInt(), Mockito.any(MateriaDto.class))).thenReturn(materiaMock);
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(materiaDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Matematica 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
    }

    @Test
    void modificarMateriaArgumentosInvalidosTest() throws Exception {
        Mockito.when(materiaService.modificarMateria(Mockito.anyInt(), Mockito.any(MateriaDto.class))).thenReturn(new Materia());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\": \"Matematica 1\",\n" +
                        "    \"anio\": \"uno\",\n" +
                        "    \"cuatrimestre\": 1,\n" +
                        "    \"carreraId\": 1 \n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void modificarMateriaMateriaNotFoundTest() throws Exception {
        Mockito.when(materiaService.modificarMateria(Mockito.anyInt(), Mockito.any(MateriaDto.class))).thenThrow(new MateriaNotFoundException("No se encontró la materia con el ID: 1"));
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"nombre\": \"Matematica 1\",\n" +
                            "    \"anio\": 1,\n" +
                            "    \"cuatrimestre\": 1,\n" +
                            "    \"carreraId\": 1 \n" +
                            "}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            Assertions.assertEquals(MateriaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la materia con el ID: 1", cause.getMessage());
        }
    }

    // Testear el método eliminarMateria

    @Test
    void eliminarMateriaCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(materiaService).eliminarMateria(Mockito.anyInt());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void eliminarMateriaMateriaNotFoundTest() throws Exception {
        Mockito.doThrow(new MateriaNotFoundException("No se encontró la materia con el ID: 1")).when(materiaService).eliminarMateria(Mockito.anyInt());
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            Assertions.assertEquals(MateriaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la materia con el ID: 1", cause.getMessage());
        }
    }

    // Testear el método obtenerMateriaPorNombre

    @Test
    void obtenerMateriaPorNombreCorrectamenteTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Matematica 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.obtenerMateriaPorNombre(Mockito.anyString())).thenReturn(materiaMock);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/name?nombre=Matematica 1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Matematica 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
    }

    @Test
    void obtenerMateriaPorNombreMateriaNotFoundTest() throws Exception {
        Mockito.when(materiaService.obtenerMateriaPorNombre(Mockito.anyString())).thenThrow(new MateriaNotFoundException("No se encontró la materia con el nombre: Matematica 1"));
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/name?nombre=Matematica 1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            Assertions.assertEquals(MateriaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la materia con el nombre: Matematica 1", cause.getMessage());
        }
    }

    // Testear el método listarMaterias

    @Test
    void listarMateriasCorrectamenteTest() throws Exception {
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

        Mockito.when(materiaService.listarMaterias(Mockito.anyString())).thenReturn(java.util.List.of(materiaMock1, materiaMock2));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia?order=asc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);

        assertEquals("Matematica 1", materias[0].getNombre());
        assertEquals(1, materias[0].getAnio());
        assertEquals(1, materias[0].getCuatrimestre());
        assertEquals("Matematica 2", materias[1].getNombre());
        assertEquals(2, materias[1].getAnio());
        assertEquals(1, materias[1].getCuatrimestre());
    }

    // Testear el método agregarCorrelativa

    @Test
    void agregarCorrelativaCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(materiaService).agregarCorrelativa(Mockito.anyInt(), Mockito.anyInt());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1/correlativas/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    // Testear el método eliminarCorrelativa

    @Test
    void eliminarCorrelativaCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(materiaService).eliminarCorrelativa(Mockito.anyInt(), Mockito.anyInt());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/1/correlativas/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}