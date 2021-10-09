package br.com.senior.commons;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.senior.api.model.input.ItemPedidoInput;
import br.com.senior.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		return modelMapper;
	}

}
