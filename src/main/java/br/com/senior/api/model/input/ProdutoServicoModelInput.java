package br.com.senior.api.model.input;

import java.math.BigDecimal;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.sun.istack.NotNull;

import br.com.senior.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoServicoModelInput {

	@NotBlank
	private String nome;

	@NotBlank
	private String descricao;

	@NotNull
	@PositiveOrZero
	private BigDecimal preco;

	@Enumerated
	private Type tipo;

}
