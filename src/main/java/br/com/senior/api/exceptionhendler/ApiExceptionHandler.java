package br.com.senior.api.exceptionhendler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.senior.domain.exception.EntidadeEmUsoException;
import br.com.senior.domain.exception.EntidadeNaoEncontradaException;
import br.com.senior.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		Problem problem = new Problem();
		List<Problem.Field> fields = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

			fields.add(new Problem.Field(name, message));
		}

		problem.setStatus(status.value());
		problem.setTimeStamp(OffsetDateTime.now());
		problem.setTitle("One or more properties are invalid, correct them and try again!");
		problem.setFields(fields);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleBusinessExecption(NegocioException ex, WebRequest request ){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = new Problem();		
		problem.setStatus(status.value());
		problem.setTimeStamp(OffsetDateTime.now());
		problem.setTitle(ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntidadeNaoEncontradaException ex, WebRequest request ){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problem problem = new Problem();		
		problem.setStatus(status.value());
		problem.setTimeStamp(OffsetDateTime.now());
		problem.setTitle(ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		Problem problem = new Problem();		
		problem.setStatus(status.value());
		problem.setTimeStamp(OffsetDateTime.now());
		problem.setTitle(ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

}
