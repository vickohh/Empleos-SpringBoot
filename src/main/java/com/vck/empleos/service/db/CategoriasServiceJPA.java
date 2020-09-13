package com.vck.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.vck.empleos.model.Categoria;
import com.vck.empleos.repository.CategoriasRepository;
import com.vck.empleos.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJPA implements ICategoriasService {
	
	@Autowired
	private CategoriasRepository categoriasRepo; 

	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);
		
	}

	@Override
	public List<Categoria> buscarTodas() {
		List<Categoria> todasCategorias = categoriasRepo.findAll();
		return todasCategorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		
		Optional<Categoria> optional = categoriasRepo.findById(idCategoria);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;			
	}

}
