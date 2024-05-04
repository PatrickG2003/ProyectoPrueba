package pe.edu.cibertec.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario_has_tb_usuario")
public class UsuarioUsuario {
	@EmbeddedId
	private UsuarioUsuarioPK id;

	public UsuarioUsuarioPK getId() {
		return id;
	}

	public void setId(UsuarioUsuarioPK id) {
		this.id = id;
	}
}
