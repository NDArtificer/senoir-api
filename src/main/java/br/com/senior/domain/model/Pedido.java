package br.com.senior.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import br.com.senior.domain.enums.StatusPedido;
import br.com.senior.domain.exception.NegocioException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pedido extends AbstractAggregateRoot<Pedido> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String codigo;

	@Column
	private BigDecimal subtotal;

	@Column
	private BigDecimal valorTotal;

	@Column 
	private String desconto;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;

	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataConfirmacao;

	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;


	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;


	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calcularPrecoTotal);

		this.subtotal = getItens().stream().map(item -> item.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal;
	}



	public void atribuirPedidsAosItens() {
		getItens().forEach(item -> item.setPedido(this));
	}

	
	public Boolean canConfirm() {
		return getStatus().podeAleterarPara(StatusPedido.CONFIRMADO);
	}
	
	
	public Boolean canCancel() {
		return getStatus().podeAleterarPara(StatusPedido.CANCELADO);
	}
	
	public boolean canDelivery() {
		return getStatus().podeAleterarPara(StatusPedido.FINALIZADO);
	}
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
	}

	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
	}

	public void finalizar() {
		setStatus(StatusPedido.FINALIZADO);
		setDataEntrega(OffsetDateTime.now());
	}

	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAleterarPara(novoStatus)) {
			throw new NegocioException(String.format("O status do pedido %s não pode ser alterado de %s para %s!",
					getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
		}

		this.status = novoStatus;
	}

	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
