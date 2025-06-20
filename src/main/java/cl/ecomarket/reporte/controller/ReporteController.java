package cl.ecomarket.reporte.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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

import cl.ecomarket.reporte.assemblers.PedidoDTOModelAssembler;
import cl.ecomarket.reporte.assemblers.ReporteModelAssembler;
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

/**
 * Controladores del tipo REST para gestionar los reportes.
 * Proporciona endpoints del tipo listar, buscar por id, guardar, actualizar y eliminar.
 */
@RestController
@RequestMapping("/api/v1/ecomarket/reporte")
@Tag(name = "Reportes", description = "Operaciones relacionadas con los reportes")
public class ReporteController {

    /**
     * Services para gestionar reportes.
     */
    @Autowired
    private ReporteService reporteService;

    /**
     * Services para gestionar Pedidos.
     */
    @Autowired
    private PedidoDTOService pedidoDTOService;

    /**
     * Assembler para implementar HATEOAS a los metodos REST de Reporte.
     */
    @Autowired
    private ReporteModelAssembler reporteAssembler;

    /**
     * Assembler para implementar HATEOAS al metodo REST de Pedido.
     */
    @Autowired
    private PedidoDTOModelAssembler dtoAssembler;

    /**
     * Logger de la clase para registar eventos y errores.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);

    /**
     * Metodo Rest del tipo GET.
     * Llama a la API Pedidos y obtiene una lista de todos los pedidos.
     * @return lista de pedidos {@link PedidoDTO}.
     */
    @GetMapping("/pedidos")
    @Operation(summary = "Obtener pedidos"  , description = "Obtiene una lista de los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos listados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PedidoDTO.class))),
                @ApiResponse(responseCode = "204", description = "Pedidos vacios")})
    public ResponseEntity<List<EntityModel<PedidoDTO>>> listarPedidos(){
        logger.info("[listarPedidos] Inicio.");
        List<EntityModel<PedidoDTO>> pedidos = pedidoDTOService.verPedidos().stream().map(dtoAssembler::toModel)
                .collect(Collectors.toList());
        if(pedidos.isEmpty()){
            logger.info("No se encontraron Pedidos.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Pedidos listados.");
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Metodo Rest del tipo GET.
     * Obtiene una lista de todos los reportes de la API.
     * @return lista de objetos {@link Reporte}.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los reportes", description = "Obtiene una lista de todos los reportes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes listados",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "204", description = "Reportes vacios")})
    public ResponseEntity<List<EntityModel<Reporte>>>listar(){
        logger.info("[listar] Inicio.");
        List<EntityModel<Reporte>> reportes = reporteService.findAll().stream().map(reporteAssembler::toModel)
        .collect(Collectors.toList());
        if(reporteService.findAll().isEmpty()){
            logger.warn("No se encontraron Reportes.");
            return ResponseEntity.noContent().build();
        }
        logger.info("[listar] Fin.");
        return ResponseEntity.ok(reportes);
    }

    /**
     * Metodo Rest del tipo GET.
     * Busca un reporte por su ID.
     * @param id reporte.
     * @return Objeto del tipo {@link Reporte}.
     */
    @GetMapping("/{id}/buscar")
    @Operation(summary = "Obtener reporte por su ID", description = "Busca y obtiene un reporte por su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
    public ResponseEntity<EntityModel<Reporte>> buscarPorId(@PathVariable Integer id) {
        logger.info("[buscarPorId] Inicio.");
        try {
            Reporte reporte = reporteService.findById(id);
            EntityModel<Reporte> reporteModel = reporteAssembler.toModel(reporte);
            logger.info("Se encontro el reporte: {}, con ID: {}", reporte.getTipo(),id);
            return ResponseEntity.ok(reporteModel);
        } catch (Exception e) {
            logger.error("Error: {}", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo Rest del tipo POST.
     * Crea un objeto y lo guarda en la base de datos.
     * @param reporte completo del Reporte {@link Reporte}.
     * @return Objeto tipo {@link Reporte} Creado.
     */
    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo reporte", description = "Guarda un reporte nuevo en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reporte guardado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Reporte.class)))})
    public ResponseEntity<EntityModel<Reporte>> guardar(@RequestBody Reporte reporte){
        logger.info("[guardar] Inicio.");
        Reporte nuevoReporte = reporteService.save(reporte);
        logger.info("Reporte nuevo creado.");
        EntityModel<Reporte> reporteModel = reporteAssembler.toModel(nuevoReporte);
        logger.info("[guardar] Fin.");
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteModel);
    }

    /**
     * Metodo Rest del tipo PUT.
     * Busca un Reporte por su ID y lo actualiza a travez de su cuerpo.
     * @param id Reporte.
     * @return Objeto tipo {@link Reporte} actualizado.
     */
    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualiza un reporte", description = "Actualiza un reporte existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte actualizado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Reporte.class))),
                @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
    public ResponseEntity<EntityModel<Reporte>> actualizar(@PathVariable Integer id, @RequestBody Reporte reporte){
        logger.info("[actualizar] Inicio.");
        try {
            Reporte rep = reporteService.findById(id);
            rep.setId(id);
            rep.setTipo(reporte.getTipo());
            rep.setFecha(reporte.getFecha());
            reporteService.save(rep);
            EntityModel<Reporte> reporteModel = reporteAssembler.toModel(rep);
            logger.info("[actualizar] Fin.");
            return ResponseEntity.ok(reporteModel);
        } catch (Exception e) {
            logger.error("Error: {}", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo Rest del tipo DELETE.
     * Busca un reporte por su ID y lo elimina.
     * @param id producto.
     */
    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar un reporte", description = "Busca y elimina un reporte por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")})
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        logger.info("[eliminar] Inicio.");
        try {
            reporteService.deleteById(id);
            logger.info("Reporte con ID: {}, eliminado",id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error: {}", e);
            return ResponseEntity.notFound().build();
        }
    }





}
