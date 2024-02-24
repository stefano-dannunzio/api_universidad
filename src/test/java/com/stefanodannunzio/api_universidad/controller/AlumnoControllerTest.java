package com.stefanodannunzio.api_universidad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanodannunzio.api_universidad.business.AlumnoService;
import com.stefanodannunzio.api_universidad.model.Alumno;
import com.stefanodannunzio.api_universidad.model.dto.AlumnoDto;
import com.stefanodannunzio.api_universidad.model.exception.NotaIncorrectaException;
import com.stefanodannunzio.api_universidad.persistence.exception.AlumnoNotFoundException;
import com.stefanodannunzio.api_universidad.persistence.exception.AsignaturaNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(SpringExtension.class)
class AlumnoControllerTest {

    @InjectMocks
    AlumnoController alumnoController;

    @Mock
    AlumnoService alumnoService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.alumnoController).build();
    }

    @AfterEach
    void tearDown() {
    }

    // Testear el método crearAlumno
    @Test
    void crearAlumnoCorrectamenteTest() throws Exception {
        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDto.class))).thenReturn(new Alumno());
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Stefano");
        alumnoDto.setApellido("D'Annunzio");
        alumnoDto.setDni(42431228);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(alumnoDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(new Alumno(), mapper.readValue(result.getResponse().getContentAsString(), Alumno.class));

    }

    @Test
    void crearAlumnoArgumentosInvalidosTest() throws Exception {
        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDto.class))).thenReturn(new Alumno());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\": \"Stefano\",\n" +
                        "    \"apellido\": \"D'Annunzio\",\n" +
                        "    \"dni\": \"cuarentaydos\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    // Testear el método modificarAlumno
    @Test
    void modificarAlumnoCorrectamenteTest() throws Exception {
        Mockito.when(alumnoService.modificarAlumno(Mockito.any(Integer.class), Mockito.any(AlumnoDto.class))).thenReturn(new Alumno());
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Estefano");
        alumnoDto.setApellido("Gomez");
        alumnoDto.setDni(42431329);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(alumnoDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(new Alumno(), mapper.readValue(result.getResponse().getContentAsString(), Alumno.class));
    }
    @Test
    void modificarAlumnoNotFoundTest() throws Exception {
        Mockito.when(alumnoService.modificarAlumno(Mockito.any(Integer.class), Mockito.any(AlumnoDto.class))).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 42431229"));
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Estefano");
        alumnoDto.setApellido("Gomez");
        alumnoDto.setDni(42431329);

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(alumnoDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AlumnoNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró el alumno con el ID: 42431229", cause.getMessage());
        }
    }

    @Test
    void modificarAlumnoArgumentosInvalidosTest() throws Exception {
        Mockito.when(alumnoService.modificarAlumno(Mockito.any(Integer.class), Mockito.any(AlumnoDto.class))).thenReturn(new Alumno());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\": \"Estefano\",\n" +
                        "    \"apellido\": \"Gomez\",\n" +
                        "    \"dni\": \"cuarentaydos\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    // Testear el método eliminarAlumno
    @Test
    void eliminarAlumnoCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(alumnoService).eliminarAlumno(Mockito.any(Integer.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/42431329")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void eliminarAlumnoNotFoundTest() throws Exception {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 42431229")).when(alumnoService).eliminarAlumno(Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/42431329")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AlumnoNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró el alumno con el ID: 42431229", cause.getMessage());
        }
    }

    // Testear el método cursarAsignatura
    @Test
    void cursarAsignaturaCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(alumnoService).cursarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/cursar")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void cursarAsignaturaAlumnoNotFoundTest() throws Exception {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 42431229")).when(alumnoService).cursarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/cursar")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AlumnoNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró el alumno con el ID: 42431229", cause.getMessage());
        }
    }

    @Test
    void cursarAsignaturaAsignaturaNotFoundTest() throws Exception {
        Mockito.doThrow(new AsignaturaNotFoundException("No se encontró la asignatura con el ID: 2")).when(alumnoService).cursarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/2/cursar")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AsignaturaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la asignatura con el ID: 2", cause.getMessage());
        }
    }


    // Testear el método aprobarAsignatura
    @Test
    void aprobarAsignaturaCorrectamente() throws Exception {
        Mockito.doNothing().when(alumnoService).aprobarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/aprobar/8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void aprobarAsignaturaAlumnoNotFoundTest() throws Exception {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 42431229")).when(alumnoService).aprobarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/aprobar/8")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AlumnoNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró el alumno con el ID: 42431229", cause.getMessage());
        }
    }

    @Test
    void aprobarAsignaturaAsignaturaNotFoundTest() throws Exception {
        Mockito.doThrow(new AsignaturaNotFoundException("No se encontró la asignatura con el ID: 2")).when(alumnoService).aprobarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/2/aprobar/8")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AsignaturaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la asignatura con el ID: 2", cause.getMessage());
        }
    }

    @Test
    void aprobarAsignaturaNotaIncorrectaTest() throws Exception {
        Mockito.doThrow(new NotaIncorrectaException("La nota debe ser mayor o igual a 4 y menor o igual a 10")).when(alumnoService).aprobarAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/aprobar/2")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(NotaIncorrectaException.class, cause.getClass());
            Assertions.assertEquals("La nota debe ser mayor o igual a 4 y menor o igual a 10", cause.getMessage());
        }
    }

    // Testear el método perderAsignatura
    @Test
    void perderAsignaturaCorrectamenteTest() throws Exception {
        Mockito.doNothing().when(alumnoService).perderAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/perder")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void perderAsignaturaAlumnoNotFoundTest() throws Exception {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 42431229")).when(alumnoService).perderAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/1/perder")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AlumnoNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró el alumno con el ID: 42431229", cause.getMessage());
        }
    }

    @Test
    void perderAsignaturaAsignaturaNotFoundTest() throws Exception {
        Mockito.doThrow(new AsignaturaNotFoundException("No se encontró la asignatura con el ID: 2")).when(alumnoService).perderAsignatura(Mockito.any(Integer.class), Mockito.any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/42431329/asignatura/2/perder")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(AsignaturaNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la asignatura con el ID: 2", cause.getMessage());
        }
    }
}