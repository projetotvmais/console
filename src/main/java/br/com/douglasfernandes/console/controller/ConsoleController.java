package br.com.douglasfernandes.console.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.console.controller.parsers.CanalParser;
import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.dao.ClassificacaoDao;
import br.com.douglasfernandes.console.dao.PerfilDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Canal;
import br.com.douglasfernandes.console.model.Classificacao;

@Transactional
@Controller
public class ConsoleController {
	
	@Qualifier("perfilJpa")
	@Autowired
	private PerfilDao perfilDao;
	
	@Qualifier("canalJpa")
	@Autowired
	private CanalDao canalDao;
	
	@Qualifier("classificacaoJpa")
	@Autowired
	private ClassificacaoDao classificacaoDao;
	
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
		classificacaoDao.primeiroAcesso();
	}
	
//	XXX Gerenciamento de recursos de canais.
	
	/**
	 * Devolve a tela de gerenciamento de canais.
	 * @return
	 */
	@RequestMapping("canais")
	public String canais(Model model){
		try{
			List<Classificacao> classificacoes = classificacaoDao.listar();
			model.addAttribute("classificacoes",classificacoes);
			Logs.info("[ConsoleController]::canais: lista de classificacoes: "+classificacoes.toString());
			
			List<Canal> canais = canalDao.listarPorNome("");
			
			model.addAttribute("canais", canais);
			Logs.info("[ConsoleController]::canais: lista de canais: "+canais.size());
			
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			
			return "canais/canais";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::canais: Erro tentanto listar canais. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Cadastrar um novo canal
	 * @return
	 */
	@RequestMapping("cadastrarCanal")
	public String cadastrarCanal(CanalParser parser, HttpServletRequest request){
		try{
			Logs.info("[ConsoleController]::cadastrarCanal: Request: "+ parser.toString());
			
			Canal canal = parser.toCanal(classificacaoDao);
			mensagem = canalDao.cadastrar(canal);
			Logs.info("[ConsoleController]::cadastrarCanal: Canal cadastrado com exito: "+canal.toString());
			
			return "redirect:canais";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::cadastrarCanal: Erro tentanto cadastrar canal. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Cadastrar um novo canal
	 * @return
	 */
	@RequestMapping("atualizarCanal")
	public String atualizarCanal(CanalParser parser, HttpServletRequest request){
		try{
			Logs.info("[ConsoleController]::atualizarCanal: Request: "+ parser.toString());
			
			Canal canal = parser.toCanal(classificacaoDao);
			mensagem = canalDao.atualizar(canal);
			Logs.info("[ConsoleController]::atualizarCanal: Canal atualizado com exito: "+canal.toString());
			
			return "redirect:canais";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::atualizarCanal: Erro tentanto atualizar canal. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Carrega a logo do canal na response (utilizado no atributo src de uma tag img)
	 */
	@RequestMapping("carregarLogoDoCanal")
	public void carregarLogoDoCanal(long id, HttpServletResponse response) throws Exception
	{
		byte[] logo = canalDao.pegarLogoDoCanal(id);
		
		if(logo != null)
		{
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(logo);
			response.getOutputStream().close();
		}
	}
	
	/**
	 * Método que devolve o canal para determinada tag de video do front.
	 */
	@RequestMapping("pegarCanal")
	public void pegarCanal(long id, HttpServletResponse response){
		try{
			Canal canal = canalDao.pegarPorId(id);
			String url = canal.getUrl();
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.getOutputStream().write(url.getBytes());
			response.getOutputStream().close();
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::pegarCanal: Erro ao tentar pegar url do canal. Exception:");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("testarCanal")
	public String testarCanal(long id, Model model){
		try{
			Canal canal = canalDao.pegarPorId(id);
			model.addAttribute("canal",canal);
			return "canais/teste";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
}