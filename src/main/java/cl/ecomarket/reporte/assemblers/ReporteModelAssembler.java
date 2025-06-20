package cl.ecomarket.reporte.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.ecomarket.reporte.controller.ReporteController;
import cl.ecomarket.reporte.model.Reporte;

/*
 * Clase que implementa el ensamblador de modelo para Reporte.
 * Utiliza spring HATEOAS para agregar enlaces a los recursos.
 */
@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>>{

    
    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        Link selfLink = linkTo(methodOn(ReporteController.class).buscarPorId(reporte.getId()))
                .withSelfRel();

        Link allReportes = linkTo(methodOn(ReporteController.class).listar()).withRel("reportes");

        Link crearLink = linkTo(methodOn(ReporteController.class).actualizar(reporte.getId(), reporte))
                .withRel("creat")
                .withType("Post");

        return EntityModel.of(reporte, selfLink, allReportes, crearLink);
    }
}

