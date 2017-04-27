package br.com.douglasfernandes.console.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.console.controller.utils.LoginUtils;

@Controller
public class ConsoleController {
	
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
