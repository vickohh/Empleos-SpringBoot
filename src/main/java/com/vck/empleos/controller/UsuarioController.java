package com.vck.empleos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Usuario;
import com.vck.empleos.service.IUsuariosService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuariosService serviceUsuario;
	
	@GetMapping("/signup")
	public String registrarse(Model model ) {		
		return "usuario/formUsuario";		
	}
	
	@PostMapping("/signup")
	public String guardarResgistro(Usuario usuario,Model model,RedirectAttributes attributes) {
		
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		
		//validar correro con optimal
	if(serviceUsuario.findByEmail(usuario) == null) {
		//validar nombre con optimal
		serviceUsuario.guardar(usuario);
		System.out.println("USUARIO NUEVO AGREGADO");
		//attributes.addFlashAttribute("msg", "Usuario Guardado");
		 return "usuario/listUsuarios";
	  }else {	
	   System.out.println("USUARIO CON CORREOR YA AGREGADO");		  
	  }

		//attributes.addFlashAttribute("msg", "Usuario Guardado");
	      return "usuario/formUsuario";
	
	}
	
	
}
