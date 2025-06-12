package cl.ecomarket.reporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
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
        //Given
        List<Reporte> reportes = new ArrayList<>();

        //When
        when(reporteRepository.findAll()).thenReturn(reportes);

        //Then
        List<Reporte> result = reporteService.findAll();

        assertEquals(reportes, result);
        assertEquals(0, result.size());

    }

    @Test    
    public void testGetReporteById(){
        Integer idReporte = 1;
        Reporte reporte = new Reporte();
        reporte.setId(idReporte);

        when(reporteRepository.findById(idReporte)).thenReturn(java.util.Optional.of(reporte));

        Reporte result = reporteService.findById(idReporte);
        assertEquals(reporte, result);

    }

    @Test
    public void testSaveReporte(){
        Integer idReporte = 1;
        String tipo = "Tipo de Reporte";
        Date fecha = new Date(System.currentTimeMillis());
        Reporte reporteSave = new Reporte();
        reporteSave.setId(idReporte);
        reporteSave.setTipo(tipo);
        reporteSave.setFecha(fecha);

        when(reporteRepository.save(reporteSave)).thenReturn(reporteSave);

        Reporte resultadoSave = reporteService.save(reporteSave);
        assertEquals(reporteSave, resultadoSave);

    }

    @Test
    public void testDeleteReporte(){
        //Given
        Integer idDelete = 1;
        
        //When
        reporteService.deleteById(idDelete);

        //Then
        verify(reporteRepository, times(1)).deleteById(idDelete);
    }

}
