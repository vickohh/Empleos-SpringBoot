package com.vck.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vck.empleos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
