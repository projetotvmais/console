package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe wrapper de perfil logavel.(Administrador do sistema)
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "perfil")
public class Perfil {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="nome", nullable=false, unique=true)
	private String nome;
	@Column(name="senha", nullable=false)
	private String senha;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	@Column(name="telefone", nullable=false, unique=true)
	private String telefone;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return "Perfil [id=" + id + ", nome=" + nome + ", senha=" + senha + ", email=" + email + ", telefone="
				+ telefone + "]";
	}
}
