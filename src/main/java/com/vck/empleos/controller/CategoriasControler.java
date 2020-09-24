package com.vck.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasControler {
	
	@Autowired
	@Qualifier("categoriasServiceJPA")
	private ICategoriasService serviceCategorias;
	
	// @GetMapping("/index")   es lo mismo                                                   //INDEX
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		
		List<Categoria> listaCategoriasController = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", listaCategoriasController);
		return "categorias/listCategorias";
	}
	
	@GetMapping("/paginate")	
	public String mostrarListaPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodasPage(page);
		model.addAttribute("categorias",lista);
		return "categorias/listCategorias";		
	}
	
	
	// @GetMapping("/create")                                                        //CREAR
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	
	
	// @PostMapping("/save")                                                        //GUARDAR
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
	
	@GetMapping("/edit/{id}")                                                        //EDITAR
	public String editar(@PathVariable("id") int idVacante,Model model) {
		Categoria categoria = serviceCategorias.buscarPorId(idVacante);
		model.addAttribute("categoria", categoria);
		return "categorias/formCategoria";		
	}
	
	@GetMapping("/delete/{id}") // se envia el id por la URI 						//ELIMINAR
	public String eliminar(@PathVariable("id") int idCategoria,Model model,RedirectAttributes attributes) {	
			 
		 try {					
			 serviceCategorias.eliminar(idCategoria);
			 attributes.addFlashAttribute("msg", "Registro Eliminado");
			 			 
			}catch(Exception ex) {
				attributes.addFlashAttribute("msg", "No es posible eliminar la Categoría seleccionada!.");
			}
		 return "redirect:/categorias/index";	
	}
	
}
