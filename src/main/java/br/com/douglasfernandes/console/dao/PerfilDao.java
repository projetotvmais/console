package br.com.douglasfernandes.console.dao;

import javax.servlet.http.HttpSession;

import br.com.douglasfernandes.console.model.Perfil;

/**
 * Interface que determina como acessar o banco de dados e obter informações de perfil (Administrador do sistema)
 * @author douglas.f.filho
 *
 */
public interface PerfilDao {
	public Perfil lerPerfil();
	public String atualizar(Perfil perfil);
	public String tentarLogar(String nomeOuEmail, String senha, HttpSession session);
	public void primeiroAcesso();
	public String esqueciMinhaSenha(String nomeOuEmail);
}
