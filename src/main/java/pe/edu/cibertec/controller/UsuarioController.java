package pe.edu.cibertec.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import pe.edu.cibertec.entity.Enlace;
import pe.edu.cibertec.entity.Rol;
import pe.edu.cibertec.entity.Usuario;
import pe.edu.cibertec.service.RolService;
import pe.edu.cibertec.service.UsuarioService;

@Controller
@RequestMapping("/sesion")
@SessionAttributes({"ENLACES","DATOSDELUSUARIO","IDUSUARIO"})
public class UsuarioController {
	@Autowired
	private UsuarioService servicioUsu;
	
	@Autowired
	private RolService servicioRol;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/principal")
	public String intranet(Authentication auth,Model model) {
		String nomRol=auth.getAuthorities().stream()
			      .map(GrantedAuthority::getAuthority)
			      .collect(Collectors.joining(","));
		List<Enlace> enlaces=servicioUsu.enlacesDelUsuario(nomRol);
		
		Usuario usu=servicioUsu.sesionUsuario(auth.getName());

		//atributo
		model.addAttribute("ENLACES",enlaces);
		model.addAttribute("DATOSDELUSUARIO",usu.getNombre()+" "+ usu.getApellido());
		model.addAttribute("IDUSUARIO", usu.getId());
		return "principal";
	}
	
	
	//METODO PARA QUE EL PRESTATARIO SE REGISTRE SOLO
	@RequestMapping("/registrarPrestatario")
	public String registrarPrestatario(
						 @RequestParam("nombre") String nom,
						 @RequestParam("apellido") String ape,
						 @RequestParam("celular") String cel,
						 @RequestParam("correo") String cor,
						 @RequestParam("usuario") String usu,
						 @RequestParam("password") String pas,
						 

						 RedirectAttributes redirect,HttpServletRequest request) {		
		try {
			
			String var;
			var = encoder.encode(pas);
			Usuario usuario=new Usuario();
			usuario.setNombre(nom);
			usuario.setApellido(ape);
			usuario.setEmail(cor);
			usuario.setTelefono(cel);
			usuario.setUsername(usu);
			usuario.setPassword(var);
			
			Rol rol=new Rol();
			rol.setCodigo(4);
			usuario.setRol(rol);
		
			servicioUsu.registrar(usuario);
			
			redirect.addFlashAttribute("MENSAJE","Usuario registrado");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sesion/login";
	}
	
	
	

	

	
	
	@RequestMapping("/consultaPorID")
	@ResponseBody
	public Usuario consultaPorID(@RequestParam("id") Integer id){
		return servicioUsu.buscarPorID(id);
	}
	
	
	
	
	@RequestMapping("/eliminarPorID")
	public String eliminar(@RequestParam("codigo") Integer cod,RedirectAttributes redirect) {
		 
		 try {
	            servicioUsu.eliminar(cod);
	            redirect.addFlashAttribute("MENSAJE", "Usuario eliminado con éxito");
	        } catch (Exception e) {
	            e.printStackTrace();
	            redirect.addFlashAttribute("ERROR", "Error al eliminar el usuario");
	        }		
		 return "redirect:/sesion/principal";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



