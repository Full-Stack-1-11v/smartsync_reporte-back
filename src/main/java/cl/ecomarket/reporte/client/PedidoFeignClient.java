package cl.ecomarket.reporte.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cl.ecomarket.reporte.dto.PedidoDTO;

@FeignClient(name = "Pedido-Api", url = "https://smartsync-pedido-back.onrender.com")
public interface PedidoFeignClient {

    @GetMapping("/api/pedidos/listar")
    List<PedidoDTO> getPedido();

}
