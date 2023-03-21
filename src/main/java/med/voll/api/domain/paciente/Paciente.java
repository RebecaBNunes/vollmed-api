package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Paciente { //Entidade JPA
	
	public Paciente(DadosCadastroPaciente dadosPaciente) {
		this.ativo = true;
		this.nome = dadosPaciente.nome();
		this.email = dadosPaciente.email();
		this.cpf = dadosPaciente.cpf();
		this.telefone = dadosPaciente.telefone();
		this.endereco = new Endereco(dadosPaciente.endereco());
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf; //poderia ser minha PK
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo;
	
	public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
		if(this.ativo == false) {
			throw new NullPointerException("paciente não encontrado!");
		}
		if(dados.nome() != null) {
			this.nome = dados.nome();
		} if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		} if(dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public void inativar() {
		this.ativo = false;
	}
	
}
