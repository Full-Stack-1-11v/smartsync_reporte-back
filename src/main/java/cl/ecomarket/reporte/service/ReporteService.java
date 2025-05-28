package cl.ecomarket.reporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.reporte.model.Reporte;
import cl.ecomarket.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> findAll(){return reporteRepository.findAll();}

    public Reporte findById(Integer id){return reporteRepository.findById(id).orElse(null);}

    public Reporte save(Reporte reporte){return reporteRepository.save(reporte);}

    public void deleteById(Integer id){reporteRepository.deleteById(id);}

}
