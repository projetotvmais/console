package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.UsuarioDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Usuario;

/**
 * Implementação das atividades sobre registros de usuários.
 * @author douglas.f.filho
 *
 */
@Repository
public class UsuarioJpa implements UsuarioDao{

	@PersistenceContext
	EntityManager manager;

	@Override
	public Usuario pegarPorId(long id) {
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.id = :id");
			q.setParameter("id", id);
			Usuario user = (Usuario)q.getSingleResult();
			if(user != null){
				Logs.info("[UsuarioJpa]::pegarPorId::Usuario obtido: "+user.toString());
				return user;
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarPorId::Usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarPorId::Erro tentando pegar usuario por id. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Usuario> listar(String nome) {
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.nome like :nome or user.identificacao like :nome");
			String name = "%"+nome+"%";
			q.setParameter("nome", name);
			@SuppressWarnings("unchecked")
			List<Usuario> lista = q.getResultList();
			if(lista != null && lista.size() > 0){
				Logs.info("[UsuarioJpa]::listar::Lista obtida: ");
				for(Usuario u : lista){
					Logs.info("[UsuarioJpa]::listar::----> "+u.toString());
				}
				return lista;
			}
			else{
				Logs.warn("[UsuarioJpa]::listar::Nenhum usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::listar::Erro tentando listar usuarios por nome. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	private Usuario pegarPorNome(String nome){
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.nome = :nome");
			q.setParameter("nome", nome);
			Usuario user = (Usuario)q.getSingleResult();
			if(user != null){
				Logs.info("[UsuarioJpa]::pegarPorNome::Usuario obtido: "+user.toString());
				return user;
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarPorNome::Usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarPorNome::Erro tentando pegar usuario por nome. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	private Usuario pegarPorIdentificacao(String identificacao){
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.identificacao = :identificacao");
			q.setParameter("identificacao", identificacao);
			Usuario user = (Usuario)q.getSingleResult();
			if(user != null){
				Logs.info("[UsuarioJpa]::pegarPorIdentificacao::Usuario obtido: "+user.toString());
				return user;
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarPorIdentificacao::Usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarPorIdentificacao::Erro tentando pegar usuario por identificacao. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	private Usuario pegarPorEmail(String email){
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.email = :email");
			q.setParameter("email", email);
			Usuario user = (Usuario)q.getSingleResult();
			if(user != null){
				Logs.info("[UsuarioJpa]::pegarPorEmail::Usuario obtido: "+user.toString());
				return user;
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarPorEmail::Usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarPorEmail::Erro tentando pegar usuario por e-mail. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	private Usuario pegarPorTelefone(String telefone){
		try{
			Query q = manager.createQuery("select user from Usuario as user where user.telefone = :telefone");
			q.setParameter("telefone", telefone);
			Usuario user = (Usuario)q.getSingleResult();
			if(user != null){
				Logs.info("[UsuarioJpa]::pegarPorTelefone::Usuario obtido: "+user.toString());
				return user;
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarPorTelefone::Usuario nao encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarPorTelefone::Erro tentando pegar usuario por telefone. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String cadastrar(Usuario usuario) {
		try{
			String nome = usuario.getNome();
			Usuario teste = pegarPorNome(nome);
			if(teste == null){
				String identificacao = usuario.getIdentificacao();
				teste = pegarPorIdentificacao(identificacao);
				if(teste == null){
					String email = usuario.getEmail();
					teste = pegarPorEmail(email);
					if(teste == null){
						String telefone = usuario.getTelefone();
						teste = pegarPorTelefone(telefone);
						if(teste == null){
							manager.persist(usuario);
							Logs.warn("[UsuarioJpa]::cadastrar::Usuario cadastrado: "+usuario.toString());
							return Mensagem.getSuccess("Usuário cadastrado com êxito.");
						}
						else{
							Logs.warn("[UsuarioJpa]::cadastrar::Ja existe um usuario com este numero de telefone. ("+telefone+")");
							return Mensagem.getWarning("Já existe um usuário cadastrado com este número de telefone.");
						}
					}
					else{
						Logs.warn("[UsuarioJpa]::cadastrar::Ja existe um usuario com este email. ("+email+")");
						return Mensagem.getWarning("Já existe um usuário cadastrado com este email.");
					}
				}
				else{
					Logs.warn("[UsuarioJpa]::cadastrar::Ja existe um usuario com este numero de identificacao. ("+identificacao+")");
					return Mensagem.getWarning("Já existe um usuário cadastrado com este número de identificação.");
				}
			}
			else{
				Logs.warn("[UsuarioJpa]::cadastrar::Ja existe um usuario com este nome. ("+nome+")");
				return Mensagem.getWarning("Já existe um usuário cadastrado com este nome.");
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::cadastrar::Erro tentando cadastrar usuario. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Não foi possível cadastrar o usuário.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String atualizar(Usuario usuario) {
		try{
			manager.merge(usuario);
			Logs.info("[UsuarioJpa]::atualizar::Usuario atualizado: "+usuario.toString());
			return Mensagem.getSuccess("Usuário atualizado com êxito.");
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::atualizar::Erro tentando atualizar usuario. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Não foi possível atualizar o usuário.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public String desativar(long id) {
		try{
			Usuario usuario = pegarPorId(id);
			usuario.setAtivo(false);
			manager.merge(usuario);
			
			Logs.info("[UsuarioJpa]::desativar::Usuario desativado: "+usuario.toString());
			return Mensagem.getSuccess("Usuário desativado com êxito.");
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::desativar::Erro tentando desativar usuario. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Não foi possível desativar o usuário.<br>Contate o suporte técnico.");
		}
	}

	@Override
	public byte[] pegarFotoDeUsuario(long id) {
		try{
			Usuario usuario = pegarPorId(id);
			if(usuario != null){
				if(usuario.getFoto() != null && usuario.getFoto().length > 1){
					Logs.info("[UsuarioJpa]::pegarFotoDeUsuario::Imagem de "+usuario.getFoto().length+" bytes");
					return usuario.getFoto();
				}
				else{
					Logs.warn("[UsuarioJpa]::pegarFotoDeUsuario::Erro tentando pegar imagem do usuario. Imagem nula ou quebrada.");
					return null;
				}
			}
			else{
				Logs.warn("[UsuarioJpa]::pegarFotoDeUsuario::Erro tentando pegar imagem do usuario. O usuario não foi encontrado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::pegarFotoDeUsuario::Erro tentando pegar imagem do usuario. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String reativar(long id) {
		try{
			Usuario usuario = pegarPorId(id);
			usuario.setAtivo(true);
			manager.merge(usuario);
			
			Logs.info("[UsuarioJpa]::reativar::Usuario reativado: "+usuario.toString());
			return Mensagem.getSuccess("Usuário reativado com êxito.");
		}
		catch(Exception e){
			Logs.warn("[UsuarioJpa]::reativar::Erro tentando reativar usuario. Exception: ");
			e.printStackTrace();
			return Mensagem.getDanger("Não foi possível reativar o usuário.<br>Contate o suporte técnico.");
		}
	}
}
