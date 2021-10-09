package br.com.senior.api.model.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoOutputModel {

	private Long produtoId;
	private String produtoNome;
	private String codigo;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private Integer quantidade;
	private String observacao;

}
