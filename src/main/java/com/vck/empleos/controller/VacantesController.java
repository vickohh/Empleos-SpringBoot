package com.vck.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.model.Vacante;
import com.vck.empleos.service.ICategoriasService;
import com.vck.empleos.service.IVacantesService;
import com.vck.empleos.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	@Qualifier("vacanteServiceJPA")
	private IVacantesService serviceVacantes;
	
	@Autowired
	@Qualifier("categoriasServiceJPA")
	ICategoriasService serviceCategorias;
	
	
	
	@GetMapping("/listvac")														  //LISTADO DE VACANTES
	public String mostrarIndex(Model model) {		
		List<Vacante> vacantes = serviceVacantes.buscartodas();
		model.addAttribute("vacantes", vacantes);		
		return "vacantes/listVacantes";	
	}
	
	@GetMapping("/paginate")													  //PAGINADO	
	public String mostrarListaPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes",lista);

		return "vacantes/listVacantes";		
	}
	
	
	@GetMapping("/view/{id}")														//BUSCAR POR ID
	public String verDetalles(@PathVariable("id") int idVacante,Model model) {	
	    Vacante vacante = serviceVacantes.buscaPorId(idVacante);
		model.addAttribute("vacantes", vacante);	
		//buscara los detalles de la vacante en la BD
		return "detalle";
	}
	
	public void setGenericos() {
		
	}
		
	@GetMapping("/create")                                                          //CREAR
	public String crear(Vacante vacante, Model model) {
		 boolean myBooleanVariable = false;
		 model.addAttribute("myBooleanVariable", myBooleanVariable);
		 return "vacantes/formVacante";
	}
	
	@PostMapping("/save")                                                            //FORMULARIO EN CREATE
	public String guardar(Vacante vacante, BindingResult result, Categoria categoria, RedirectAttributes attributes, 
						@RequestParam("archivoImagen") MultipartFile multiPart, Model model)  {
		
		List<Categoria> categorias = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", categorias);
		
		if(result.hasErrors()) {			
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("El error es: " + error.getDefaultMessage());
			}			
			return "vacantes/formVacante";
		}
		if (!multiPart.isEmpty()) {
			//String ruta = ruta; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ // La imagen si se subio
			// Procesamos la variable nombreImagen
			vacante.setImagen(nombreImagen);
			System.out.println(nombreImagen);
			}
		}
		serviceVacantes.guardar(vacante);                                            //GUARDAR
		System.out.println(vacante.toString());
		attributes.addFlashAttribute("msg", "Registro Guardado");
		return "redirect:/vacantes/listvac";		
	}
	
	@GetMapping("/edit/{id}")                                                        //EDITAR
	public String editar(@PathVariable("id") int idVacante,Model model) {
		Vacante vacante = serviceVacantes.buscaPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";		
	}
	
	@GetMapping("/delete/{id}") // se envia el id por la URI 						 //ELIMINAR
	public String eliminar(@PathVariable("id") int idVacante,Model model,RedirectAttributes attributes) {	
		 serviceVacantes.eliminar(idVacante);
		 attributes.addFlashAttribute("msg", "Registro Eliminado");
		 return "redirect:/vacantes/listvac";		
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new  SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
	}
	
	
	@ModelAttribute/// agregar atributos globales para todos los metodos
	public void setGenericos(Model model,Authentication auth) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		
		String username =null;
		try {
			username = auth.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("el usuario logeado es: " + username);
		model.addAttribute("username", username);
	}
	
}
