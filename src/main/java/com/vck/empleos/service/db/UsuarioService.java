package com.vck.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vck.empleos.model.Usuario;
import com.vck.empleos.repository.UsuariosRepository;
import com.vck.empleos.service.IUsuariosService;

@Service
public class UsuarioService implements IUsuariosService {

	@Autowired
	private UsuariosRepository usuarioRepo; 
		
	
	public void guardar(Usuario usuario) {		
		usuarioRepo.save(usuario);	
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
	
	public Usuario findByEmail(Usuario usuario) {
		Optional<Usuario> optional = usuarioRepo.findByEmail(usuario.getEmail());
		if(optional.isPresent()) {
			return (optional.get());
		}
		return null;		
	}
}