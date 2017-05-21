package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Canal;

/**
 * Implementação da interface de acesso ao conteudo de canais.
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
				Canal teste = pegarPorNome(canal.getNome());
				if(teste == null){
					if(canal.getLogo() == null || canal.getLogo().length < 1)
						canal.setDefaultLogo();
					if(canal.getUrl() != null && !canal.getUrl().equals("")){
						teste = pegarPorUrl(canal.getUrl());
						if(teste == null){
							canal.setFuncionando(true);
							manager.persist(canal);
							Logs.info("[CanalJpa]::cadastrar: Canal registrado com exito.");
							return Mensagem.getSuccess("Canal registrado com êxito.");
						}
						else{
							Logs.warn("[CanalJpa]::cadastrar: Ja existe um canal registrado com esta URL.");
							return Mensagem.getWarning("Já existe um canal registrado com esta URL.");
						}
					}
					else{
						Logs.warn("[CanalJpa]::cadastrar: A url do canal nao pode ser nula.");
						return Mensagem.getWarning("A url do canal não pode ser nula.");
					}
				}
				else{
					Logs.warn("[CanalJpa]::cadastrar: Ja existe um canal cadastrado com este nome. NOME="+canal.getNome());
					return Mensagem.getWarning("Já existe um canal com este nome.");
				}
			}
			else{
				Logs.warn("[CanalJpa]::cadastrar: O nome do canal nao pode ser nulo.");
				return Mensagem.getWarning("O nome do canal não pode ser nulo.");
			}
		}
		catch(Exception e){
			Logs.warn("[CanalJpa]::cadastrar: Ocorreu um erro ao tentar cadastrar o canal. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Ocorreu um erro ao tentar cadastrar o canal.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String atualizar(Canal canal) {
		try{
			if(canal.getNome() != null && !canal.getNome().equals("")){
				Canal teste = pegarPorNome(canal.getNome());
				
				if(teste == null || teste.getId() == canal.getId()){
					if(canal.getLogo() == null || canal.getLogo().length < 1){
						byte[] logoOriginal = pegarLogoDoCanal(canal.getId());
						canal.setLogo(logoOriginal);
					}
					if(canal.getUrl() != null && !canal.getUrl().equals("")){
						teste = pegarPorUrl(canal.getUrl());
						if(teste != null){
							manager.merge(canal);
							Logs.info("[CanalJpa]::atualizar: Canal atualizado com exito.");
							return Mensagem.getSuccess("Canal atualizado com êxito.");
						}
						else{
							Logs.warn("[CanalJpa]::atualizar: Ja existe um canal registrado com esta URL.");
							return Mensagem.getWarning("Já existe um canal registrado com esta URL.");
						}
					}
					else{
						Logs.warn("[CanalJpa]::atualizar: A url do canal nao pode ser nula.");
						return Mensagem.getWarning("A url do canal não pode ser nula.");
					}
				}
				else{
					Logs.warn("[CanalJpa]::atualizar: Ja existe um canal cadastrado com este nome. NOME="+canal.getNome());
					return Mensagem.getWarning("Já existe um canal com este nome.");
				}
			}
			else{
				Logs.warn("[CanalJpa]::atualizar: O nome do canal nao pode ser nulo.");
				return Mensagem.getWarning("O nome do canal não pode ser nulo.");
			}
		}
		catch(Exception e){
			Logs.warn("[CanalJpa]::atualizar: Ocorreu um erro ao tentar atualizar o canal. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Ocorreu um erro ao tentar atualizar o canal.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public Canal pegarPorId(long id){
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.id = :id");
			query.setParameter("id", id);
			Canal canal = (Canal) query.getSingleResult();
			if(canal != null){
				Logs.info("[CanalJpa]:: pegarPorId: Canal encontrado: "+canal.getNome());
				return canal;
			}
			else{
				Logs.warn("[ClassificacaoJpa]::pegarPorId: Erro ao tentar pegar canal por id. Canal não encontrado ID = "+id);
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::pegarPorId: Erro ao tentar pegar canal por id. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	private Canal pegarPorNome(String nome){
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.nome = :nome");
			query.setParameter("nome", nome);
			Canal canal = (Canal) query.getSingleResult();
			if(canal != null){
				Logs.info("[CanalJpa]:: pegarPorNome: Canal encontrado: "+canal.getNome());
				return canal;
			}
			else{
				Logs.warn("[ClassificacaoJpa]::pegarPorNome: Erro ao tentar pegar canal por nome. Canal não encontrado NOME = "+nome);
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::pegarPorNome: Erro ao tentar pegar canal por id. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	private Canal pegarPorUrl(String url){
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.url = :url");
			query.setParameter("url", url);
			Canal canal = (Canal) query.getSingleResult();
			if(canal != null){
				Logs.info("[CanalJpa]:: pegarPorUrl: Canal encontrado: "+canal.getNome());
				return canal;
			}
			else{
				Logs.warn("[ClassificacaoJpa]::pegarPorUrl: Erro ao tentar pegar canal pela url. Canal não encontrado URL = "+url);
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::pegarPorUrl: Erro ao tentar pegar canal pela url. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String remover(long id) {
		try{
			Canal canal = pegarPorId(id);
			manager.remove(canal);
			Logs.info("[CanalJpa]::remover: Canal removido com exito.");
			return Mensagem.getSuccess("Canal removido com êxito.");
		}
		catch(Exception e){
			Logs.warn("[CanalJpa]::remover: Ocorreu um erro ao tentar remover o canal. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Ocorreu um erro ao tentar remover o canal.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public List<Canal> listarPorClassificacao(String classificacao) {
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.classificacao.nome like :classificacao");
			classificacao = "%" + classificacao + "%";
			query.setParameter("classificacao", classificacao);
			@SuppressWarnings("unchecked")
			List<Canal> canais = query.getResultList();
			Logs.info("[CanalJpa]:: listarPorClassificacao: Canais encontrados: "+canais.size());
			return canais;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::listarPorClassificacao: Erro ao tentar pegar canais por classificacao. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Canal> listarPorNome(String nome) {
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.nome like :nome");
			nome = "%" + nome + "%";
			query.setParameter("nome", nome);
			@SuppressWarnings("unchecked")
			List<Canal> canais = query.getResultList();
			Logs.info("[CanalJpa]:: listarPorNome: Canais encontrados: "+canais.size());
			return canais;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::listarPorNome: Erro ao tentar pegar canais por nome. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Canal> listarPorStatus(boolean funcionando) {
		try{
			Query query = manager.createQuery("select ch from Canal as ch where ch.funcionando = :funcionando");
			query.setParameter("funcionando", funcionando);
			@SuppressWarnings("unchecked")
			List<Canal> canais = query.getResultList();
			Logs.info("[CanalJpa]:: listarPorStatus: Canais encontrados: "+canais.size());
			return canais;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::listarPorStatus: Erro ao tentar pegar canais por status. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] pegarLogoDoCanal(long id) {
		try{
			Canal canal = pegarPorId(id);
			if(canal != null){
				if(canal.getLogo() != null && canal.getLogo().length > 1){
					Logs.info("[CanalJpa]::pegarLogoDoCanal: Imagem de "+canal.getLogo().length+" bytes");
					return canal.getLogo();
				}
				else{
					Logs.warn("[CanalJpa]::pegarLogoDoCanal: Erro tentando pegar imagem do canal. Imagem nula ou quebrada.");
					return null;
				}
			}
			else{
				Logs.warn("[CanalJpa]::pegarLogoDoCanal: Erro tentando pegar imagem do canal. O canal não foi encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[CanalJpa]::pegarLogoDoCanal: Erro tentando pegar imagem do canal. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

}
