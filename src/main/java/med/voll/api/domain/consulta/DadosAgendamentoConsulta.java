package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

public record DadosAgendamentoConsulta(
		
		@JsonAlias({"id-medico", "id-do-medico"})
		Long idMedico,
		
		@NotNull
		@JsonAlias({"id-paciente", "id-do-paciente"})
		Long idPaciente,
		
		@NotNull
		@Future //data de hoje pra frente
		//@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime data,
		
		Especialidade especialidade) {
	
}
