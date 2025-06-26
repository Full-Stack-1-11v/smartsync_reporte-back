package cl.ecomarket.reporte.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.reporte.model.Reporte;
import cl.ecomarket.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;

/**
 * Servicio que permite gestionar los reportes.
 * Proporciona metodos para listar, buscar por id, guardar y eliminar reportes.
 */
@Service
@Transactional
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    /**
     * Logger de la clase para registrar eventos.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReporteRepository.class);

    /**
     * Metodo que permite listar todos los reportes.
     * @return lista de objetos {@link Reporte}.
     */
    public List<Reporte> findAll(){
        logger.info("[findAll] Inicio.");
        return reporteRepository.findAll();
    }

    /**
     * Metodo que permite buscar un reporte por su ID.
     * @param id Reporte.
     * @return Objeto {@link Reporte}.
     */
    public Reporte findById(Integer id){
        logger.info("[findById] Inicio.");
        return reporteRepository.findById(id).orElse(null);
    }

    /**
     * Metodo que permite guardar un nuevo reporte.
     * @param reporte completo {@link Reporte}.
     * @return Objeto tipo {@link Reporte} creado.
     */
    public Reporte save(Reporte reporte){
        logger.info("[save] Inicio.");
        return reporteRepository.save(reporte);
    }

    /**
     * Metodo que permite buscar y elimiar un reporte por su ID.
     * @param id  {@link Reporte}.
     */
    public void deleteById(Integer id){
        logger.info("[deleteById] Inicio.");
        reporteRepository.deleteById(id);
    }

}
