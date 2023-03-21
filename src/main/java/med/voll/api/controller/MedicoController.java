package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired //indica para o spring boot que quem vai instanciar essa classe eh ele //injecao de dependencias
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		Medico medico = new Medico(dados);
		repository.save(medico);
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); //o uriBuilder retorna apenas a uri do host por isso eh necessarop colocar o recurso 
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
		
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listarMedico(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional //preciso realizar transações no database
	public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		Medico medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //DTO criado para retornar todos os dados do médico após a atualização
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluirMedico(@PathVariable Long id) { //anotacao que diz que esse parametro vem do caminho da url
		Medico medico = repository.getReferenceById(id);
		medico.excluir();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) { //anotacao que diz que esse parametro vem do caminho da url
		Medico medico = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	
	
}
