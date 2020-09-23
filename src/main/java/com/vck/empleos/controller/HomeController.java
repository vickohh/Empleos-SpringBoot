package com.vck.empleos.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.model.Usuario;
import com.vck.empleos.model.Vacante;
import com.vck.empleos.service.ICategoriasService;
import com.vck.empleos.service.IUsuariosService;
import com.vck.empleos.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("vacanteServiceJPA")
	private IVacantesService serviceVacantes;
	
	@Autowired
	@Qualifier("categoriasServiceJPA")
	ICategoriasService serviceCategorias;
	
	@Autowired
	IUsuariosService serviceUsuarios;
	
	
	@GetMapping("/tabla")
	public String showTable(Model model) {
		List<Vacante> lista = serviceVacantes.buscartodas();
		model.addAttribute("tabla", lista);
		return "tabla";
	}
	
	
	@GetMapping("/detalle")
	public String showDetails(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para soporte de Intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(10900);
		
		model.addAttribute("vacante", vacante);		
		return"home";		
	}
	
	
	@GetMapping("/")
	public String showHome(Model model ) {
		List<Categoria> categorias = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", categorias);
		return "home";		
	}
	
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		
		//where descripcion like '%?%'
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante,matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista); //Vacantes filtradas
		
		List<Categoria> categorias = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", categorias);
		
		return "home";		
	}
	
	 /**
	 * InitBinder para Strings si los detecta vacios en el Data Binding los setea como NULL
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		
	}
	
	@GetMapping("/index")																		//ATRIBUTOS Y SESIONES DE USUARIO
	public String mostrarIndex(Authentication auth, RedirectAttributes attributes,HttpSession session) {
		String username = auth.getName();
		attributes.addFlashAttribute("username", username);
		System.out.println("nombre del usuario logeado: " + username);
		
		
		/**
		 * Esto es necesario si se quiere renderizar algun atributo del usuario logeado como correo o nombre,  tambien en setGenericos en caso de que solo se quiera desplegar
		 * el username se puede usar  **Renderizar en la vista el nombre del usuario que inicio sesión: sec:authentication="name"**
		 */
		if (session.getAttribute("user")== null) {
			Usuario user = serviceUsuarios.findByUsername(username);
			user.setPassword(null);													      		//POR SEGURIDAD SE REMUEVE EL PASSWORD
			System.out.println("El correo del usuario es: " + user.toString());								
			session.setAttribute("user", user);
		}

		for(GrantedAuthority rol : auth.getAuthorities()) {                                    //TIPO DE ROL DE USUARIO
			System.out.println("ROL: " + rol.getAuthority());
		}
		return "redirect:/";	
	}
	
	@ModelAttribute/// agregar atributos globales para todos los metodos
	public void setGenericos(Model model,RedirectAttributes attributes,HttpSession session,Authentication auth) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("search", vacanteSearch);
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());

		String username = "noValue";

		try {
			username = auth.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (session.getAttribute("user") == null) {//se valida si no existe el atributo creado, para crear uno
			if (username != "noValue") {// se valida que si se haya encontrado un usuario loegado
				Usuario user = serviceUsuarios.findByUsername(username);
				user.setPassword(null);															 // POR SEGURIDAD SE REMUEVE EL PASSWORD
				System.out.println("El correo del usuario es: " + user.toString());
				session.setAttribute("user", user);
			}
		}
	}
}

