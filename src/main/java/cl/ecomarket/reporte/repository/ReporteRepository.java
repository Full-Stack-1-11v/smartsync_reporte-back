package cl.ecomarket.reporte.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.ecomarket.reporte.model.Reporte;

/*
 * Repositorio para gestionar el acceso a los datos de la entidad {@link Reporte}
 * Proporciona metodos para realizar consultas personalizadas.
 */
@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer>{

    List<Reporte> findByFecha(Date fecha);

    @Query("SELECT r FROM Reporte r WHERE r.tipo = :tipo")
    List<Reporte> findByTipo(String tipo);  

}
