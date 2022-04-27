package br.com.anhanguera.caranavirus.controler;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.anhanguera.caranavirus.dto.UserDto;
import br.com.anhanguera.caranavirus.entity.Adress;
import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.Vacina;
import br.com.anhanguera.caranavirus.service.CEPService;
import br.com.anhanguera.caranavirus.service.UserService;
import br.com.anhanguera.caranavirus.service.VacinaService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	UserService service;
	
	@Autowired
	CEPService cepService;
	
	@Autowired
	VacinaService vacinaService;

	@GetMapping("")
	public String listAll(Model model) {
		List<User> users = service.findAll();
		model.addAttribute("users", users);
		return "all-users";
	}

	@RequestMapping("profile")
	public String formUser(Long id, Model model) {
		UserDto userDto = new UserDto();
		Vacina vacina = new Vacina();
		if (id != null) {
			Optional<User> user = service.loadById(id);
			if (user.isPresent()) {
				userDto.userToUserDto(user.get());
			}
		}
		model.addAttribute("newVacina", vacina);
		model.addAttribute("userDto", userDto);
		return "profile";
	}

	@PostMapping("add-user")
	public String addUsers(@Valid UserDto userDto, BindingResult result, RedirectAttributes attributes) {

		String mensagem = "";

		if (result.hasErrors() || userDto.getName().isEmpty() || userDto.getAdress() == null
				|| userDto.getTipoSanguinio() == null) {
			mensagem = " Os campos nome, endereço, celular e tipo sanguinio são obrigatórios";
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/form-user";
		}
		
		if (userDto.getAdress().getBairro() == "" && userDto.getAdress().getLocalidade() == ""
				&& userDto.getAdress().getLogradouro() == "" && userDto.getAdress().getUf() == "") {
			Adress adress = null;
			
			try {
				adress = cepService.getEndereco(userDto.getAdress().getNumeroCEP().replace("-", ""));				
			}catch (Exception e) {
				mensagem = "Houve um erro ao tentar buscar o endereço do cep informado, favor consulte o Administrador do sistema - codigo error: " + e.getMessage();
				attributes.addFlashAttribute("msnError", mensagem);
				return "redirect:/";
			}
			
			if(adress == null) {
				adress = new Adress(userDto.getAdress().getNumeroCEP(), "Indefinido", "Indefinido", "Indefinido", "Indefinido", "Indefinido"); 
				attributes.addFlashAttribute("msnWarning", "Infelizmente não foi possivel completar o endereço de "+ userDto.getName() +", pedimos por gentileza que tente redefini-lo mais tarde");
			}
			userDto.setAdress(adress);
		}

		try {
			User user = userDto.userDTOToUser();
			service.save(user);
			mensagem = "O cadastro de " + userDto.getName() + " foi salva com sucesso";
			attributes.addFlashAttribute("msnSucess", mensagem);
		} catch (Exception e) {
			mensagem = "Houve o seguinte erro ao tentar salvar o usuario " + e.getMessage();
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping("form-user")
	public String profile(UserDto userDto) {
		return "form-user";
	}

	@RequestMapping("update-user/{id}")
	public String updateUser(@PathVariable("id") Long id, @Valid UserDto userDto, BindingResult result,
			RedirectAttributes attributes) {

		String mensagem = "";

		if (result.hasErrors() || userDto.getName().isEmpty() || userDto.getAdress() == null
				|| userDto.getTipoSanguinio() == null) {
			mensagem = " Os campos nome, endereço, celular e tipo sanguinio são obrigatórios";
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/profile?id="+ id.toString();
		}

		try {
			Optional<User> userLoad = service.loadById(id);
			
			if(userLoad.isPresent() && userDto.getId().equals(userLoad.get().getId())) {
				
				userLoad.get().setEndereco(userDto.getAdress());
				userLoad.get().setCelular(userDto.getCelular());
				userLoad.get().setId(userDto.getId());
				userLoad.get().setName(userDto.getName());
				userLoad.get().setProfissao(userDto.getProfissao());
				userLoad.get().setTelefone(userDto.getTelefone());
				userLoad.get().setTipoSanguinio(userDto.getTipoSanguinio());
				
				service.save(userLoad.get());				
				mensagem = "O cadastro atualizado com sucesso";
				attributes.addFlashAttribute("msnSucess", mensagem);
			}
			
		} catch (Exception e) {
			mensagem = "Houve um erro ao reculperar o cadastro do usuario ";
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/all-users";
		}
		return "redirect:/profile?id="+ id.toString();			
	}
	
	@PostMapping("add-vacina/{idUser}")
	public String addVacina(@PathVariable("idUser") Long idUser, @Valid Vacina vacina, BindingResult result,
			RedirectAttributes attributes) {

		String mensagem = "";

		if ( vacina.getMarcaVacina() == null|| vacina.getDataAplicacao() == null  ) {
			mensagem = " Os campos marca da vacina e data de aplicação são obrigatórios";
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/profile?id="+ idUser.toString();	
		}

		try {
			Optional<User> userLoad = service.loadById(idUser);
			if(userLoad.isPresent()) {
				
				if(!vacinaService.validarVacina(userLoad.get(), vacina)) {
					mensagem = "Esse usuário ja recebeu a dose maxima dessa vacina";
					attributes.addFlashAttribute("msnError", mensagem);
					throw new Exception();
				}
				
				if(!vacinaService.validarDataDose(userLoad.get(), vacina)) {
					mensagem = "Essa vacina não pode ser aplicada por pois a data da aplicação é menor que a exigida para esse tipo de vacina";
					attributes.addFlashAttribute("msnError", mensagem);
					throw new Exception();
				}
				
				vacinaService.save(vacina);
				service.save(userLoad.get());
				mensagem = "Vacina adicionada com sucesso";
				attributes.addFlashAttribute("msnSucess", mensagem);
			}
			
		} catch (Exception e) {
			return "redirect:/profile?id="+ idUser.toString();	
		}
		return "redirect:/profile?id="+ idUser.toString();			
	}
}
