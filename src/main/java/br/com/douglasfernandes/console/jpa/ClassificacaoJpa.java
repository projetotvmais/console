package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.ClassificacaoDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Classificacao;

/**
 * Implementação da interface de acesso ao banco de dados desta entidade.
 * @author douglas.f.filho
 *
 */
@Repository
public class ClassificacaoJpa implements ClassificacaoDao{
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public String cadastrar(Classificacao classificacao) {
		try{
			if(classificacao.getNome() != null && !classificacao.getNome().equals("")){
				Classificacao teste = pegarClassificacaoPorNome(classificacao.getNome());
				if(teste == null){
					manager.persist(classificacao);
					Logs.info("[ClassificacaoJpa]::cadastrar: classificacao cadastrada. "+classificacao.toString());
					return Mensagem.getSuccess("Classificação cadastrada com êxito.");
				}
				else{
					Logs.warn("[ClassificacaoJpa]::cadastrar: Ja existe classificacao com este nome. NOME="+classificacao.getNome());
					return Mensagem.getDanger("Já existe classificação com este nome");
				}
			}
			else{
				Logs.warn("[ClassificacaoJpa]::cadastrar: O nome do item nao pode ficar em branco.");
				return Mensagem.getDanger("O nome do item não pode ficar em branco.");
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::cadastrar: Erro ao tentar cadastrar item no banco de dados. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Erro ao tentar cadastrar item no banco de dados.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String atualizar(Classificacao classificacao) {
		try{
			if(classificacao.getNome() != null && !classificacao.getNome().equals("")){
				Classificacao teste = pegarClassificacaoPorNome(classificacao.getNome());
				if(teste == null || teste.getId() == classificacao.getId()){
					manager.merge(classificacao);
					Logs.info("[ClassificacaoJpa]::atualizar: classificacao atualizada. "+classificacao.toString());
					return Mensagem.getSuccess("Classificação atualizada com êxito.");
				}
				else{
					Logs.warn("[ClassificacaoJpa]::atualizar: Ja existe classificacao com este nome. NOME="+classificacao.getNome());
					return Mensagem.getDanger("Já existe classificação com este nome");
				}
			}
			else{
				Logs.warn("[ClassificacaoJpa]::atualizar: O nome do item nao pode ficar em branco.");
				return Mensagem.getDanger("O nome do item não pode ficar em branco.");
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::atualizar: Erro ao tentar atualizar item do banco de dados. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Erro ao tentar atualizar item do banco de dados.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String remover(long id) {
		try{
			Classificacao classificacao = pegarClassificacao(id);
			manager.remove(classificacao);
			Logs.info("[ClassificacaoJpa]::remover: item removido.");
			return Mensagem.getSuccess("Classificação removida com êxito.");
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::remover: Erro ao tentar remover item do banco de dados. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Erro ao tentar remover item do banco de dados.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public List<Classificacao> listar() {
		try{
			Query query = manager.createQuery("select c from Classificacao as c");
			@SuppressWarnings("unchecked")
			List<Classificacao> classificacoes = query.getResultList();
			if(classificacoes != null && classificacoes.size() > 0)
				Logs.info("[ClassificacaoJpa]:: listar: " + classificacoes.size() + " classificacoes encontradas.");
			return classificacoes;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::listar: Erro ao tentar pegar classificacoes. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Classificacao pegarClassificacao(long id) {
		try{
			Query query = manager.createQuery("select c from Classificacao as c where c.id = :id");
			query.setParameter("id", id);
			Classificacao classificacao = (Classificacao) query.getSingleResult();
			if(classificacao != null)
				Logs.info("[ClassificacaoJpa]:: pegarClassificacao: Classificacao encontrada: "+classificacao.getNome());
			return classificacao;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::pegarClassificacao: Erro ao tentar pegar classificacao por id. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	private Classificacao pegarClassificacaoPorNome(String nome) {
		try{
			Query query = manager.createQuery("select c from Classificacao as c where c.nome = :nome");
			query.setParameter("nome", nome);
			Classificacao classificacao = (Classificacao) query.getSingleResult();
			if(classificacao != null)
				Logs.info("[ClassificacaoJpa]:: pegarClassificacaoPorNome: Classificacao encontrada: "+classificacao.getNome());
			return classificacao;
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::pegarClassificacaoPorNome: Erro ao tentar pegar classificacao por nome. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void primeiroAcesso() {
		try{
			List<Classificacao> classificacoes = listar();
			if(classificacoes == null || classificacoes.size() < 1){
				Classificacao esportes = new Classificacao();
				esportes.setNome("Esportes");
				cadastrar(esportes);
				
				Classificacao adulto = new Classificacao();
				adulto.setNome("Adulto");
				cadastrar(adulto);
				
				Classificacao infantil = new Classificacao();
				infantil.setNome("Infantil");
				cadastrar(infantil);
				
				Classificacao serie = new Classificacao();
				serie.setNome("Série");
				cadastrar(serie);
				
				Classificacao filme = new Classificacao();
				filme.setNome("Filme");
				cadastrar(filme);
				
				Classificacao cultura = new Classificacao();
				cultura.setNome("Cultura");
				cadastrar(cultura);
				
				Classificacao noticias = new Classificacao();
				noticias.setNome("Noticias");
				cadastrar(noticias);
				
				Classificacao aberto = new Classificacao();
				aberto.setNome("Aberto");
				cadastrar(aberto);
			}
		}
		catch(Exception e){
			Logs.warn("[ClassificacaoJpa]::primeiroAcesso: Erro ao tentar cadastrar classificacoes. Exception: ");
			e.printStackTrace();
		}
	}	
}