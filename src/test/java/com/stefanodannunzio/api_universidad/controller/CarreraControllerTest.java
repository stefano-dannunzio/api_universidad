package com.stefanodannunzio.api_universidad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanodannunzio.api_universidad.business.CarreraService;
import com.stefanodannunzio.api_universidad.model.Carrera;
import com.stefanodannunzio.api_universidad.model.dto.CarreraDto;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.CarreraNotFoundException;
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
class CarreraControllerTest {

    @InjectMocks
    CarreraController carreraController;

    @Mock
    CarreraService carreraService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.carreraController).build();
    }

    @AfterEach
    void tearDown() {
    }

    // Testear el método crearCarrera

    @Test
    void crearCarreraCorrectamenteTest() throws Exception {
        Carrera carreraMock = new Carrera();
        carreraMock.setNombre("Ingenieria en Sistemas");
        carreraMock.setDepartamentoId(1);
        carreraMock.setCuatrimestres(10);

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDto.class))).thenReturn(carreraMock);
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingenieria en Sistemas");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(carreraDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Carrera carrera = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);

        assertEquals("Ingenieria en Sistemas", carrera.getNombre());
        assertEquals(1, carrera.getDepartamentoId());
        assertEquals(10, carrera.getCuatrimestres());
    }



    @Test
    void crearCarreraArgumentosInvalidosTest() throws Exception {
        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDto.class))).thenReturn(new Carrera());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\": \"Ingenieria en Sistemas\",\n" +
                                "    \"id_departamento\": \"cinco\",\n" +
                                "    \"cuatrimestres\": \"veinte\"\n" +
                                "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    // Testear el método modificarCarrera
    @Test
    void modificarCarreraCorrectamenteTest() throws Exception {
        Carrera carreraMock = new Carrera();
        carreraMock.setNombre("Ingenieria en Sistemas");
        carreraMock.setDepartamentoId(1);
        carreraMock.setCuatrimestres(10);

        Mockito.when(carreraService.modificarCarrera(Mockito.anyInt(), Mockito.any(CarreraDto.class))).thenReturn(carreraMock);
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingenieria en Sistemas");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(carreraDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Carrera carrera = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);

        assertEquals("Ingenieria en Sistemas", carrera.getNombre());
        assertEquals(1, carrera.getDepartamentoId());
        assertEquals(10, carrera.getCuatrimestres());
    }

    @Test
    void modificarCarreraArgumentosInvalidosTest() throws Exception {
        Mockito.when(carreraService.modificarCarrera(Mockito.anyInt(), Mockito.any(CarreraDto.class))).thenReturn(new Carrera());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\": \"Ingenieria en Sistemas\",\n" +
                        "    \"id_departamento\": \"cinco\",\n" +
                        "    \"cuatrimestres\": \"veinte\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void modificarCarreraCarreraNotFoundTest() throws Exception {
        Mockito.when(carreraService.modificarCarrera(Mockito.anyInt(), Mockito.any(CarreraDto.class))).thenThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: 1"));

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "    \"nombre\": \"Ingenieria en Sistemas\",\n" +
                                    "    \"id_departamento\": 1,\n" +
                                    "    \"cuatrimestres\": 10\n" +
                                    "}")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();

        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(CarreraNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la carrera con el ID: 1", cause.getMessage());
        }

    }

    // Testear el método eliminarCarrera

    @Test
    void eliminarCarreraCorrectamente() throws Exception {
        Mockito.doNothing().when(carreraService).eliminarCarrera(Mockito.anyInt());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void eliminarCarreraCarreraNotFound() throws Exception {
        Mockito.doThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: 1")).when(carreraService).eliminarCarrera(Mockito.anyInt());
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(CarreraNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la carrera con el ID: 1", cause.getMessage());
        }
    }

    // Testear el método listarCarreras

    @Test
    void listarCarrerasCorrectamenteTest() throws Exception {
        Carrera carreraMock1 = new Carrera();
        carreraMock1.setNombre("Ingenieria en Sistemas");
        carreraMock1.setDepartamentoId(1);
        carreraMock1.setCuatrimestres(10);

        Carrera carreraMock2 = new Carrera();
        carreraMock2.setNombre("Ingenieria en Alimentos");
        carreraMock2.setDepartamentoId(2);
        carreraMock2.setCuatrimestres(8);

        Mockito.when(carreraService.listarCarreras()).thenReturn(java.util.List.of(carreraMock1, carreraMock2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carrera/listar")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Carrera[] carreras = mapper.readValue(result.getResponse().getContentAsString(), Carrera[].class);

        assertEquals("Ingenieria en Sistemas", carreras[0].getNombre());
        assertEquals(1, carreras[0].getDepartamentoId());
        assertEquals(10, carreras[0].getCuatrimestres());
        assertEquals("Ingenieria en Alimentos", carreras[1].getNombre());
        assertEquals(2, carreras[1].getDepartamentoId());
        assertEquals(8, carreras[1].getCuatrimestres());
    }
}