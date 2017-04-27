package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.PerfilDao;
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
		Query query = manager.createQuery("select p from Perfil as p where p.nome = :nome or p.email = :email");
		query.setParameter("nome", nomeOuEmail);
		query.setParameter("email", nomeOuEmail);
		Perfil perfil = (Perfil)query.getSingleResult();
		return perfil;
	}
	
	@Override
	public String atualizar(Perfil perfil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tentarLogar(String nomeOuEmail, String senha, HttpSession session) {
		Perfil perfil = getPerfilPorNomeOuEmail(nomeOuEmail);
		if(perfil != null){
			if(senha.equals(perfil.getSenha())){
				session.setAttribute("logado", perfil);
				return Mensagem.getSuccess("Bem Vindo, "+perfil.getNome()+".");
			}
			else
				return Mensagem.getWarning("Senha incorreta.");
		}
		else
			return Mensagem.getDanger("Não há usuário cadastrado com esse nome.");
	}

	@Override
	public byte[] pegarFoto(String nomeOuEmail) {
		Perfil perfil = getPerfilPorNomeOuEmail(nomeOuEmail);
		if(perfil != null && perfil.getFoto() != null && perfil.getFoto().length > 1)
			return perfil.getFoto();
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	private boolean temPerfilCadastrado(){
		Query query = manager.createQuery("select p from Perfil as p");
		List<Perfil> perfis = query.getResultList();
		if(perfis != null && perfis.size() > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void primeiroAcesso() {
		try{
			if(temPerfilCadastrado()){
				Perfil perfil = new Perfil();
				perfil.setNome("Administrador");
				perfil.setSenha("adm");
				perfil.setDefaultFoto();
				perfil.setEmail("douglasf.filho@gmail.com");
				perfil.setTelefone("(81)9 9672-9491");
				perfil.setEndereco("Rua Argina Aguiar");
				perfil.setNumero("206");
				perfil.setBairro("Tejipió");
				perfil.setCidade("Recife");
				perfil.setEstado("PE");
				perfil.setCep("50.920-600");
				
				manager.persist(perfil);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
