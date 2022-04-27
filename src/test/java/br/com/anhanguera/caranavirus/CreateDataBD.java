package br.com.anhanguera.caranavirus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.Vacina;
import br.com.anhanguera.caranavirus.enuns.NomeVacinaEnum;
import br.com.anhanguera.caranavirus.enuns.TipoSanguinioEnum;
import br.com.anhanguera.caranavirus.repository.UserRepository;
import br.com.anhanguera.caranavirus.service.CEPService;
import br.com.anhanguera.caranavirus.service.VacinaService;
import br.com.anhanguera.caranavirus.util.LeitorCSV;

public class CreateDataBD extends CaranaVirusApplicationTests{

	private User user;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private VacinaService vacinaService;
	
	@Autowired
	private CEPService service;
	
	@BeforeEach
	public void createForTest() {
		
		List<String> ceps = LeitorCSV.geradorEndereco("ceps.csv");
		
		
		int totalUsuarios = 60;
		
		
		for(int i = 0; totalUsuarios > i; i++ ) {
			user = new User();
			user.setName(faker.name().fullName());
			user.setProfissao(faker.job().title());
			user.setCelular(faker.phoneNumber().cellPhone());
			user.setTelefone(faker.phoneNumber().phoneNumber());
			
			Collections.shuffle(ceps);
			user.setEndereco(service.getEndereco(ceps.get(0)));		
			
			user.setTipoSanguinio(gerateTipoSanguinio());
			user.setVacinas(createVacinaFotTeste());
			repository.save(user);			
		}
	}
	
	@Test
	public void testSalve() {
		List<User>usuarios = repository.findAll();
		assertTrue(usuarios.size() > 0);
	}
	
	private TipoSanguinioEnum gerateTipoSanguinio() {
		
		TipoSanguinioEnum[] tiposDanguinios = TipoSanguinioEnum.values();
		
		List<TipoSanguinioEnum> listTiposSanguinios = new ArrayList<>();
		
		for (TipoSanguinioEnum tipoSanguinioEnum : tiposDanguinios) {
			listTiposSanguinios.add(tipoSanguinioEnum);
		}
		
		Collections.shuffle(listTiposSanguinios);
		
		return listTiposSanguinios.get(0);
	}
	
	private List<Vacina> createVacinaFotTeste() {

		List<Vacina> vacinas = new ArrayList<>();

		Random random = new Random();

		int numeroRandomico = random.nextInt(12);
		int numeroRandomico2 = random.nextInt(3);
		
		if(numeroRandomico == 0) {
			numeroRandomico = 1;
		}
		
		if(numeroRandomico2 == 0) {
			numeroRandomico2 = 1;
		}

		for (int i = 0; numeroRandomico2 > i; i++) {
			Vacina vacina = new Vacina();
			vacina.setMarcaVacina(gerateNomeVacina());
			vacinas.add(vacina);
		}

		for (int i = 0; vacinas.size() > i; i++) {
			Vacina vacina = vacinas.get(i);
			Vacina vacinaAnterior = null;

			if (i > 0) {
				vacinaAnterior = vacinas.get(i-1);
				vacina.setDataAplicacao(vacinaAnterior.getDataAplicacao().plusMonths(4));
			} else {
				vacina.setDataAplicacao(LocalDate.now().plusWeeks(numeroRandomico));
			}

		}
		
		List<Vacina>vacinasValidas = new ArrayList<>();
		
		for (Vacina vacina : vacinas) {
			if(vacinaService.validarVacina(user, vacina)){
				vacinasValidas.add(vacina);
			}
						
		}

		return vacinasValidas;
	}
	
	private NomeVacinaEnum gerateNomeVacina() {

		NomeVacinaEnum[] tiposVacinas = NomeVacinaEnum.values();

		List<NomeVacinaEnum> listTiposVacinas = new ArrayList<>();

		for (NomeVacinaEnum tipoVacina : tiposVacinas) {
			listTiposVacinas.add(tipoVacina);
		}

		Collections.shuffle(listTiposVacinas);
		return listTiposVacinas.get(0);
	}
}
