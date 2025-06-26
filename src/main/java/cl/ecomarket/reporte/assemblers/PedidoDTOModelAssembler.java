package cl.ecomarket.reporte.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.ecomarket.reporte.controller.ReporteController;
import cl.ecomarket.reporte.dto.PedidoDTO;
import org.springframework.hateoas.Link;

/**
 *  Clase que implementa el ensamblador de modelo para PedidoDTO.
 *  Utiliza Spring HATEOAS para agregar enlaces a los recursos.
 */
@Component
public class PedidoDTOModelAssembler implements RepresentationModelAssembler<PedidoDTO, EntityModel<PedidoDTO>>{

    @Override
    public EntityModel<PedidoDTO> toModel(PedidoDTO pedidoDTO){
        Link selfLink = linkTo(methodOn(ReporteController.class).listarPedidos())
                .withSelfRel();

        return EntityModel.of(pedidoDTO,selfLink);


    }
}
