package br.com.anhanguera.caranavirus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "endereco_id")
	private Long id;
	@Column(name = "cep")
	private String numeroCEP;
	private String logradouro;
	private String complemento;
	private String bairro;
	@Column(name = "cidade")
	private String localidade;
	private String uf;
	
	public Adress(String numeroCEP, String logradouro, String complemento, String bairro, String localidade, String uf) {
		this.numeroCEP = numeroCEP;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}
	
	public String concatenarEndereco() {
		return bairro+" "+ localidade+" "+ uf;
	}
}
