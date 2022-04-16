package br.com.anhanguera.caranavirus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.Vacina;
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

	private boolean adicionarVacina(User user, Vacina vacina) {

		boolean adicionado = false;

		NumeroDosagemEnum numeracaoDoseRecebida = NumeroDosagemEnum.DEFAULT;

		for (Vacina varc : user.getVacinas()) {
			if (varc.getNumeracaoDose() != NumeroDosagemEnum.DEFAULT) {
				numeracaoDoseRecebida = varc.getNumeracaoDose();
			}

		}
		switch (numeracaoDoseRecebida) {
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
}
