package pe.edu.cibertec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dao.SectorRepository;
import pe.edu.cibertec.dao.SolicitudRepository;
import pe.edu.cibertec.entity.Solicitud;
import pe.edu.cibertec.entity.Usuario;

@Service
public class SolicituServiceImpl {
    @Autowired
    private SolicitudRepository repository;
    public void registrar(Solicitud m) {
        repository.save(m);
    }
}
