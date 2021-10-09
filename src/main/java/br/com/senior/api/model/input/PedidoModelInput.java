package br.com.senior.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoModelInput {

	
	@NotBlank
	private String desconto;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoInput> itens;

}
