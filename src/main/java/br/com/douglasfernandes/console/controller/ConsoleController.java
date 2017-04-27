package br.com.douglasfernandes.console.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.console.controller.utils.LoginUtils;
import br.com.douglasfernandes.console.model.Perfil;

@Controller
public class ConsoleController {
	String mensagem = "";
	
	@RequestMapping(value = {"home","/"})
	public String home(){
		try{
			return "index";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("login")
	public String apresentaTelaDeLogin(){
		try{
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
			Perfil logou = LoginUtils.tentaLogar(nome, senha);
			if(logou){
				
				return "redirect:home";
			}
			return "redirect:login";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
}
