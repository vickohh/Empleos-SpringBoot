package com.vck.empleos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	
	
	
	@GetMapping("/signup")
	public String registrarse(Model model ) {		
		return "usuario/formUsuario";		
	}	
	
}
