package com.vck.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vck.empleos.model.Solicitud;
import com.vck.empleos.model.Usuario;
import com.vck.empleos.model.Vacante;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {
	Solicitud findByVacanteAndUsuario(Vacante idVacante, Usuario idUsuario);
	
}
