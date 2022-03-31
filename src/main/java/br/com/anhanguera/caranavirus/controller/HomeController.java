package br.com.anhanguera.caranavirus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anhanguera.caranavirus.data.entity.User;
import br.com.anhanguera.caranavirus.data.entity.UserDto;
import br.com.anhanguera.caranavirus.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {"" , "users"})
	public String listAll(Model model) {
		List<User> users = userService.findAll(); 
		model.addAttribute("users", users);
		return "all-users";
	}
	
	@GetMapping("view-users")
	public String viewUsers(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		
		return "form-user";
	}
	
	@GetMapping("asdf/{id}")
	public String profile(@PathVariable("id") Long id, Model model) {
		Optional<User> user = userService.loadById(id);
		UserDto userEditDto = new UserDto().userToUserDto(user.get());
		model.addAttribute("userDto", userEditDto);
		return "form-user";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/404";
	}
	
	
}
