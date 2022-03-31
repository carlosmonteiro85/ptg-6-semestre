package br.com.anhanguera.caranavirus.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.anhanguera.caranavirus.enuns.NumeroDosagemEnum;
import br.com.anhanguera.caranavirus.enuns.TipoSanguinioEnum;
import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "endereco_id")
	private Adress endereco;
	private String telefone;
	private String celular;
	private String profissao;
	@Enumerated(EnumType.STRING)
	private TipoSanguinioEnum tipoSanguinio;
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	@JoinColumn(name="vacina_id")
	private List<Vacina> vacinas = new LinkedList<>();
	
	public String concatenarEndereco() {
		return endereco.getLogradouro()+" "+ endereco.getLocalidade()+" "+ endereco.getUf();
	}
	
	public boolean recebeuDose(NumeroDosagemEnum numeroDose) {
		
		boolean confirmacaoVacina = false;
		
		for (Vacina vacina : vacinas) {
			if(vacina.getNumeracaoDose().equals(numeroDose)) {
				confirmacaoVacina = true;
				break;
			}
		}
		return confirmacaoVacina;
	}
	
	
}
