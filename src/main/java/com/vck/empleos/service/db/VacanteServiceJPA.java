package com.vck.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vck.empleos.model.Vacante;
import com.vck.empleos.repository.VacantesRepository;
import com.vck.empleos.service.IVacantesService;

@Service
public class VacanteServiceJPA implements IVacantesService {
	
	@Autowired
	private VacantesRepository vacantesRepo; 
	

	@Override
	public List<Vacante> buscartodas() {
		return vacantesRepo.findAll();
		
	}

	@Override
	public Vacante buscaPorId(Integer idVacante) {
		
		Optional<Vacante> optional = vacantesRepo.findById(idVacante);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;			
	}

	@Override
	public void guardar(Vacante vacante) {
		
		vacantesRepo.save(vacante);
		
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

}
