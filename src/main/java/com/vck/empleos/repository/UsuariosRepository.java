package com.vck.empleos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vck.empleos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByUsername(String username);

}
