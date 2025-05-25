package cl.ecomarket.reporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.reporte.dto.PedidoDTO;
import cl.ecomarket.reporte.model.Reporte;
import cl.ecomarket.reporte.service.PedidoDTOService;
import cl.ecomarket.reporte.service.ReporteService;

@RestController
@RequestMapping("/api/v1/ecomarket/reporte")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private PedidoDTOService pedidoDTOService;

    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoDTO>> listarPedidos(){
        List<PedidoDTO> pedidos = pedidoDTOService.verPedidos();
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping
    public ResponseEntity<List<Reporte>>listar(){
        List<Reporte> reportes = reporteService.findAll();
        if(reporteService.findAll().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> buscarPorId(@PathVariable Integer id) {
        try {
            Reporte reporte = reporteService.findById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Reporte> guardar(@PathVariable Integer id) {
        try {
            Reporte reporte = reporteService.findById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(@PathVariable Integer id, @RequestBody Reporte reporte){
        try {
            Reporte rep = reporteService.findById(id);
            rep.setId(id);
            rep.setTipo(reporte.getTipo());
            rep.setFecha(reporte.getFecha());

            reporteService.save(rep);
            return ResponseEntity.ok(rep);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            reporteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }





}
