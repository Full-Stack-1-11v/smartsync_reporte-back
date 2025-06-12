package cl.ecomarket.reporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import cl.ecomarket.reporte.model.Reporte;
import cl.ecomarket.reporte.repository.ReporteRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ReporteServiceTest {

    @Autowired
    private ReporteService reporteService;

    @MockBean
    private ReporteRepository reporteRepository;

    @Test
    public void testGetReportes(){
        List<Reporte> reportes = new ArrayList<>();

        when(reporteRepository.findAll()).thenReturn(reportes);

        List<Reporte> result = reporteService.findAll();

        assertEquals(reportes, result);
        assertEquals(0, result.size());

    }

}
