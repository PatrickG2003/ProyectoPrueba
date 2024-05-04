package pe.edu.cibertec.entity;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
    private Integer id;
	@Column(name = "username")
    private String username;
	@Column(name = "password")
    private String password;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "apellido")
    private String apellido;
	@Column(name = "email", length = 100)
    private String email;
	@Column(name = "telefono")
    private String telefono;

	@ManyToOne
	@JoinColumn(name="idrol")
	private Rol rol;

	@ManyToOne
	@JoinColumn(name="sector_idsector")
	private Sector sector;
	@Column(name = "id_usuario_registra")
	private Integer idUsuarioRegistra;

	public Integer getIdUsuarioRegistra() {
		return idUsuarioRegistra;
	}

	public void setIdUsuarioRegistra(Integer idUsuarioRegistra) {
		this.idUsuarioRegistra = idUsuarioRegistra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	
}
