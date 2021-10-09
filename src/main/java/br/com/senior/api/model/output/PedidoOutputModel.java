package br.com.senior.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoOutputModel {

	private Long id;
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal valorTotal;
	private String desconto;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private String status;
	private List<ItemPedidoOutputModel> itens;

}
