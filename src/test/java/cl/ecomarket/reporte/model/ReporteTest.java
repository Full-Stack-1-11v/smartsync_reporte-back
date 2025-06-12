package cl.ecomarket.reporte.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ReporteTest {

    @Test
    public void testReporteGetterAndSetters(){
        Reporte reporte = new Reporte();
        Date fecha = Date.valueOf("2025-06-10");
        reporte.setId(1);
        reporte.setTipo("Tipo");
        reporte.setFecha(fecha);

        assertEquals(1, reporte.getId());
        assertEquals("Tipo", reporte.getTipo());
        assertEquals(fecha, reporte.getFecha()); 

    }

    @Test
    public void testEqualsAndHashCode(){
        Date fecha = Date.valueOf("2025-06-10");
        Reporte reporte1 = new Reporte(1, "tipo1", fecha);
        Reporte reporte2 = new Reporte(1, "tipo1", fecha);
        Reporte reporte3 = new Reporte(3, "tipo3", fecha);
        Reporte reporte4 = new Reporte(null, null, fecha);
        Reporte reporte5 = new Reporte(null, null, fecha);
        Reporte reporteVacio1 = new Reporte();
        Reporte reporteVacio2 = new Reporte();

        //Igual a si mismos
        assertEquals(reporte1, reporte1);

        //Objetos iguals y objetos diferentes
        assertEquals(reporte1, reporte2);
        assertNotEquals(reporte1, reporte3);

        //Objetos iguales y objetos diferentes con distinto hashCode
        assertEquals(reporte1.hashCode(), reporte2.hashCode());
        assertNotEquals(reporte1.hashCode(), reporte3.hashCode());

        //Objeto distinto a campo null
        assertNotEquals(reporte1, null);

        //Objeto distinto a otro tipo de datos
        assertNotEquals(reporte1, "Reporte");
        assertNotEquals(reporte1, Integer.valueOf(1));

        //Objetos con campos null
        assertEquals(reporte4, reporte5);
        assertEquals(reporte4.hashCode(), reporte5.hashCode());
        assertNotEquals(reporte4.hashCode(), reporte3.hashCode());

        //Objetos vacios
        assertEquals(reporteVacio1, reporteVacio2);
        assertEquals(reporteVacio1.hashCode(), reporteVacio2.hashCode());
        assertNotEquals(reporteVacio1, reporte1);
        assertNotEquals(reporteVacio1.hashCode(), reporte1.hashCode());
    }

    @Test
    public void testToString(){
        Date fecha = Date.valueOf("2025-06-10");
        Reporte reporte = new Reporte(1, "null", fecha);
        String str = reporte.toString();
        assertNotNull(str);
        assertTrue(str.contains("1"));
        assertTrue(str.contains("null"));
        assertTrue(str.contains("2025-06-10"));

    }

    @Test
    public void testWithDifferentValues(){
        Date fecha1 = Date.valueOf("2025-06-10");
        Date fecha2 = Date.valueOf("2024-06-10");
        //Reportes con diferente fecha
        Reporte repo1 = new Reporte(1, "tipo", fecha1);
        Reporte repo2 = new Reporte(1, "tipo", fecha2);
        assertFalse(repo1.equals(repo2));
        assertFalse(repo2.equals(repo1));

        //Reportes con diferente tipo
        Reporte repo3 = new Reporte(1, "tipo1", fecha1);
        Reporte repo4 = new Reporte(1, "tipo2", fecha1);
        assertFalse(repo3.equals(repo4));
        assertFalse(repo4.equals(repo3));

        //Reportes con diferente ID
        Reporte repo5 = new Reporte(1, "tipo", fecha1);
        Reporte repo6 = new Reporte(2, "tipo", fecha1);
        assertFalse(repo5.equals(repo6));
        assertFalse(repo6.equals(repo5));

        //Reporte completamente diferentes
        Reporte repo7 = new Reporte(7, "tipo7", fecha1);
        Reporte repo8 = new Reporte(8, "tipo8", fecha2);
        assertFalse(repo7.equals(repo8));
        assertFalse(repo8.equals(repo7));
    }


}
