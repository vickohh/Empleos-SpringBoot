package com.vck.empleos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vck.empleos.model.Usuario;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	
	@GetMapping("/signup")
	public String registrarse(Model model ) {		
		return "usuario/formUsuario";		
	}
	
	@PostMapping("/signup")
	public String guardarResgistro(Usuario usuario,Model model ) {
		
		
		return "usuario/listUsuarios";	
	
	}
	
	
}
