package pe.edu.cibertec.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import pe.edu.cibertec.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

	@Query("select s from Solicitud s where s.usuarioRegistro.idUsuarioRegistra=?1 ")
	public List<Solicitud> listaSolicitudesPorPrestatariosdePrestamista(int d);
	
	
	@Modifying
	@Query("update Solicitud s SET s.estado = 'APROBADO' WHERE s.id = ?1")
	public void actualizarSolicitudAprobado(int id);

}
