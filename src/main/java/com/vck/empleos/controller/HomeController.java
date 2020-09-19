package com.vck.empleos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.model.Vacante;
import com.vck.empleos.service.ICategoriasService;
import com.vck.empleos.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("vacanteServiceJPA")
	private IVacantesService serviceVacantes;
	
	@Autowired
	@Qualifier("categoriasServiceJPA")
	ICategoriasService serviceCategorias;
	
	
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
		
		Example<Vacante> example = Example.of(vacante);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		
		List<Categoria> categorias = serviceCategorias.buscarTodas();
		model.addAttribute("vacantes", lista); //Vacantes filtradas
		model.addAttribute("categorias", categorias);
		System.out.println("Buscando por: " + vacante);
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
	
	
	@ModelAttribute/// agregar atributos globales para todos los metodos
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("search", vacanteSearch);	
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());			
	}
}

