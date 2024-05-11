package pe.edu.cibertec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dao.MontosRepository;
import pe.edu.cibertec.entity.Montos;


import java.util.List;
@Service
public class MontosServiceImpl {
    @Autowired
    private MontosRepository repositorio;

    public List<Montos> listarTodos(){
        return repositorio.findAll();
    }
}
