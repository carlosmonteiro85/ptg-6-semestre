package br.com.anhanguera.caranavirus.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.anhanguera.caranavirus.enuns.NomeVacinaEnum;
import br.com.anhanguera.caranavirus.enuns.NumeroDosagemEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Vacina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private NumeroDosagemEnum numeracaoDose = NumeroDosagemEnum.DEFAULT;
	@Column(name = "nome_vacina")
	@Enumerated(EnumType.STRING)
	private NomeVacinaEnum marcaVacina;
	@Column(name = "data_aplicacao")
	private LocalDate dataAplicacao;
	
	public Vacina(NumeroDosagemEnum numeracaoDose, NomeVacinaEnum marcaVacina, LocalDate dataAplicacao) {
		this.numeracaoDose = numeracaoDose;
		this.marcaVacina = marcaVacina;
		this.dataAplicacao = dataAplicacao;
	}
	
}
