package com.vck.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vck.empleos.model.Solicitud;
import com.vck.empleos.model.Usuario;
import com.vck.empleos.model.Vacante;
import com.vck.empleos.service.ISolicitudesService;
import com.vck.empleos.service.IUsuariosService;
import com.vck.empleos.service.IVacantesService;
import com.vck.empleos.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	@Autowired
	@Qualifier("vacanteServiceJPA")
	private IVacantesService serviceVacantes;
	
	@Autowired
	IUsuariosService serviceUsuarios;
	
	@Autowired
	ISolicitudesService serviceSolicitudes;
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio--------------------------------------DONE
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */
	
	@Value("${empleosapp.ruta.cv}")
	private String rutaArvhivo;
		
    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex() {

    	// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado() {
		
		// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante------------------------------------------------------------------------DONE
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear(@PathVariable("idVacante") int idVacante,Model model, RedirectAttributes attributes,Solicitud solicitud) {
		Vacante vacantes = serviceVacantes.buscaPorId(idVacante);
		model.addAttribute("vacantes", vacantes);
		System.out.println(solicitud.toString());
		return "solicitudes/formSolicitud";
		
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos-----------------------------------------------------------------EN PROGRESO
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result,Vacante vacante,RedirectAttributes attributes, Authentication auth,
						@RequestParam("archivocv") MultipartFile multiPart, Model model) {	
		
		/**
		 * 
		 */
		String username = auth.getName();
		Usuario user = serviceUsuarios.findByUsername(username);
		
		if(result.hasErrors()) {			
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("El error es:*****************************" + error.getDefaultMessage());
			}	
			//attributes.addFlashAttribute("solicitud", solicitud);
			attributes.addFlashAttribute("errores", "Llene todos los campos");	
			return "redirect:/solicitudes/create/"+solicitud.getVacante().getId();
		}
				
		solicitud.setUsuario(user);
		solicitud.setFecha(new Date());
	
		if (!multiPart.isEmpty()) {
		
			String nombreArchivo = Utileria.guardarArchivo(multiPart, rutaArvhivo);
			if (nombreArchivo != null){ // La imagen si se subio
			// Procesamos la variable nombreImagen
				solicitud.setArchivo(nombreArchivo);
			System.out.println(nombreArchivo);
			}
		}
					
		serviceSolicitudes.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		attributes.addFlashAttribute("errors", result);

		return "redirect:/solicitudes/index";	
		
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar() {
		
		// EJERCICIO
		return "redirect:/solicitudes/indexPaginate";
		
	}
			
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
