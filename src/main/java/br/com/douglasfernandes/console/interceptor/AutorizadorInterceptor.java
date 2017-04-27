package br.com.douglasfernandes.console.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.douglasfernandes.console.logger.Logs;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter
{
	@Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object controller) throws Exception
	 {
		  String uri = request.getRequestURI();
	      Logs.info("[AutorizadorInterceptor INFO]: URL("+uri+")");
		  
	      if(uri.contains("WEB-INF")||uri.endsWith("esqueciSenha")||uri.contains("getPontos")||uri.endsWith("login")||uri.endsWith("entrar")||uri.contains("resources")||uri.contains("tags/")||uri.contains("erro")) {
	    	  Logs.info("[AutorizadorInterceptor INFO]: Area externa.");
	    	  return true;
	      }
	      
	      //TODO verifica se tem perfil logado antes.
	      
	      Logs.warn("[AutorizadorInterceptor WARN]: Login necessario.");
	      response.sendRedirect("login");
	      return false;
	 }
}