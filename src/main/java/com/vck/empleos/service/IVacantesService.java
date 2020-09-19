package com.vck.empleos.service;
import java.util.List;

import org.springframework.data.domain.Example;

import com.vck.empleos.model.Vacante;

public interface IVacantesService {
	
	List<Vacante> buscartodas();
	Vacante buscaPorId(Integer idVacante);
	void guardar(Vacante vacante);
	List<Vacante> buscarDestacadas();
	void eliminar(Integer idVacante);
	List<Vacante> buscarByExample(Example<Vacante> example);
}
