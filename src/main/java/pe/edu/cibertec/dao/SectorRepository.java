package pe.edu.cibertec.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.edu.cibertec.entity.Sector;
import pe.edu.cibertec.entity.Solicitud;

public interface SectorRepository extends JpaRepository<Sector, Integer> {
	
	
	@Query("select s from Sector s where s.idsectorsuperior=?1 ")
	public List<Sector> listaSectorParaPrestamista(int d);
	
	@Query("select s from Sector s where s.idsectorsuperior=0 ")
	public List<Sector> listaSectorParaJefes();
	
	@Query("SELECT s FROM Sector s WHERE s.idsectorsuperior != -1 AND s.idsectorsuperior != 0 AND s.idsectorsuperior != 1 ")
	public List<Sector> listaSectorPrestatarios();
	
	
}
