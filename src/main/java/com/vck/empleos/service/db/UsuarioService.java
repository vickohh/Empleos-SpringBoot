package com.vck.empleos.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vck.empleos.model.Usuario;
import com.vck.empleos.repository.UsuariosRepository;
import com.vck.empleos.service.IUsuariosService;

public class UsuarioService implements IUsuariosService {

	@Autowired
	private UsuariosRepository usuarioRepo; 
	
	
	
	@Override
	public void guardar(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Integer idUsuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
