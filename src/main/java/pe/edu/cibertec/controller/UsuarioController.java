package pe.edu.cibertec.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import pe.edu.cibertec.entity.Enlace;
import pe.edu.cibertec.entity.Rol;
import pe.edu.cibertec.entity.Sector;
import pe.edu.cibertec.entity.Usuario;
import pe.edu.cibertec.service.RolService;
import pe.edu.cibertec.service.SectorServiceImpl;
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
	
	@Autowired
	private SectorServiceImpl sectorService;
	
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
	public ResponseEntity<String> registraPrestatario(
						 @Valid@RequestParam("nombre") String nom,
						 @Valid@RequestParam("apellido") String ape,
						 @Valid@RequestParam("celular") String cel,
						 @Valid@RequestParam("correo") String cor,
						 @Valid@RequestParam("usuario") String usu,
						 @Valid@RequestParam("password") String pas,
						 @Valid@RequestParam("dni") String dni,
						 @Valid@RequestParam("sector") int sectorId,
						 RedirectAttributes redirect,HttpServletRequest request) {		
		try {
			
			String var;
			var = encoder.encode(pas);
			Usuario usuario=new Usuario();
			
			if(servicioUsu.buscaDni(dni) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"El DNI ya está registrado\"}");
			}else if(servicioUsu.buscarPorNombre(nom) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"El nombre ya está registrado\"}");
			}else if(servicioUsu.buscaUsername(usu) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"El Usuario ya está registrado\"}");
			}else if(servicioUsu.buscaEmail(cor) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"El Email ya está registrado\"}");
			}else {
				usuario.setNombre(nom);
				usuario.setApellido(ape);
				usuario.setEmail(cor);
				usuario.setTelefono(cel);
				usuario.setUsername(usu);
				usuario.setPassword(var);
				usuario.setDni(dni);
				
				Sector sector = new Sector();
		        sector.setId(sectorId);
		        usuario.setSector(sector);
		        
		      	//Sector sector=new Sector();
				//sector.setId(1);
		        //usuario.setSector(sector);
				Rol rol=new Rol();
				rol.setCodigo(4);			
				usuario.setRol(rol);
				
			
				servicioUsu.registrar(usuario);
				
				redirect.addFlashAttribute("MENSAJE","Usuario registrado");
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Usuario registrado exitosamente");
	}
	
	
	@ModelAttribute("sectores")
	public List<Sector> getSector(){
		List<Sector> listaSector = sectorService.listaSectorParaPrestatario();
		return listaSector;
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



