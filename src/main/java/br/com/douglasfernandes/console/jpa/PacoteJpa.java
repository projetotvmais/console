package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.PacoteDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Pacote;

/**
 * Implementação da interface de acesso ao banco de dados.
 * @author douglas.f.filho
 *
 */
@Repository
public class PacoteJpa implements PacoteDao{
	
	@PersistenceContext
	EntityManager manager;

	/**
	 * Pega informações de pacote de acordo com o id passado.
	 * @param id
	 * @return
	 */
	@Override
	public Pacote pegarPacotePorId(long id){
		try{
			Query q = manager.createQuery("select p from Pacote as p where p.id = :id");
			q.setParameter("id", id);
			Pacote pacote = (Pacote)q.getSingleResult();
			if(pacote != null){
				Logs.info("[PacoteJpa]::pegarPacotePorId::Pacote recuperado por id. ("+pacote.getNome()+")");
				return pacote;
			}
			else{
				Logs.warn("[PacoteJpa]::pegarPacotePorId::Nenhum pacote cadastrado com este id.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::pegarPacotePorId::Erro ao tentar pegar dados de pacote por Id. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Verifica se ja tem um pacote cadastrado com o mesmo nome.
	 * @param pacote
	 * @return
	 */
	private boolean jaExiste(Pacote pacote){
		try{
			Query q = manager.createQuery("select p from Pacote as p where p.nome = :nome");
			q.setParameter("nome", pacote.getNome());
			@SuppressWarnings("unchecked")
			List<Pacote> lista = q.getResultList();
			if(lista != null && lista.size() > 0){
				Logs.warn("[PacoteJpa]::jaExiste::Ja existe um pacote cadastrado com o mesmo nome. ("+pacote.getNome()+")");
				return true;
			}
			else{
				Logs.warn("[PacoteJpa]::jaExiste::Nenhum pacote cadastrado com este nome. ("+pacote.getNome()+")");
				return false;
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::jaExiste::Erro ao tentar verificar se ja exite algum pacote com o mesmo nome. Exception: ");
			e.printStackTrace();
			return true;
		}
	}
	
	@Override
	public String cadastrar(Pacote pacote) {
		try{
			if(jaExiste(pacote)){
				Logs.warn("[PacoteJpa]::cadastrar::Ja existe um pacote cadastrado com o mesmo nome. ("+pacote.getNome()+")");
				return Mensagem.getWarning("Já exite um pacote cadastrado com o mesmo nome.");
			}
			else{
				manager.persist(pacote);
				Logs.info("[PacoteJpa]::cadastrar::Pacote cadastrado com exito. ("+pacote.getNome()+")");
				return Mensagem.getSuccess("Pacote cadastrado com êxito.");
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::cadastrar::Erro ao tentar cadastrar um novo pacote. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Houve um erro ao tentar cadastrar um novo pacote.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String atualizar(Pacote pacote) {
		try{
			Pacote cadastrado = pegarPacotePorId(pacote.getId());
			String nomeAtual = cadastrado.getNome();
			if(!nomeAtual.equals(pacote.getNome()) && jaExiste(pacote)){
				Logs.warn("[PacoteJpa]::atualizar::Ja existe um pacote cadastrado com o mesmo nome. ("+pacote.getNome()+")");
				return Mensagem.getWarning("Já exite um pacote cadastrado com o mesmo nome.");
			}
			else{
				manager.merge(pacote);
				Logs.info("[PacoteJpa]::atualizar::Pacote atualizado com exito. ("+pacote.getNome()+")");
				return Mensagem.getSuccess("Pacote atualizado com êxito.");
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::atualizar::Erro ao tentar atualizar o pacote. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Houve um erro ao tentar atualizar este pacote.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String remover(long id) {
		try{
			Pacote pacote = pegarPacotePorId(id);
			if(pacote != null){
				manager.remove(pacote);
				Logs.info("[PacoteJpa]::remover::Pacote removido com exito. ("+pacote.getNome()+")");
				return Mensagem.getSuccess("Pacote removido com êxito.");
			}
			else{
				Logs.warn("[PacoteJpa]::remover::Pacote nao encontrado.");
				return Mensagem.getWarning("Não foi possível localizar o pacote a ser removido.<br>Contate o suporte técnico.");
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::remover::Erro ao tentar remover o pacote. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Houve um erro ao tentar remover este pacote.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public byte[] pegarLogoDoPacote(long id) {
		try{
			Pacote pacote = pegarPacotePorId(id);
			if(pacote != null){
				Logs.info("[PacoteJpa]::pegarLogoDoPacote::Logo recuperada do pacote "+pacote.getNome());
				return pacote.getLogo();
			}
			else{
				Logs.warn("[PacoteJpa]::pegarLogoDoPacote::Nao foi possivel pegar a logo no pacote pelo Id.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::pegarLogoDoPacote::Nao foi possivel pegar a logo no pacote pelo Id. Exception: ");
			return null;
		}
	}

	@Override
	public List<Pacote> listar(String nome) {
		try{
			Query q = manager.createQuery("select p from Pacote as p where p.nome like :nome");
			String name = "%"+nome+"%";
			q.setParameter("nome", name);
			@SuppressWarnings("unchecked")
			List<Pacote> lista = q.getResultList();
			if(lista != null && lista.size() > 0){
				Logs.info("[PacoteJpa]::listar::"+lista.size()+" pacotes recuperados");
				return lista;
			}
			else{
				Logs.warn("[PacoteJpa]::listar::Nenhum pacote cadastrado com este nome. ("+nome+")");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PacoteJpa]::listar::Erro ao tentar listar pacote por nome. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
}
