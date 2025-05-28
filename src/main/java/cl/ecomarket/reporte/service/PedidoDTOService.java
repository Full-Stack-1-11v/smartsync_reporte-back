package cl.ecomarket.reporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.reporte.client.PedidoFeignClient;
import cl.ecomarket.reporte.dto.PedidoDTO;

@Service
public class PedidoDTOService {
    
    @Autowired
    private PedidoFeignClient pedidoFeignClient;

    public List<PedidoDTO> verPedidos(){return pedidoFeignClient.getPedido();}
}
