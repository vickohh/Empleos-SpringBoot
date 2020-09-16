package com.vck.empleos.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vck.empleos.model.Vacante;

@Service
public class VacantesServicesImpl implements IVacantesService {
	private List<Vacante> lista = null;

	public VacantesServicesImpl() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();

		try {
				Vacante vacante1 = new Vacante();
				vacante1.setId(1);
				vacante1.setNombre("Ingeniero de Comunicaciones");
				vacante1.setDescripcion("Se solicita ingeniero para soporte de Intranet");
				vacante1.setFecha(sdf.parse("08-02-2020"));
				vacante1.setSalario(15900);
				vacante1.setDestacado(1);
				vacante1.setImagen("empresa1.png");
				
				Vacante vacante2 = new Vacante();
				vacante2.setId(2);
				vacante2.setNombre("Ingeniero de Sistenas");
				vacante2.setDescripcion("Se solicita ingeniero para soporte de Intranet");
				vacante2.setFecha(sdf.parse("09-03-2020"));
				vacante2.setSalario(10900);
				vacante2.setDestacado(0);
				vacante2.setImagen("empresa2.png");
				
				Vacante vacante3 = new Vacante();
				vacante3.setId(3);
				vacante3.setNombre("Ingeniero Civil");
				vacante3.setDescripcion("Se solicita ingeniero para soporte de Intranet");
				vacante3.setFecha(sdf.parse("08-03-2020"));
				vacante3.setSalario(10900);
				vacante3.setDestacado(0);
				
				Vacante vacante4 = new Vacante();
				vacante4.setId(4);
				vacante4.setNombre("Ingeniero de Aeroespacial");
				vacante4.setDescripcion("Se solicita ingeniero para soporte de Intranet");
				vacante4.setFecha(sdf.parse("15-01-2020"));
				vacante4.setSalario(10900);
				vacante4.setDestacado(1);
				vacante4.setImagen("empresa4.png");
								
				lista.add(vacante1);
				lista.add(vacante2);
				lista.add(vacante3);
				lista.add(vacante4);
								
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}

	@Override
	public List<Vacante> buscartodas() {
		
		return lista;
	}

	@Override
	public Vacante buscaPorId(Integer idVacante) {
		
		for(Vacante v: lista) {
			
			if(v.getId()== idVacante) {
				return v;
			}		
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
	
		lista.add(vacante);
		
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

}
