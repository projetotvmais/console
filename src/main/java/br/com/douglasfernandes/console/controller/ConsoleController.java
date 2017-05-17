package br.com.douglasfernandes.console.controller;

import java.util.Calendar;
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
import br.com.douglasfernandes.console.controller.parsers.TokenParser;
import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;
import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.dao.ClassificacaoDao;
import br.com.douglasfernandes.console.dao.PerfilDao;
import br.com.douglasfernandes.console.dao.TokenDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Canal;
import br.com.douglasfernandes.console.model.Classificacao;
import br.com.douglasfernandes.console.model.Perfil;
import br.com.douglasfernandes.console.model.Token;

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
	
	@Qualifier("tokenJpa")
	@Autowired
	private TokenDao tokenDao;
	
	String mensagem = "";
	
//	XXX Gerenciamento de entrada, login, logout e perfil.
	
	@RequestMapping(value = {"home","/"})
	public String home(Model model){
		try{
			List<Token> tokens = tokenDao.listar();
			model.addAttribute("tokens", tokens);
			
			List<Canal> canais = canalDao.listarPorNome("");
			model.addAttribute("listaCanais", canais);
			
			Perfil perfil = perfilDao.lerPerfil();
			model.addAttribute("perfil",perfil);
			
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
	
	@RequestMapping("atualizarPerfil")
	public String atualizarPerfil(Perfil perfil){
		try{
			mensagem = perfilDao.atualizar(perfil);
			return "redirect:home";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
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
			Logs.info("[ConsoleController]::canais: "+classificacoes.size()+" classificacoes encontradas.");
			
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
	
	/**
	 * Usado para testar um canal especifico na tela de testes
	 * @param id
	 * @param model
	 * @return
	 */
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
	
	/**
	 * Usado para testar um canal especifico na tela de testes
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("testeDeCanal")
	public String testeDeCanal(long id, String token, Model model){
		try{
			Token tokenClient = tokenDao.validar(token);
			if(token != null){
				Calendar agora = FMT.getAgora();
				String timezone = agora.getTimeZone().getID();
				if(timezone.equals("Etc/UTC"))
					agora.add(Calendar.HOUR_OF_DAY, -3);
				Logs.info("[ConsoleController]::testeDeCanal::TimeZone: "+timezone);
				Calendar validade = tokenClient.getValidade();
				Logs.info("[ConsoleController]::testeDeCanal:: Agora:"+FMT.getStringFromCalendar(agora, DateFormat.DMYHM)+" | Validade:"+FMT.getStringFromCalendar(validade, DateFormat.DMYHM));
				
				if(agora.after(validade)){
					mensagem = Mensagem.getWarning("token inválido.");
					tokenDao.remover(tokenClient.getId());
					return "redirect:login";
				}
				else{
					Canal canal = canalDao.pegarPorId(id);
					model.addAttribute("canal",canal);
					return "canais/teste_cliente";
				}
			}
			else{
				mensagem = Mensagem.getWarning("token inválido.");
				return "redirect:login";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Devolve a tela de gerenciamento de canais.
	 * @return
	 */
	@RequestMapping("pesquisarCanal")
	public String canais(String pesquisa, Model model){
		try{
			if(pesquisa == null || pesquisa.equals(""))
				return "redirect:canais";
			
			List<Classificacao> classificacoes = classificacaoDao.listar();
			model.addAttribute("classificacoes",classificacoes);
			Logs.info("[ConsoleController]::canais: "+classificacoes.size()+" classificacoes encontradas");
			
			List<Canal> canais = canalDao.listarPorNome(pesquisa);
			
			model.addAttribute("pesquisa", pesquisa);
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
	
	@RequestMapping("cadastrarToken")
	public String cadastrarToken(TokenParser tokenParser){
		try{
			Logs.info("[ConsoleController]::cadastrarToken:tokenParser: "+tokenParser.toString());
			Token token = tokenParser.toToken(canalDao);
			mensagem = tokenDao.cadastrar(token);
			return "redirect:home";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::cadastrarToken: Erro tentando cadastrar token. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("removerToken")
	public String cadastrarToken(long id){
		try{
			Logs.info("[ConsoleController]::removerToken:tokenId: "+id);
			mensagem = tokenDao.remover(id);
			return "redirect:home";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::removerToken: Erro tentando remover token. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
}