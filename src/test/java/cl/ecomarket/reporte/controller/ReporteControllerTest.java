package cl.ecomarket.reporte.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.ecomarket.reporte.dto.PedidoDTO;
import cl.ecomarket.reporte.model.Reporte;
import cl.ecomarket.reporte.service.PedidoDTOService;
import cl.ecomarket.reporte.service.ReporteService;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @MockBean
    private ReporteService reporteService;

    @MockBean
    private PedidoDTOService pedidoDTOService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte devuelve un 200(ok) si encuentra reportes")
    void findReportesTest()throws Exception{
        Reporte reporte1 = new Reporte();
        Reporte reporte2 = new Reporte();
        when(reporteService.findAll()).thenReturn(Arrays.asList(reporte1,reporte2));

        mockMvc.perform(get("/api/v1/ecomarket/reporte")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte Devuelve un 204(NoContent) al no encontrar datos")
    void findReportesEmptyTest() throws Exception{
        when(reporteService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/ecomarket/reporte")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte/{id}/buscar Buscar un reporte por su ID y devuelve un 200(OK)")
    void findReporteByIdTest() throws Exception{
        Date fecha = Date.valueOf("2024-01-10");
        Reporte reporte = new Reporte(1, "Tipo", fecha);
        when(reporteService.findById(1)).thenReturn(reporte);

        mockMvc.perform(get("/api/v1/ecomarket/reporte/1/buscar")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipo").value("Tipo"))
                .andExpect(jsonPath("$.fecha").value("2024-01-10"));
    }

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte/{id}/buscar Busca un reporte por su id y devuelve un 204")
    void findReporteByIdEmptyTest() throws Exception{
        when(reporteService.findById(1)).thenThrow(new RuntimeException("Reporte no encontrado"));

        mockMvc.perform(get("/api/v1/ecomarket/reporte/1/buscar")
                .accept(MediaType.APPLICATION_JSON))        
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/ecomarket/reporte/guardar Guarda un nuevo reporte y devuelve un 201(Created)")
    void saveReporteTest()throws Exception{
        Date fecha = Date.valueOf("2024-10-10");
        Reporte reporte = new Reporte();
        reporte.setTipo("tipo");
        reporte.setFecha(fecha);
        Reporte reporte2 = new Reporte(10,"tipo",fecha);
        when(reporteService.save(any(Reporte.class))).thenReturn(reporte2);

        mockMvc.perform(post("/api/v1/ecomarket/reporte/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"tipo\",\"fecha\":\"2024-10-10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.tipo").value("tipo"))
                .andExpect(jsonPath("$.fecha").value("2024-10-10"));

        verify(reporteService).save(any(Reporte.class));
    }

    @Test
    @DisplayName("PUT /api/v1/ecomarket/reporte/{id}/actualizar Actualiza un reporte y devuelve un 200(ok)")
    void updateReporteByIdTest()throws Exception{
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date fecha = Date.valueOf("2024-10-10");
        when(reporteService.findById(10)).thenReturn(new Reporte(10, "tipo", fecha));
        Reporte updateReporte = new Reporte(10, "tipo actualizado", fecha);
        when(reporteService.save(updateReporte)).thenReturn(updateReporte);

        mockMvc.perform(put("/api/v1/ecomarket/reporte/10/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"tipo actualizado\",\"fecha\":\"2024-10-10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.tipo").value("tipo actualizado"))
                .andExpect(jsonPath("$.fecha").value("2024-10-10"));
    }

    @Test
    @DisplayName("PUT /api/v1/ecomarket/reporte/{id}/actualizar Devuelve un 204 al no encontrar el reporte")
    void updateReporteByIdEmptyTest()throws Exception{
        when(reporteService.findById(10)).thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(put("/api/v1/ecomarket/reporte/10/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipo\":\"tipo actualizado\",\"fecha\":\"2024-10-10\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/ecomarket/reporte/{id}/eliminar Elimina un reporte y devuelve un 204(NoContent)")
    void deleteReporteByIdTest() throws Exception{
        mockMvc.perform(delete("/api/v1/ecomarket/reporte/1/eliminar")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/ecomarket/reporte/{id}/eliminar Devuelve un 404 si no existe el reporte")
    void deleteReporteByIdEmptyTest() throws Exception{
        doThrow(new RuntimeException("No encontrado")).when(reporteService).deleteById(10);

        mockMvc.perform(delete("/api/v1/ecomarket/reporte/10/eliminar")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte/pedidos Devuelve un 200(ok) si encuentra pedidos")
    void findPedidosApiTest()throws Exception{
        PedidoDTO pedido1 = new PedidoDTO();
        PedidoDTO pedido2 = new PedidoDTO();

        when(pedidoDTOService.verPedidos()).thenReturn(Arrays.asList(pedido1,pedido2));
        mockMvc.perform(get("/api/v1/ecomarket/reporte/pedidos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/ecomarket/reporte/pedidos devuelve un 204(NoContent) al no encontrar pedidos")
    void findPedidosApiTestEmpty() throws Exception{
        when(pedidoDTOService.verPedidos()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/ecomarket/reporte/pedidos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
