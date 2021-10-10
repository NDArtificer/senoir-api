package br.com.senior.commons;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModel {

	private Integer page;
	private Integer size;
	private List<String> sort;
	
}
