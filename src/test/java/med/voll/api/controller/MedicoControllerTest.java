package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.endereco.Uf;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.MedicoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;
	 
	@Autowired
	private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;
	
	@MockBean
	private MedicoRepository medicoRepository;
	
	@Test
	@DisplayName("deveria devolver codigo HTTP 400 quando as informacoes estiverem invalidas")
	@WithMockUser
	void testCadastrarMedicoCenario1() throws Exception {
		var response = mvc.perform(MockMvcRequestBuilders.post("/medicos"))
		.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@DisplayName("deveria devolver codigo HTTP 200 e DTO de detalhamento do medico criado quando as informacoes estiverem invalidas")
	@WithMockUser
	void testCadastrarMedicoCenario2() throws Exception {
		 var dadosCadastro = new DadosCadastroMedico(
		            "Medico",
		            "medico@voll.med",
		            "61999999999",
		            "123456",
		            Especialidade.CARDIOLOGIA,
		            cadastrarEndereco());
		var response = mvc.perform(MockMvcRequestBuilders.post("/medicos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosCadastroMedicoJson.write(
						dadosCadastro)
						.getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		
		var jsonEsperado = dadosDetalhamentoMedicoJson.write(new DadosDetalhamentoMedico(null, "Medico", "123456", "61999999999",  Especialidade.CARDIOLOGIA, new Endereco(cadastrarEndereco()) )).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	
	}
	private DadosEndereco cadastrarEndereco() {
		return new DadosEndereco(
				"rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            Uf.DF,
	            null,
	            null);
	}
	
}
