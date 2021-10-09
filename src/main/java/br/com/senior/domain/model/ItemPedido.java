package br.com.senior.domain.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import br.com.senior.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String codigo;
	@Column
	private BigDecimal precoUnitario;
	@Column
	private BigDecimal precoTotal;
	@Column
	private Integer quantidade;
	@Column
	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private ProdutoServico produtoServico;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

	public void calcularPrecoTotal() {

		var mathCoontext = new MathContext(2, RoundingMode.HALF_UP);
		Double valorDesconto = Double.parseDouble(pedido.getDesconto());
		valorDesconto = valorDesconto / 100;

		BigDecimal precoUnitario = this.getPrecoUnitario();

		Integer quantidade = this.getQuantidade();

		if (precoUnitario == null) {
			precoUnitario = BigDecimal.ZERO;
		}

		if (quantidade == null) {
			quantidade = 0;
		}

		if (produtoServico.getTipo().equals(Type.PRODUTO)) {
			precoUnitario = precoUnitario.multiply(new BigDecimal(1 - valorDesconto).round(mathCoontext));
			precoUnitario = precoUnitario.setScale(2, RoundingMode.HALF_UP);
		}

		this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}

}
