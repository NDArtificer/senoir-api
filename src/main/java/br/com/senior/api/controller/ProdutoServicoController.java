package br.com.senior.api.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.api.converter.ProdutoServicoConverter;
import br.com.senior.api.model.input.ProdutoServicoModelInput;
import br.com.senior.api.model.output.ProdutoServicoOutputModel;
import br.com.senior.domain.exception.EntidadeEmUsoException;
import br.com.senior.domain.exception.ProdutoNaoEncontradoException;
import br.com.senior.domain.model.ProdutoServico;
import br.com.senior.domain.repository.ProdutoServicoRepository;
import br.com.senior.domain.service.ProdutoServicoService;
import io.swagger.annotations.Api;

@Api(tags = "ProdutoServico")
@RestController
@RequestMapping("/produtoServico")
public class ProdutoServicoController {

	@Autowired
	private ProdutoServicoRepository produtoServicoRepository;

	@Autowired
	private ProdutoServicoService produtoServicoService;

	@Autowired
	private ProdutoServicoConverter produtoServicoConverter;

	@GetMapping
	private Page<ProdutoServicoOutputModel> listar(@PageableDefault(size = 5) Pageable pageable) {
		Page<ProdutoServico> produtoServicoPage = produtoServicoRepository.findAll(pageable);

		List<ProdutoServicoOutputModel> produtoServicoList = produtoServicoConverter
				.toCollectionModel(produtoServicoPage.getContent());

		Page<ProdutoServicoOutputModel> produtoServicoModel = new PageImpl<>(produtoServicoList, pageable,
				produtoServicoPage.getTotalElements());

		return produtoServicoModel;
	}

	@GetMapping("/{codigo}")
	private ProdutoServico buscar(@PathVariable String codigo) {
		return produtoServicoService.buscar(codigo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private ProdutoServicoOutputModel adcionar(@RequestBody @Valid ProdutoServicoModelInput produtoServicoInput) {

		ProdutoServico produtoServico = produtoServicoConverter.toEntity(produtoServicoInput);
		return produtoServicoConverter.toModel(produtoServicoService.salvar(produtoServico));
	}

	@PutMapping("/{codigo}")
	public ProdutoServicoOutputModel atualizar(@PathVariable String codigo,
			@RequestBody @Valid ProdutoServicoModelInput produtoServicoInput) {

		ProdutoServico produtoServicoAtual = produtoServicoService.buscar(codigo);

		produtoServicoConverter.copyToDomainObject(produtoServicoInput, produtoServicoAtual);

		produtoServicoAtual = produtoServicoService.salvar(produtoServicoAtual);

		return produtoServicoConverter.toModel(produtoServicoAtual);

	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable String codigo) {
		try {
			produtoServicoService.excluir(codigo);
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(String.format("Codigo de produto %s inexistente!", codigo));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Produto de codigo %s não pode ser excluido, pois está em uso", codigo));
		} catch (ConstraintViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Produto de codigo %s não pode ser excluido, pois está em uso", codigo));
		}
	}

	@PutMapping("/{codigo}/ativacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable String codigo) {
		produtoServicoService.ativar(codigo);
	}
	
	@DeleteMapping("/{codigo}/inativacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable String codigo) {
		produtoServicoService.inativar(codigo);
	}	
	
	
}
