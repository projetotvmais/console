package br.com.douglasfernandes.console.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Perfil;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter
{
	@Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object controller) throws Exception
	 {
		  String uri = request.getRequestURI();
	      Logs.info("[AutorizadorInterceptor INFO]: URL("+uri+")");
		  
	      if(uri.endsWith("login")||uri.endsWith("entrar")||uri.contains("resources")||uri.contains("erro")) {
	    	  Logs.info("[AutorizadorInterceptor INFO]: Area externa.");
	    	  return true;
	      }
	      
	      Perfil logado = (Perfil)request.getSession().getAttribute("logado");
	      if(logado != null && !logado.getNome().equals("")){
	    	  Logs.info("[AutorizadorInterceptor INFO]: Usuario logado.");
	    	  return true;
	      }
	      
	      Logs.warn("[AutorizadorInterceptor WARN]: Login necessario.");
	      response.sendRedirect("login");
	      return false;
	 }
}