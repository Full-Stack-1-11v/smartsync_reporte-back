package cl.ecomarket.reporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para representar un pedido.
 * Contiene los campos necesarios para la transferencia de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    /**
     * Pedido ID.
     */
    private Long pedidoId;

    /**
     * Estado pedido.
     */
    private boolean estadoPedido;

    /**
     * Fecha pedido.
     */
    private String fechaPedido;

}
