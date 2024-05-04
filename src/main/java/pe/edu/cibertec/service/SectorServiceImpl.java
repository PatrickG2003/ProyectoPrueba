package pe.edu.cibertec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dao.SectorRepository;
import pe.edu.cibertec.entity.Sector;
import pe.edu.cibertec.entity.Solicitud;

import java.util.List;

@Service
public class SectorServiceImpl {
    @Autowired
    private SectorRepository repository;
    public List<Sector> listarTodos(){
        return repository.findAll();
    }
    
    public List<Sector> listaSectorParaPrestamista(Integer cod) {
		return repository.listaSectorParaPrestamista(cod);
	}
    public List<Sector> listaSectorParaJefe() {
		return repository.listaSectorParaJefes();
	}
    
}
