package br.com.senior.commons.openapi;

import java.util.List;

import br.com.senior.api.model.output.PedidoOutputModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidoOutputModel")
@Getter
@Setter
public class PedidoOutputModelOpenApi {

	private List<PedidoOutputModel> content;
	private Long size;
	private Long totalElements;
	private Long totalPages;
	private Long number;

}
