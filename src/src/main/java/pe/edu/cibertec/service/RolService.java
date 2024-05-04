package pe.edu.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.cibertec.dao.RolRepository;


@Service
public class RolService {
	@Autowired
	private RolRepository repositorio;
	
	public List<Rol> listarTodos(){
		return repositorio.findAll();
				}
}
