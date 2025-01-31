package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorConsultaPaciente implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime primeiroHorario = dados.data().withHour(7);
		LocalDateTime ultimoHorario = dados.data().withHour(18);
		var pacienteTemConsultaNaData = repository.existsByPacienteIdAndDataBetweenAndMotivoIsNull(dados.idPaciente(), primeiroHorario, ultimoHorario);
		if(pacienteTemConsultaNaData) {
			throw new ValidacaoException("paciente já tem consulta na data!");
		}
	}
	
}
