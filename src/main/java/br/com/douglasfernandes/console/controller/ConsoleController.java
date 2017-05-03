package br.com.douglasfernandes.console.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.PerfilDao;
import br.com.douglasfernandes.console.logger.Logs;

@Transactional
@Controller
public class ConsoleController {
	
	@Qualifier("perfilJpa")
	@Autowired
	private PerfilDao perfilDao;
	
	String mensagem = "";
	
//	XXX Gerenciamento de entrada, login, logout e perfil.
	
	@RequestMapping(value = {"home","/"})
	public String home(Model model){
		try{
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			return "index";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("login")
	public String apresentaTelaDeLogin(Model model){
		try{
			chamaPrimeiroAcesso();
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			return "login/login";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("entrar")
	public String tentaEntrarComUsuarioESenha(String nome, String senha, Boolean esqueciFlag, HttpSession session){
		try{
			if(esqueciFlag){
				mensagem = perfilDao.esqueciMinhaSenha(nome);
				return "redirect:login";
			}
			else{
				mensagem = perfilDao.tentarLogar(nome, senha, session);
				if(Mensagem.isSuccess(mensagem)){
					return "redirect:home";
				}
				return "redirect:login";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		try{
			session.invalidate();
			mensagem = Mensagem.getInfo("Você saiu do sistema.");
			return "redirect:login";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("mostrarFotoDoPerfil")
	public void mostrarFotoDoPerfil(String nomeOuEmail, HttpServletResponse response){
		try{
			byte[] foto = perfilDao.pegarFoto(nomeOuEmail);
			if(foto != null)
			{
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				response.getOutputStream().write(foto);
				response.getOutputStream().close();
			}
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::mostrarFotoDoPerfil: Erro tentando pegar foto do perfil. Exception:");
			e.printStackTrace();
		}
	}
	
	/**
	 * Cadastra um perfil padrao caso seja a primeira execucao do sistema.
	 */
	private void chamaPrimeiroAcesso(){
		perfilDao.primeiroAcesso();
	}
	
//	XXX Gerenciamento de recursos de canais.
	
	/**
	 * Devolve a tela de gerenciamento de canais.
	 * @return
	 */
	@RequestMapping("canais")
	public String canais(Model model){
		try{
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			return "canais/canais";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Método que devolve o canal para determinada tag de video do front.
	 */
	@RequestMapping("pegarCanal")
	public void pegarCanal(long id, HttpServletResponse response){
		try{
			
			
			
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::pegarCanal: Erro ao tentar pegar canal. Exception:");
			e.printStackTrace();
		}
	}
	
}
