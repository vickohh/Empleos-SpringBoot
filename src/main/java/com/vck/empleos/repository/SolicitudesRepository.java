package com.vck.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vck.empleos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
