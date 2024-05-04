package pe.edu.cibertec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.entity.Sector;

public interface SectorRepository extends JpaRepository<Sector, Integer> {
}
