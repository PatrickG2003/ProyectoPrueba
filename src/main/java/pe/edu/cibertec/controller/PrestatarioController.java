package pe.edu.cibertec.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.entity.*;
import pe.edu.cibertec.service.RolService;
import pe.edu.cibertec.service.SolicituServiceImpl;
import pe.edu.cibertec.service.UsuarioService;

@Controller
@RequestMapping("/prestatario")
@SessionAttributes({"ENLACES","DATOSDELUSUARIO","IDUSUARIO"})

public class PrestatarioController {
	@Autowired
	private UsuarioService servicioUsu;
	@Autowired
	private SolicituServiceImpl serviceSolicitud;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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

	@RequestMapping("/solicitarPrestamo")
	public String registrarSolicitud(Model model){

		return "solicitarPrestamo";
	}

	@RequestMapping("/registrarSolicitud")
	public String registrarSolicitud(
			@RequestParam("montoinicial") Double montoi,
			@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechai,
			@RequestParam("montofinal") Double montof,
			@RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaf,
			@RequestParam("pagodiario") Double pagod,
			@RequestParam("periodo") String periodo,
			RedirectAttributes redirect,
			HttpServletRequest request,
			Authentication auth) {
		try {
			// Obtener el usuario autenticado desde el objeto Authentication
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String username = userDetails.getUsername();

			// Utilizar el servicio de Usuario para obtener el usuario por nombre de usuario
			Usuario usuario = servicioUsu.sesionUsuario(username);

			// Crear la solicitud y establecer sus atributos
			Solicitud solicitud = new Solicitud();
			solicitud.setMontoInicial(montoi);
			solicitud.setMontoFinal(montof);
			solicitud.setFechaInicio(fechai);
			solicitud.setFechaFin(fechaf);
			solicitud.setEstado("SOLICITADO");
			solicitud.setPagoDiario(pagod);
			solicitud.setPeriodo(periodo);

			// Establecer el usuario en la solicitud
			solicitud.setUsuarioRegistro(usuario);

			// Registrar la solicitud
			serviceSolicitud.registrar(solicitud);

			// Redireccionar con mensaje de éxito
			redirect.addFlashAttribute("MENSAJE","Solicitud registrada");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sesion/principal";
	}
	@RequestMapping("/listarSolicitudes")
	public String listarSolicitudes(Model model) {
		// Obtener la autenticación del contexto de seguridad
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String username = userDetails.getUsername();

			Usuario usuario = servicioUsu.sesionUsuario(username);

			if (usuario != null) {
				int usuarioId = usuario.getId();

				model.addAttribute("Solicitud", serviceSolicitud.listaSolicitudesPorUsuario(usuarioId));
			} else {
				model.addAttribute("error", "Usuario no encontrado");
			}
		} else {
			model.addAttribute("error", "Detalles de usuario no disponibles");
		}

		return "verSolicitudes";
	}
}
