package pe.edu.cibertec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UsuarioUsuarioPK implements Serializable {

    @Column(name = "tb_usuario_id_usuario")
    private Integer usuarioId1;

    @Column(name = "tb_usuario_id_usuario1")
    private Integer usuarioId2;

    public Integer getUsuarioId1() {
        return usuarioId1;
    }

    public void setUsuarioId1(Integer usuarioId1) {
        this.usuarioId1 = usuarioId1;
    }

    public Integer getUsuarioId2() {
        return usuarioId2;
    }

    public void setUsuarioId2(Integer usuarioId2) {
        this.usuarioId2 = usuarioId2;
    }
}
