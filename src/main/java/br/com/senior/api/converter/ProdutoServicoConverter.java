package br.com.senior.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.senior.api.model.input.ProdutoServicoModelInput;
import br.com.senior.api.model.output.ProdutoServicoOutputModel;
import br.com.senior.domain.model.ProdutoServico;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ProdutoServicoConverter {

	private ModelMapper modelMapper;
	
	
	public ProdutoServicoOutputModel toModel(ProdutoServico produtoServico) {
		return modelMapper.map(produtoServico, ProdutoServicoOutputModel.class);
	}
	
	public List<ProdutoServicoOutputModel> toCollectionModel(List<ProdutoServico> produtosServicos){
		return produtosServicos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public ProdutoServico toEntity(ProdutoServicoModelInput produtoServicoInput) {
		return modelMapper.map(produtoServicoInput, ProdutoServico.class);
	}
	
	
	public void copyToDomainObject(ProdutoServicoModelInput produtoServicoInput, ProdutoServico produtoServico) {
		modelMapper.map(produtoServicoInput, produtoServico);
	}
	
	
}
