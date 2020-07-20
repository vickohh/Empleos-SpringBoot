package com.vck.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasControler {
	
	@Autowired
	ICategoriasService serviceCategorias;
	
	// @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		
		List<Categoria> listaCategoriasController = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", listaCategoriasController);
		return "categorias/listCategorias";
	}
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear() {
		return "categorias/formCategoria";
	}
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria,BindingResult result,RedirectAttributes attributes) {
		if(result.hasErrors()) {			
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("El error es: " + error.getDefaultMessage());
			}			
			return "vacantes/formVacante";
		}	
		serviceCategorias.guardar(categoria);
		System.out.println(categoria.toString());
		attributes.addFlashAttribute("msg", "Registro Guardado");
		return "redirect:/categorias/index";		
	}


}
