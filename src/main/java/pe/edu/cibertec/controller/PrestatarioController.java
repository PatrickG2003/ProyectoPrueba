package pe.edu.cibertec.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

	@RequestMapping("/solicitud")
	public String registrarJefe(Model model){

		return "registrarSolicitud";
	}

	@RequestMapping("/registarSolicitud")
			public String registrarS(
					@RequestParam("montoinicial") Integer montoi,
					@RequestParam("fechaInicio")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechai,
					@RequestParam("fechaFin")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaf,
					@RequestParam("periodo") String periodo,
					@RequestParam("montofinal") Integer montof,
					@RequestParam("pagodiario") Double pagod,
					RedirectAttributes redirect, HttpServletRequest request,Authentication auth) {
				try {

					Solicitud solicitud= new Solicitud();

					solicitud.setMontoInicial(montoi);
					solicitud.setMontoInicial(montof);
					solicitud.setFechaInicio(fechai);
					solicitud.setFechaFin(fechaf);
					solicitud.setEstado("solicitado");
					solicitud.setPeriodo(periodo);
					solicitud.setPagoDiario(pagod);
					//pasarle el id del logueado
					solicitud.getUsuarioRegistro();
					serviceSolicitud.registrar(solicitud);
					redirect.addFlashAttribute("MENSAJE","Solicitud registrada");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sesion/login";
	}
	
}
