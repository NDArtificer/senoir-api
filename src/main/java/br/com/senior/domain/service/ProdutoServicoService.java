package br.com.senior.domain.service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.senior.domain.enums.Type;
import br.com.senior.domain.exception.EntidadeEmUsoException;
import br.com.senior.domain.exception.NegocioException;
import br.com.senior.domain.exception.ProdutoNaoEncontradoException;
import br.com.senior.domain.model.ProdutoServico;
import br.com.senior.domain.repository.ProdutoServicoRepository;

@Service
public class ProdutoServicoService {

	@Autowired
	private ProdutoServicoRepository produtoServicoRepository;

	@Transactional
	public ProdutoServico buscar(String codigo) {

		return produtoServicoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado, código inexistente!"));

	}

	@Transactional
	public ProdutoServico salvar(ProdutoServico produtoServico) {
		if (produtoServico.getTipo().equals(Type.PRODUTO) || produtoServico.getTipo().equals(Type.SERVICO)) {
			return produtoServicoRepository.save(produtoServico);
		} else {
			throw new NegocioException("O tipo informado nao coincide com produto ou servico");
		}
	}

	@Transactional
	public void excluir(String codigo) {
		try {
			ProdutoServico produtoServico = buscar(codigo);
			produtoServicoRepository.deleteById(produtoServico.getId());
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(String.format("Codigo de produto %s inexistente!", codigo));
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Produto de codigo %s não pode ser excluido, pois está em uso", codigo));
		} catch (ConstraintViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Produto de codigo %s não pode ser excluido, pois está em uso", codigo));
		}
		

	}

	public ProdutoServico buscar(Long id) {
		return produtoServicoRepository.findById(id)
				.orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado, código inexistente!"));
	}

	@Transactional
	public void ativar(String codigo) {
		ProdutoServico produtoServico = buscar(codigo);
		produtoServico.ativar();
	}

	@Transactional
	public void inativar(String codigo) {
		ProdutoServico produtoServico = buscar(codigo);
		produtoServico.inativar();
		
	}

}
