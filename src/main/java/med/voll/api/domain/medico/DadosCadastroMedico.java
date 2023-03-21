package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
		
		@NotBlank(message = "nome é obrigatório")
		String nome, 
		@NotBlank
		@Email
		String email, 
		@NotBlank
		@Pattern(regexp = "\\d{9,11}")
		String telefone,
		@NotBlank
		@Pattern(regexp = "\\d{4,6}")
		String crm, 
		@NotNull
		Especialidade especialidade, 
		@Valid
		@NotNull
		DadosEndereco endereco) {
	
}
