package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component
public class ValidadorAntecedenciaCancelamento implements ValidadorCancelamentoDeConsulta{
	
	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DadosCancelamentoConsulta dados) {
		var data = repository.findDatabyId(dados.idConsulta());
		var agora = LocalDateTime.now();
		var horarioDeAntecedencia = Duration.between(agora, data).toHours();
		if(horarioDeAntecedencia < 24) {
			throw new ValidacaoException("O cancelamento só pode ser efetuado com pelo menos 24h de antecedencia!");
		}
	}
}
