package com.vck.empleos.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vck.empleos.model.Solicitud;
import com.vck.empleos.repository.SolicitudesRepository;
import com.vck.empleos.service.ISolicitudesService;


@Service
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	
	@Autowired
	private SolicitudesRepository solicitudesRepo; 

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepo.save(solicitud);
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Solicitud> buscarTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}	

}
