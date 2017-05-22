package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.model.Usuario;

/**
 * Interface de acesso ao banco de dados para operar a tabale de clientes
 * @author douglas.f.filho
 *
 */
public interface UsuarioDao {
	public Usuario pegarPorId(long id);
	public List<Usuario> listar(String nome);
	public String cadastrar(Usuario usuario);
	public String atualizar(Usuario usuario);
	public String desativar(long id);
	public String reativar(long id);
	public byte[] pegarFotoDeUsuario(long id);
}
