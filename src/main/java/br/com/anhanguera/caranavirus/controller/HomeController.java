package br.com.anhanguera.caranavirus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping()
	public String home() {
		
//		all-users
//		form-user
		
		return "all-users";
	}
}
