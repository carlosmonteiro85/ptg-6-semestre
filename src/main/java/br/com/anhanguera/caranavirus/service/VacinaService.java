package br.com.anhanguera.caranavirus.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.Vacina;
import br.com.anhanguera.caranavirus.enuns.NomeVacinaEnum;
import br.com.anhanguera.caranavirus.enuns.NumeroDosagemEnum;
import br.com.anhanguera.caranavirus.repository.VacinaRepository;

@Service
public class VacinaService {

	private VacinaRepository repository;

	public VacinaService(VacinaRepository vacinaRepository) {
		this.repository = vacinaRepository;
	}

	public void save(Vacina vacina) {
		repository.save(vacina);
	}

	public List<Vacina> findAll() {
		return repository.findAll();
	}

	public boolean validarVacina(User user, Vacina vacina) {
		boolean valido = false;

		if (!user.recebeuDose(vacina.getNumeracaoDose())) {
			if (adicionarVacina(user, vacina)) {
				valido = true;
			}
		}

		return valido;
	}

	public boolean validarDataDose(User user, Vacina vacina) {
		Vacina v = obterUltimaVacinaAplicada(user);
		boolean valido = false;
		LocalDate dataAplicacao = null;
		
		if(v.getId() == null && v.getDataAplicacao() == null ) {
			return valido = true;
		}

		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.PFIZER) ) {
			dataAplicacao = v.getDataAplicacao().plusWeeks(8);
			if (vacina.getDataAplicacao().compareTo(dataAplicacao) >= 0) {
				valido = true;
			}
		}

		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.ASTRAZENECA)) {
			dataAplicacao = v.getDataAplicacao().plusWeeks(12);
			if (vacina.getDataAplicacao().compareTo(dataAplicacao) >= 0) {
				valido = true;
			}
		}
		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.CORONAVAC)) {
			dataAplicacao = v.getDataAplicacao().plusDays(28);
			if (vacina.getDataAplicacao().compareTo(dataAplicacao) >= 0) {
				valido = true;
			}
		}
		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.JANSSEN)) {
			dataAplicacao = v.getDataAplicacao().minusMonths(2);
			if (vacina.getNumeracaoDose().equals(NumeroDosagemEnum.SEGUNDA)) {
				if (vacina.getDataAplicacao().compareTo(dataAplicacao) >= 0) {
					valido = true;
				}
			}
			valido = false;
		}
		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.COVAXIN)) {
			valido = false;
		}

		if (vacina.getMarcaVacina().equals(NomeVacinaEnum.SPUTNIK)) {
			dataAplicacao = v.getDataAplicacao().plusMonths(2);
			if (vacina.getDataAplicacao().compareTo(dataAplicacao) >= 0) {
				valido = true;
			}
		}

		return valido;
	}

	private boolean adicionarVacina(User user, Vacina vacina) {

		boolean adicionado = false;
		
		Vacina v = obterUltimaVacinaAplicada(user);
		
		switch (v.getNumeracaoDose()) {
		case PRIMEIRA:
			vacina.setNumeracaoDose(NumeroDosagemEnum.SEGUNDA);
			user.getVacinas().add(vacina);
			adicionado = true;
			break;
		case SEGUNDA:
			vacina.setNumeracaoDose(NumeroDosagemEnum.TERCEIRA);
			user.getVacinas().add(vacina);
			adicionado = true;
			break;
		case TERCEIRA:
			break;
		case DEFAULT:
			vacina.setNumeracaoDose(NumeroDosagemEnum.PRIMEIRA);
			user.getVacinas().add(vacina);
			adicionado = true;
			break;
		}
		return adicionado;
	}
	
	private Vacina obterUltimaVacinaAplicada(User user) {
		List<Vacina> vacinas = repository.findLastVacina(user.getId());
		
		if(!vacinas.isEmpty()) {
			return  vacinas.get(0);
		}
		return new Vacina();
	}
}
