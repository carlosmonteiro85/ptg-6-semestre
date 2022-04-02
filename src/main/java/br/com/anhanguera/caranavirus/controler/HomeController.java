package br.com.anhanguera.caranavirus.controler;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.anhanguera.caranavirus.dto.UserDto;
import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	UserService service;

	@GetMapping("")
	public String listAll(Model model) {
		List<User> users = service.findAll();
		model.addAttribute("users", users);
		return "all-users";
	}

	@RequestMapping("profile")
	public String formUser(Long id, Model model) {
		UserDto userDto = new UserDto();
		if (id != null) {
			Optional<User> user = service.loadById(id);
			if (user.isPresent()) {
				userDto.userToUserDto(user.get());
			}
		}
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

		try {
			User user = userDto.userDTOToUser();
			service.save(user);
			mensagem = "O cadastro de " + userDto.getName() + " foi salva com sucesso";
			attributes.addFlashAttribute("msnSucess", mensagem);
		} catch (Exception e) {
			mensagem = "Houve o seguinte erro ao tentar salvar o usuario " + e.getMessage();
			attributes.addFlashAttribute("msnError", mensagem);
			return "redirect:/form-user";
		}
		return "redirect:/";
	}

	@RequestMapping("form-user")
	public String profile(UserDto userDto) {
		return "form-user";
	}

}
