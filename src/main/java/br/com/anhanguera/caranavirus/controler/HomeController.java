package br.com.anhanguera.caranavirus.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anhanguera.caranavirus.entity.User;
import br.com.anhanguera.caranavirus.entity.UserDto;
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

	@RequestMapping("form-user")
	public String formUser(Long id, Model model) {
		UserDto userDto = new UserDto();

		if (id != null) {
			
			Optional<User> user = service.loadById(id);
			
			if (user.isPresent()) {
				userDto.userToUserDto(user.get());
			}

		}
		
		model.addAttribute("userDto", userDto);
		return "form-user";
	}
}
