package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Endereco {
	
	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.cidade = dados.cidade();
		this.complemento = dados.complemento();
		this.numero = dados.numero();
		this.uf = dados.uf();
	}

	private String logradouro;
	private String bairro;
	private String complemento;
	private String cep;
	private String numero;
	private String cidade;
	
	@Enumerated(EnumType.STRING)
	private Uf uf;

	public void atualizarInformacoes(DadosEndereco endereco) {
		if(endereco.logradouro() != null) {
			this.logradouro = endereco.logradouro();
		}
		if(endereco.bairro() != null) {
			this.bairro = endereco.bairro();
		}
		if(endereco.cep() != null) {
			this.cep = endereco.cep();
		}
		if(endereco.uf() != null) {
			this.uf = endereco.uf();
		}
		if(endereco.cidade() != null) {
			this.cidade = endereco.cidade();
		}
		if(endereco.complemento() != null) {
			this.complemento = endereco.complemento();
		}
		if(endereco.numero() != null) {
			this.logradouro = endereco.numero();
		}
		
	}
	
	
}
