package pe.edu.cibertec.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.cibertec.service.UsuarioService;
import pe.edu.cibertec.entity.Usuario;



@Service
public class UsuarioDetailsService implements UserDetailsService{
	@Autowired
	private UsuarioService servicioUsu;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails bean=null;
		//invocar al método sesionUsuario
		Usuario u=servicioUsu.sesionUsuario(username);
		//rol del usuario
		Set<GrantedAuthority> rol= new HashSet<GrantedAuthority>();
		//adicionar rol del usario
		rol.add(new SimpleGrantedAuthority(u.getRol().getDescripcion()));
		//crear objeto bean y enviar los dos del usuario "u"
		bean=new User(u.getUsername(), u.getPassword(), rol);
		return bean;
	}

}




