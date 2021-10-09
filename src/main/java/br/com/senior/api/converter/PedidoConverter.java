package br.com.senior.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.senior.api.model.input.PedidoModelInput;
import br.com.senior.api.model.output.PedidoOutputModel;
import br.com.senior.domain.model.Pedido;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoConverter {

	private ModelMapper modelMapper;
	
	
	public PedidoOutputModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoOutputModel.class);
	}
	
	public List<PedidoOutputModel> toCollectionModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Pedido toEntity(PedidoModelInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	
	public void copyToDomainObject(PedidoModelInput pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);
	}
	
	
}
