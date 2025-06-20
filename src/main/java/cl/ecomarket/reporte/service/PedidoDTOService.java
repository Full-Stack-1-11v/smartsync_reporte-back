package cl.ecomarket.reporte.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.reporte.client.PedidoFeignClient;
import cl.ecomarket.reporte.dto.PedidoDTO;

/**
 * Servicio para gestionar la API Pedido llamada desde Feign Client.
 * Permite listar todos los pedidos.
 */
@Service
public class PedidoDTOService {
    
    @Autowired
    private PedidoFeignClient pedidoFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(PedidoDTOService.class);

    /**
     * Metodo que permite listar todos los pedidos.
     * @return lista de objetos {@link PedidoDTO}
     */
    public List<PedidoDTO> verPedidos(){
        logger.info("[verPedidos] Inicio.");
        return pedidoFeignClient.getPedido();
    }
}
