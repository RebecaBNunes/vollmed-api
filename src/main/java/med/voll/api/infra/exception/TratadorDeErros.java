package med.voll.api.infra.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;

@RestControllerAdvice
public class TratadorDeErros {
	
	@ExceptionHandler(EntityNotFoundException.class) //anotação que indica qual exceção esse método irá tratar //EntituNotFound por padrão retorna o código 500
	public ResponseEntity<Void> tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
		
	}
		
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
//		return ResponseEntity.badRequest().body(ex.getMessage());
//	}
//
//	@ExceptionHandler(BadCredentialsException.class)
//	public ResponseEntity tratarErroBadCredentials() {
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
//	}
//
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity tratarErroAuthentication() {
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
//	}
//
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity tratarErroAcessoNegado() {
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity tratarErro500(Exception ex) {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
//	}
}


