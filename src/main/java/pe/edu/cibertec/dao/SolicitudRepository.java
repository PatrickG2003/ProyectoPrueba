package pe.edu.cibertec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
}
