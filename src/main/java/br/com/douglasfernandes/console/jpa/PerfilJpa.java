package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.EnviaEmail;
import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.controller.utils.ValidaEmail;
import br.com.douglasfernandes.console.controller.utils.ValidaString;
import br.com.douglasfernandes.console.dao.PerfilDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Perfil;

/**
 * Implementação da interface de acesso ao banco de dados.
 * @author douglas.f.filho
 *
 */
@Repository
public class PerfilJpa implements PerfilDao{
	
	@PersistenceContext
	EntityManager manager;

	private Perfil getPerfilPorNomeOuEmail(String nomeOuEmail){
		try{
			Query query = manager.createQuery("select p from Perfil as p where p.nome = :nome or p.email = :email");
			query.setParameter("nome", nomeOuEmail);
			query.setParameter("email", nomeOuEmail);
			Perfil perfil = (Perfil)query.getSingleResult();
			
			if(perfil != null)
				Logs.info("[PerfilJpa]::getPerfilPorNomeOuEmail :: Perfil encontrado: "+perfil.getNome());
			else
				Logs.info("[PerfilJpa]::getPerfilPorNomeOuEmail :: Perfil nao encontrado.");
			
			return perfil;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::getPerfilPorNomeOuEmail :: Erro ao tentar pegar perfil por nome ou email. Exception:");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String atualizar(Perfil perfil) {
		try{
			if(ValidaString.isValida(perfil.getNome())){
				if(ValidaString.isValida(perfil.getSenha())){
					if(ValidaEmail.isValido(perfil.getEmail())){
						manager.merge(perfil);
						Logs.info("[PerfilJpa]::atualizar :: Perfil atualizado com exito: "+perfil.getNome());
						return Mensagem.getSuccess("Perfil atualizado.");
					}
					else{
						Logs.warn("[PerfilJpa]::atualizar :: Erro ao tentar atualizar perfil com email invalido.");
						return Mensagem.getWarning("E-mail inválido.");
					}
				}
				else
				{
					Logs.warn("[PerfilJpa]::atualizar :: Erro ao tentar atualizar perfil com senha invalida.");
					return Mensagem.getWarning("Senha inválida.");
				}
			}
			else{
				Logs.warn("[PerfilJpa]::atualizar :: Erro ao tentar atualizar perfil com nome invalido.");
				return Mensagem.getWarning("Nome inválido.");
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::atualizar :: Erro ao tentar atualizar perfil, Exception:");
			e.printStackTrace();
			return Mensagem.getWarning("Erro no servidor.<br>Por favor, contate o suporte técnico.");
		}
	}

	@Override
	public String tentarLogar(String nomeOuEmail, String senha, HttpSession session) {
		try{
			Perfil perfil = getPerfilPorNomeOuEmail(nomeOuEmail);
			if(perfil != null){
				if(senha.equals(perfil.getSenha())){
					session.setAttribute("logado", perfil);
					Logs.info("[PerfilJpa]::tentarLogar :: Usuario logou com exito: "+perfil.getNome());
					return Mensagem.getSuccess("Bem Vindo, "+perfil.getNome()+".");
				}
				else{
					Logs.warn("[PerfilJpa]::tentarLogar :: Erro ao tentar logar perfil com senha incorreta.");
					return Mensagem.getWarning("Senha incorreta.");
				}
			}
			else{
				Logs.warn("[PerfilJpa]::tentarLogar :: Erro ao tentar logar perfil com nome desconhecido.");
				return Mensagem.getDanger("Não há usuário cadastrado com esse nome.");
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::tentarLogar :: Erro ao tentar logar perfil, Exception:");
			e.printStackTrace();
			return Mensagem.getWarning("Erro no servidor.<br>Por favor, contate o suporte técnico.");
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean temPerfilCadastrado(){
		try{
			Query query = manager.createQuery("select p from Perfil as p");
			List<Perfil> perfis = query.getResultList();
			if(perfis != null && perfis.size() > 0){
				Logs.info("[PerfilJpa]::temPerfilCadastrado :: ja tem perfil cadastrado.");
				return true;
			}
			else{
				Logs.warn("[PerfilJpa]::temPerfilCadastrado :: Nao ha perfil cadastrado.");
				return false;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::temPerfilCadastrado :: Erro ao tentar verificar se ha perfil cadastrado, Exception:");
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void primeiroAcesso() {
		try{
			if(!temPerfilCadastrado()){
				Perfil perfil = new Perfil();
				perfil.setNome("Administrador");
				perfil.setSenha("adm");
				perfil.setEmail("douglasf.filho@gmail.com");
				perfil.setTelefone("(81)9 9672-9491");
				
				manager.persist(perfil);
				Logs.info("[PerfilJpa]::primeiroAcesso :: Cadastrado perfil com exito.");
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::primeiroAcesso :: Erro ao tentar cadastrar o perfil, Exception:");
			e.printStackTrace();
		}
	}

	@Override
	public String esqueciMinhaSenha(String nomeOuEmail) {
		try{
			Perfil perfil = getPerfilPorNomeOuEmail(nomeOuEmail);
			if(perfil != null){
				if(ValidaEmail.isValido(perfil.getEmail())){
					boolean enviouMensagem = EnviaEmail.envia(perfil.getEmail(), "TVMais - Recuperação de senha", "Sua senha é: "+perfil.getSenha());
					if(enviouMensagem){
						Logs.info("[PerfilJpa]::esqueciMinhaSenha :: Senha enviada ao email: "+perfil.getEmail());
						return Mensagem.getSuccess("Senha enviada ao email: "+perfil.getEmail());
					}
					else{
						Logs.warn("[PerfilJpa]::esqueciMinhaSenha :: Nao foi possivel acessar o perfil: "+nomeOuEmail);
						return Mensagem.getWarning("Perfil com cadastro inválido.<br>Por favor, contate o suporte técnico.");
					}
				}
				else{
					Logs.warn("[PerfilJpa]::esqueciMinhaSenha :: Nao foi possivel acessar o perfil: "+nomeOuEmail);
					return Mensagem.getWarning("Perfil com cadastro inválido.<br>Por favor, contate o suporte técnico.");
				}
			}
			else{
				Logs.warn("[PerfilJpa]::esqueciMinhaSenha :: Nao foi possivel acessar o perfil: "+nomeOuEmail);
				return Mensagem.getWarning("Perfil não encontrado.");
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::esqueciMinhaSenha :: Erro ao tentar enviar senha por e-mail, Exception:");
			e.printStackTrace();
			return Mensagem.getDanger("Erro no servidor.<br>Por favor, entre em contato com o suporte técnico.");
		}
	}

	@Override
	public Perfil lerPerfil() {
		try{
			Query query = manager.createQuery("select p from Perfil as p");
			Perfil perfil = (Perfil)query.getSingleResult();
			Logs.info("[PerfilJpa]::lerPerfil :: Perfil encontrado: "+perfil.getNome());
			return perfil;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::lerPerfil :: Erro ao tentar pegar informacoes de perfil, Exception:");
			e.printStackTrace();
			return null;
		}
	}
}