package br.com.senior.api.exceptionhendler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private Integer status;
	private OffsetDateTime timeStamp;
	private String title;
	private List<Field> fields;

	
	@Getter
	@AllArgsConstructor
	public static class Field{
		
		private String name;
		private String message;
		
	} 
}
