package com.vck.empleos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vck.empleos.model.Solicitud;

public interface ISolicitudesService {
	
	// EJERCICIO: Método que guarda un objeto tipo Solicitud en la BD (solo disponible para un usuario con perfil USUARIO).
	void guardar(Solicitud solicitud);
	
	// EJERCICIO: Método que elimina una Solicitud de la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
	void eliminar(Integer idSolicitud);
	
	// EJERCICIO: Método que recupera todas las Solicitudes guardadas en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
	List<Solicitud> buscarTodas();
	
	// EJERCICIO: Método que busca una Solicitud en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
	Solicitud buscarPorId(Integer idSolicitud);
	
	// EJERCICIO: Método que recupera todas las Solicitudes (con paginación) guardadas en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
	Page<Solicitud> buscarTodas(Pageable page);
}
