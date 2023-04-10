package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorConsultaMedico implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		var medicoPossuiOutraConsultaNesseHorario = repository.existsByMedicoIdAndDataAndMotivoIsNull(dados.idMedico(), dados.data());
		if(medicoPossuiOutraConsultaNesseHorario) {
			throw new ValidacaoException("Medico já tem consulta marcada na data!");
		}
	}

}