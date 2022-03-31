package br.com.anhanguera.caranavirus.entity;

import java.util.LinkedList;
import java.util.List;

import br.com.anhanguera.caranavirus.enuns.NumeroDosagemEnum;
import br.com.anhanguera.caranavirus.enuns.TipoSanguinioEnum;
import lombok.Data;

@Data
public class UserDto {
	
	private Long id;
	private String name;
	private Adress adress;
	private String telefone;
	private String celular;
	private String profissao;
	private TipoSanguinioEnum tipoSanguinio;
	private List<Vacina> vacinas = new LinkedList<>();
	
	public Vacina obterVacina(NumeroDosagemEnum numeroDose) {
		
		Vacina vacinaRetornada = null;
		
		for (Vacina vacina : vacinas) {
			if(vacina.getNumeracaoDose().equals(numeroDose)) {
				vacinaRetornada = vacina;
				break;
			}
		}
		return vacinaRetornada;
	}
	
	public User userDTOToUser() {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setEndereco(adress);
		user.setTelefone(telefone);
		user.setCelular(celular);
		user.setProfissao(profissao);
		user.setVacinas(vacinas);
		user.setTipoSanguinio(tipoSanguinio);
		return user;
	}
	
	public UserDto userToUserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.adress = user.getEndereco();
		this.telefone = user.getTelefone();
		this.celular = user.getCelular();
		this.profissao = user.getProfissao();
		this.vacinas = user.getVacinas();
		this.tipoSanguinio = user.getTipoSanguinio();
		return this;
	}

}
