package com.vck.empleos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/listUsuarios")														  //LISTADO DE USUARIOS
	public String mostrarIndex(Model model) {
		model.addAttribute("usuarios", serviceUsuario.buscarTodos());
		return "usuario/listUsuarios";	
	}
	
	@GetMapping("/signup")
	public String registrarse(Model model ) {		
		return "usuario/formUsuario";		
	}
	
	@PostMapping("/signup")
	public String guardarResgistro(Usuario usuario,Model model,RedirectAttributes attributes) {		
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());

	if(serviceUsuario.findByEmail(usuario) == null  && serviceUsuario.findByUsername(usuario) == null) {
		System.out.println("USUARIO NUEVO AGREGADO");
		serviceUsuario.guardar(usuario);
		attributes.addFlashAttribute("msg","Usuario agregado");
		//System.out.println("USUARIO NUEVO AGREGADO");
		return "redirect:/usuario/listUsuarios";
	  }else {
		  if(serviceUsuario.findByEmail(usuario) != null) 
		  {
			  attributes.addFlashAttribute("msg", "Correo en uso");
			  System.out.println("USUARIO CON EMAIL YA AGREGADO");		
		  }
		  if(serviceUsuario.findByUsername(usuario) != null) 
		  {
			  attributes.addFlashAttribute("msg", "Nombre de usuario en uso");
			  System.out.println("USUARIO CON NOMBRE YA AGREGADO");	
		  }
	  }
	 return "redirect:/usuario/signup";	      	 	
	}
	
	@GetMapping("/delete/{id}")														  //ELIMINAR VACANTE
	public String eliminar(@PathVariable("id") int idUsuario,Model model,RedirectAttributes attributes) {
		serviceUsuario.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "Registro Eliminado");
		return  "redirect:/usuario/listUsuarios";	
	}
	
	
}
