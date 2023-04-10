package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores; //instancia a lista e adiciona as classes que implementam essa interface
	
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
	
	public DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dadosConsulta) {
		if(!pacienteRepository.existsById(dadosConsulta.idPaciente())) {
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if(dadosConsulta.idMedico() != null && !medicoRepository.existsById(dadosConsulta.idMedico())) {
			throw new ValidacaoException("Id do medico informado não existe!");
		}
		
		validadores.forEach(v -> v.validar(dadosConsulta));
		
	 	var medico = escolherMedico(dadosConsulta);
	 	if(medico == null) {
	 		throw new ValidacaoException("Não existe médico disponível nessa data!");
	 	}
	 	var paciente = pacienteRepository.getReferenceById(dadosConsulta.idPaciente());
		Consulta consulta = new Consulta(null, medico, paciente, dadosConsulta.data(), null);
		consultaRepository.save(consulta);
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
		}
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}

	public void cancelar(@Valid DadosCancelamentoConsulta dadosCancelamento) {
		if(!consultaRepository.existsById(dadosCancelamento.idConsulta())) {
			throw new ValidacaoException("Id da consulta não existe no banco de dados");
		}
		
		validadoresCancelamento.forEach(v -> v.validar(dadosCancelamento));
		
		Consulta consulta = consultaRepository.getReferenceById(dadosCancelamento.idConsulta());
		consulta.cancelar(dadosCancelamento.motivoCancelamento());
		
		
	}
	
}
