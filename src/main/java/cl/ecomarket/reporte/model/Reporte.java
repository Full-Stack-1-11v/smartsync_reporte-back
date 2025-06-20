package cl.ecomarket.reporte.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad que representa los Reportes del sistema.
 * Contiene informacion de los reportes tales como ID Reporte,
 * Tipo de reporte y fecha del reporte.
 */
@Entity
@Table(name = "reporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
    
    /*
     * ID unico del reporte,
     * es generado automaticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Tipo del reporte.
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private String tipo;

    /*
     * Fecha del reporte.
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private Date fecha;


}
