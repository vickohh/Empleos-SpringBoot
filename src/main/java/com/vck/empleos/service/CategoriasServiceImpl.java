package com.vck.empleos.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vck.empleos.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

	public List<Categoria> listaCategorias = null;
	
	
	public CategoriasServiceImpl() {
		
		listaCategorias = new LinkedList<Categoria>();
		
		Categoria cate1 = new Categoria();
		cate1.setId(1);
		cate1.setNombre("Informatica");
		cate1.setDescripcion("Departamento de Informatica general");
		
		Categoria cate2 = new Categoria();
		cate2.setId(2);
		cate2.setNombre("Contabilidad");
		cate2.setDescripcion("Ciencias contables");
		
		Categoria cate3 = new Categoria();
		cate3.setId(3);
		cate3.setNombre("Desarrollo de Software");
		cate3.setDescripcion("Desarrollo de aplicaciones multiplataformas");
		
		listaCategorias.add(cate1);
		listaCategorias.add(cate2);
		listaCategorias.add(cate3);
		
	}

	@Override
	public void guardar(Categoria categoria) {
		listaCategorias.add(categoria);
		
	}

	@Override
	public List<Categoria> buscarTodas() {
			
		return listaCategorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		
		for(Categoria v: listaCategorias) {
			
			if(v.getId()== idCategoria) {
				return v;
			}		
		}
		return null;
	}

}
