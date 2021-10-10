package br.com.senior.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.domain.exception.NegocioException;
import br.com.senior.domain.exception.PedidoNaoEncontradoException;
import br.com.senior.domain.model.Pedido;
import br.com.senior.domain.model.ProdutoServico;
import br.com.senior.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoServicoService produtoServicoService;

	public Pedido buscar(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
		ValidarDesconto(pedido.getDesconto());
		validarItens(pedido);
		pedido.calcularValorTotal();
		return pedidoRepository.save(pedido);
	}

	@Transactional
	private void ValidarDesconto(String desconto) {
		Double valorDesconto = Double.parseDouble(desconto);

		if (valorDesconto < 0 || valorDesconto > 100) {
			throw new NegocioException("Valor do desconto deve ser inferior a 100 e superior a 0");
		}
	}

	@Transactional
	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			ProdutoServico produtoServico = produtoServicoService.buscar(item.getProdutoServico().getId());

			if (produtoServico.isAtivo()) {
				item.setPedido(pedido);
				item.setProdutoServico(produtoServico);
				item.setPrecoUnitario(produtoServico.getPreco());
			} else {
				throw new NegocioException("Produto/Servico não pode ser adicionado, pois está inativo!");
			}
		});

	}

	@Transactional
	public void confirmar(String codigoPedido) {

		Pedido pedido = buscar(codigoPedido);
		pedido.confirmar();
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void cancelar(String codigoPedido) {

		Pedido pedido = buscar(codigoPedido);
		pedido.cancelar();
		pedidoRepository.save(pedido);
	}

	@Transactional
	public void finalizar(String codigoPedido) {

		Pedido pedido = buscar(codigoPedido);
		pedido.finalizar();
		pedidoRepository.save(pedido);

	}

}
