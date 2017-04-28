package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Representa o usuário que irá acessar o client side.
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="nome", nullable=false, unique=true)
	private String nome;
	@Column(name="identificacao", nullable=false, unique=true)
	private String identificacao;
	@Column(name="senha", nullable=false)
	private String senha;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	@Column(name="telefone", nullable=false, unique=true)
	private String telefone;
	@Column(name="foto", length=15000000, nullable=false)//Tamanho máximo de 15Mb
	private byte[] foto;
	
	@Column(name="endereco", nullable=false)
	private String endereco;
	@Column(name="numero", nullable=false)
	private String numero;
	@Column(name="bairro", nullable=false)
	private String bairro;
	@Column(name="cidade", nullable=false)
	private String cidade;
	@Column(name="estado", nullable=false)
	private String estado;
	@Column(name="cep", nullable=false)
	private String cep;
	
	@OneToOne
	@JoinColumn(name = "assinatura", nullable = false)
	private Assinatura assinatura;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
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
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Assinatura getAssinatura() {
		return assinatura;
	}
	public void setAssinatura(Assinatura assinatura) {
		this.assinatura = assinatura;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
