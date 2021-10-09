package br.com.senior.api.model.output;

import java.math.BigDecimal;

import br.com.senior.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoServicoOutputModel {

	private Long id;
	private String codigo;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Type tipo;

}
