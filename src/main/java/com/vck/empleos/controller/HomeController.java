package com.vck.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vck.empleos.model.Vacante;
import com.vck.empleos.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	
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
		
		return"detalle";
		
	}
	
		
	@GetMapping("/listado")
	public String showList(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Aux de Contabilidad");
		lista.add("Enfermero");	
		
		model.addAttribute("lista",lista);
		return "listado";
		
	}
	
	@GetMapping("/")
	public String showHome(Model model ) {
		List<Vacante> lista = serviceVacantes.buscartodas();
		model.addAttribute("vacantes", lista);
	
		return "home";
		
	}
	
}
