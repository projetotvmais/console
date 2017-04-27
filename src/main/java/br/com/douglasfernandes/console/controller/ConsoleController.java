package br.com.douglasfernandes.console.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.PerfilDao;

@Transactional
@Controller
public class ConsoleController {
	
	@Qualifier("perfilJpa")
	@Autowired
	private PerfilDao perfilDao;
	
	String mensagem = "";
	
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
	public String tentaEntrarComUsuarioESenha(String nome, String senha, HttpSession session){
		try{
			mensagem = perfilDao.tentarLogar(nome, senha, session);
			if(Mensagem.isSuccess(mensagem)){
				return "redirect:home";
			}
			return "redirect:login";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	private void chamaPrimeiroAcesso(){
		perfilDao.primeiroAcesso();
	}
	
}
