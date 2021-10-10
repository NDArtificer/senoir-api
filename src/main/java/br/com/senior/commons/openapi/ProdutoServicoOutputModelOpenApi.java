package br.com.senior.commons.openapi;

import java.util.List;

import br.com.senior.api.model.output.ProdutoServicoOutputModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProdutoServicoOutputModel")
@Getter
@Setter
public class ProdutoServicoOutputModelOpenApi {

	private List<ProdutoServicoOutputModel> content;
	private Long size;
	private Long totalElements;
	private Long totalPages;
	private Long number;
}
