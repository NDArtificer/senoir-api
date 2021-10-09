package br.com.senior.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.api.converter.PedidoConverter;
import br.com.senior.api.model.input.PedidoModelInput;
import br.com.senior.api.model.output.PedidoOutputModel;
import br.com.senior.domain.exception.NegocioException;
import br.com.senior.domain.model.Pedido;
import br.com.senior.domain.repository.PedidoRepository;
import br.com.senior.domain.service.PedidoService;
import io.swagger.annotations.Api;

@Api(tags = "Pedidos")
@RestController
@RequestMapping(value = "/pedidos")
public class PedidosController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoConverter pedidoConverter;

	@GetMapping
	public List<PedidoOutputModel> listar() {

		return pedidoConverter.toCollectionModel(pedidoRepository.findAll());

	}

	@GetMapping("/{codigo}")
	public PedidoOutputModel buscar(@PathVariable String codigo) {

		return pedidoConverter.toModel(pedidoService.buscar(codigo));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoOutputModel emitir(@RequestBody @Valid PedidoModelInput pedidoInput) {
		
		try {
			Pedido pedido = pedidoConverter.toEntity(pedidoInput);
			return pedidoConverter.toModel(pedidoService.emitir(pedido));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	} 
	
	@PutMapping("/{codigo}/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigo) {
		pedidoService.confirmar(codigo);

	}
	
	
	@PutMapping("/{codigo}/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigo) {
		pedidoService.cancelar(codigo);

	}
	
	@PutMapping("/{codigo}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable String codigo) {
		pedidoService.finalizar(codigo);

	}
	
}
