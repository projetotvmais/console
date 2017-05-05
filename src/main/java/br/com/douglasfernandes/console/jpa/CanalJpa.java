package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Canal;

/**
 * Implementa��o da interface de acesso ao conteudo de canais.
 * @author douglas.f.filho
 *
 */
@Repository
public class CanalJpa implements CanalDao{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public String cadastrar(Canal canal) {
		try{
			if(canal.getNome() != null && !canal.getNome().equals("")){
				if(canal.getLogo() == null || canal.getLogo().length < 1)
					canal.setDefaultLogo();
				if(canal.getUrl() != null && !canal.getUrl().equals("")){
					canal.setFuncionando(true);
					manager.persist(canal);
					Logs.info("[CanalJpa]::cadastrar: Canal registrado com �xito.");
					return Mensagem.getSuccess("Canal registrado com �xito.");
				}
				else{
					Logs.warn("[CanalJpa]::cadastrar: A url do canal n�o pode ser nula.");
					return Mensagem.getWarning("A url do canal n�o pode ser nula.");
				}
			}
			else{
				Logs.warn("[CanalJpa]::cadastrar: O nome do canal n�o pode ser nulo.");
				return Mensagem.getWarning("O nome do canal n�o pode ser nulo.");
			}
		}
		catch(Exception e){
			Logs.warn("[CanalJpa]::cadastrar: Ocorreu um erro ao tentar cadastrar o canal. Contate o suporte t�cnico. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Ocorreu um erro ao tentar cadastrar o canal. Contate o suporte t�cnico.");
		}
	}

	@Override
	public String atualizar(Canal canal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String remover(Canal canal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Canal> listarPorClassificacao(String classificaco) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Canal> listarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Canal> listarPorStatus(boolean funcionando) {
		// TODO Auto-generated method stub
		return null;
	}

}
