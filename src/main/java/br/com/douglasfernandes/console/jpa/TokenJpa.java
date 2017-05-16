package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;
import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.TokenDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Token;

/**
 * Implementacao do acesso ao banco de dados para operar tokens
 * @author douglas.f.filho
 *
 */
@Repository
public class TokenJpa implements TokenDao{
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public String cadastrar(Token token) {
		try{
			if(token.getNome() != null && !token.getNome().equals("")){
				manager.persist(token);
				String validade = FMT.getStringFromCalendar(token.getValidade(), DateFormat.DMYHM);
				Logs.info("[TokenJpa]::cadastrar::Token valido ate "+validade);
				return Mensagem.getSuccess("Token cadastrado válido até "+validade+".");
			}
			else{
				Logs.info("[TokenJpa]::cadastrar::Token sem nome.");
				return Mensagem.getWarning("Token não cadastrado por não ter um nome válido.");
			}
		}
		catch(Exception e){
			Logs.warn("[TokenJpa]::cadastrar::Impossivel cadastrar novo token. Exception:");
			e.printStackTrace();
			return Mensagem.getDanger("Houve um erro ao tentar cadastrar este token.<br>Contate o suporte técnico.");
		}
	}

	private Token pegarPorId(long id){
		try{
			Query query = manager.createQuery("select t from Token as t where t.id = :id");
			query.setParameter("id", id);
			Token token = (Token)query.getSingleResult();
			if(token != null){
				Logs.info("[TokenJpa]::pegarPorId::Token obtido: "+token.toString());
				return token;
			}
			else{
				Logs.warn("[TokenJpa]::pegarPorId::Token nulo.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[TokenJpa]::pegarPorId::Erro ao tentar achar token por id. Exception:");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String remover(long id) {
		try{
			Token token = pegarPorId(id);
			if(token != null){
				manager.remove(token);
				Logs.info("[TokenJpa]::remover::Token removido: "+token.toString());
				return Mensagem.getSuccess("Token removido com êxito.");
			}
			else{
				Logs.warn("[TokenJpa]::remover::Token nulo.");
				return Mensagem.getWarning("Não é possível remover um token nulo.<br>Contate o suporte técnico.");
			}
		}
		catch(Exception e){
			Logs.warn("[TokenJpa]::remover::Impossivel remover este token. Exception:");
			e.printStackTrace();
			return Mensagem.getDanger("Houve um erro ao tentar remover este token.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public List<Token> listar() {
		try{
			Query query = manager.createQuery("select t from Token as t");
			@SuppressWarnings("unchecked")
			List<Token> lista = query.getResultList();
			if(lista != null && lista.size() > 0){
				Logs.warn("[TokenJpa]::listar::Lista obtida com "+lista.size()+" itens.");
				return lista;
			}
			else{
				Logs.warn("[TokenJpa]::listar::Lista vazia.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[TokenJpa]::listar::Impossivel listar tokens. Exception:");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Token validar(String nome){
		try{
			Query query = manager.createQuery("select t from Token as t where t.nome = :nome");
			query.setParameter("nome", nome);
			Token token = (Token)query.getSingleResult();
			if(token != null){
				Logs.info("[TokenJpa]::validar::Token obtido: "+token.toString());
				return token;
			}
			else{
				Logs.warn("[TokenJpa]::validar::Token nulo.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[TokenJpa]::validar::Erro ao tentar achar token por nome. Exception:");
			e.printStackTrace();
			return null;
		}
	}
}
