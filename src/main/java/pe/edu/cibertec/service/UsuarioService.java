package pe.edu.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.cibertec.dao.UsuarioRepository;
import pe.edu.cibertec.entity.Enlace;
import pe.edu.cibertec.entity.Usuario;



@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario sesionUsuario(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}
	public List<Enlace> enlacesDelUsuario(String desRol){
		return repo.traerEnlacesDelUsuario(desRol);
	}
	
	public void registrar(Usuario m) {
		repo.save(m);
	}
	public void actualizar(Usuario m) {
		repo.save(m);
	}
	public void eliminar(Integer cod) {
		repo.deleteById(cod);
	}
	public Usuario buscarPorID(Integer cod) {
		return repo.findById(cod).orElse(null);
	}
	public List<Usuario> listarTodos(){
		return repo.findAll();
	}
	public List<Usuario> listarUsuarioporRol(Integer codRol){
		return repo.listarUsuarioporRol(codRol);
	}
	
}
