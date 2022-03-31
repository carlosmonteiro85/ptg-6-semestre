package br.com.anhanguera.caranavirus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.anhanguera.caranavirus.entity.Adress;
import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.Vacina;
import br.com.anhanguera.caranavirus.enuns.NumeroDosagemEnum;
import br.com.anhanguera.caranavirus.enuns.TipoSanguinioEnum;
import br.com.anhanguera.caranavirus.repository.UserRepository;
import br.com.anhanguera.caranavirus.service.CEPService;

public class PersistenciaTest extends CaranaVirusApplicationTests{

	private User user;
	
	@Autowired
	private CEPService cepService;
	
	@Autowired
	private UserRepository repository;
	
	@BeforeEach
	public void createForTest() {
		
		user = new User();
		user.setName(faker.name().fullName());
		user.setProfissao(faker.job().title());
		user.setCelular(faker.phoneNumber().cellPhone());
		user.setTelefone(faker.phoneNumber().phoneNumber());
		user.setTipoSanguinio(TipoSanguinioEnum.A_POSITIVO);
		
		user.getVacinas().add(new Vacina(NumeroDosagemEnum.PRIMEIRA,"PFIZER", LocalDate.now().minusDays(60)));
		user.getVacinas().add(new Vacina(NumeroDosagemEnum.SEGUNDA,"PFIZER", LocalDate.now().minusDays(30)));
		user.getVacinas().add(new Vacina(NumeroDosagemEnum.TERCEIRA,"PFIZER", LocalDate.now()));
		
		Adress endereco = cepService.getEndereco("72231804");
		user.setEndereco(endereco);
	}
	
	@AfterEach
	public void deleteUserTest() {
		repository.deleteAll();
	}
	
	@Test
	public void testSalve() {
		repository.save(user);
		assertTrue(user.getId() != null);
	}
	
	@Test
	public void getAllUsers() {
		repository.save(user);
		List<User> usuarios = repository.findAll();
		assertTrue(usuarios.size() > 0);
	}
	
	@Test
	public void getDoselUsers() {
		repository.save(user);
		boolean fistDose = user.recebeuDose(NumeroDosagemEnum.PRIMEIRA);
		assertTrue(fistDose );
		boolean secondDose = user.recebeuDose(NumeroDosagemEnum.SEGUNDA);
		assertTrue(secondDose);
		user.getVacinas().remove(2);
		boolean thirdDose = user.recebeuDose(NumeroDosagemEnum.TERCEIRA);
		assertFalse(thirdDose);
	}
	
}
