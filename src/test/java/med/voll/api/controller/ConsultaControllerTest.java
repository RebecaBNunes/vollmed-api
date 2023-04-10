package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AgendaDeConsultas agendaDeConsultas;
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
	

	@Test
	@DisplayName("deveria devolver codigo http 400 quando as informacoes estiverem invalidas")
	@WithMockUser
	void agendarCenario1() throws Exception {
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas"))
		.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("deveria devolver codigo http 200 com o DTO Detalhamento quando as informacoes estiverem validas")
	@WithMockUser
	void agendarCenario2() throws Exception {
		var data = LocalDateTime.now().plusHours(2);
		var especialidade = Especialidade.DERMATOLOGIA;
		
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
		
		when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);
		
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosAgendamentoConsultaJson.write(
						new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
						).getJson()))
		.andReturn().getResponse();
		
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var jsonEsperado = dadosDetalhamentoConsultaJson
				.write(dadosDetalhamento).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
