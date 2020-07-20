package com.vck.empleos.service;
import java.util.List;

import com.vck.empleos.model.Vacante;

public interface IVacantesService {
	
	List<Vacante> buscartodas();
	Vacante buscaPorId(Integer idVacante);
	void guardar(Vacante vacante);
	
}
