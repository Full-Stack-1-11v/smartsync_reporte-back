package cl.ecomarket.reporte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ecomarket/reporte")
@Tag(name = "Reportes", description = "Operaciones relacionadas con los reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private PedidoDTOService pedidoDTOService;

    @GetMapping("/pedidos")
    @Operation(summary = "Obtener pedidos", description = "Obtiene una lista de los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos listados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PedidoDTO.class))),
                @ApiResponse(responseCode = "204", description = "Pedidos vacios")})
    public ResponseEntity<List<PedidoDTO>> listarPedidos(){
        List<PedidoDTO> pedidos = pedidoDTOService.verPedidos();
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes", description = "Obtiene una lista de todos los reportes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes listados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "204", description = "Reportes vacios")})
    public ResponseEntity<List<Reporte>>listar(){
        List<Reporte> reportes = reporteService.findAll();
        if(reporteService.findAll().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}/buscar")
    @Operation(summary = "Obtener reporte por su ID", description = "Busca y obtiene un reporte por su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
    public ResponseEntity<Reporte> buscarPorId(@PathVariable Integer id) {
        try {
            Reporte reporte = reporteService.findById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo reporte", description = "Guarda un reporte nuevo en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reporte guardado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Reporte.class)))})
    public ResponseEntity<Reporte> guardar(@RequestBody Reporte reporte){
        Reporte nuevoReporte = reporteService.save(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
    }

    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualiza un reporte", description = "Actualiza un reporte existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte actualizado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
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

    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar un reporte", description = "Busca y elimina un reporte por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            reporteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }





}
