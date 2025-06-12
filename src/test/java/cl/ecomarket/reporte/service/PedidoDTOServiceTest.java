package cl.ecomarket.reporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import cl.ecomarket.reporte.client.PedidoFeignClient;
import cl.ecomarket.reporte.dto.PedidoDTO;

@SpringBootTest
@ActiveProfiles("test")
public class PedidoDTOServiceTest {

    @Autowired
    private PedidoDTOService pedidoDTOService;

    @MockBean
    private PedidoFeignClient pedidoFeignClient;

    @Test
    public void testVerPedidos(){
        List<PedidoDTO> pedidos = new ArrayList<>();

        when(pedidoFeignClient.getPedido()).thenReturn(pedidos);

        List<PedidoDTO> result = pedidoDTOService.verPedidos();
        assertEquals(pedidos, result);
        assertEquals(0, result.size());
    }

}
