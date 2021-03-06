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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import br.com.douglasfernandes.console.controller.parsers.CanalParser;
import br.com.douglasfernandes.console.controller.parsers.PacoteParser;
import br.com.douglasfernandes.console.controller.parsers.TokenParser;
import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;
import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.dao.ClassificacaoDao;
import br.com.douglasfernandes.console.dao.PacoteDao;
import br.com.douglasfernandes.console.dao.PerfilDao;
import br.com.douglasfernandes.console.dao.TokenDao;
import br.com.douglasfernandes.console.dao.UsuarioDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Canal;
import br.com.douglasfernandes.console.model.Classificacao;
import br.com.douglasfernandes.console.model.Pacote;
import br.com.douglasfernandes.console.model.Perfil;
import br.com.douglasfernandes.console.model.Token;
import br.com.douglasfernandes.console.model.Usuario;

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
	
	@Qualifier("pacoteJpa")
	@Autowired
	private PacoteDao pacoteDao;
	
	@Qualifier("usuarioJpa")
	@Autowired
	private UsuarioDao usuarioDao;
	
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
			
			cadastrarUsuarioTeste();
			
			return "index";
		}
		catch(Exception e){
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	private void cadastrarUsuarioTeste(){
		try{
			Usuario usuario = new Usuario();
			usuario.setDefaultFoto();
			usuario.setNome("Douglas Fernandes");
			usuario.setSenha("$senha=password!dificil");
			usuario.setEmail("douglasf.filho@gmail.com");
			usuario.setIdentificacao("084.683.314-06");
			usuario.setTelefone("(81)9 9672-9491");
			usuario.setEndereco("Rua Argina Aguiar");
			usuario.setNumero("206A - Casa");
			usuario.setBairro("Tejipip�");
			usuario.setCidade("Recife");
			usuario.setEstado("PE");
			usuario.setCep("50.920-600");
			usuario.setObservacoes("Otimo cliente");
			usuario.setAtivo(true);
			
			usuarioDao.cadastrar(usuario);
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::cadastrarUsuarioTeste::Erro tentando cadastrar novo usuario.Exception:\n");
			e.printStackTrace();
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
			mensagem = Mensagem.getInfo("Voc� saiu do sistema.");
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
	 * M�todo que devolve o canal para determinada tag de video do front.
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
					mensagem = Mensagem.getWarning("token inv�lido.");
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
				mensagem = Mensagem.getWarning("token inv�lido.");
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
	
//	XXX Gerenciar tokens de acesso a canal para demonstra��o
	
	/**
	 * Cadastrar um token para demo
	 * @param tokenParser
	 * @return
	 */
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
	
	/**
	 * Remover um token criado
	 * @param id
	 * @return
	 */
	@RequestMapping("removerToken")
	public String removerToken(long id){
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
	
//	XXX Mostrar tela de gerenciamento de pacotes.
	
	/**
	 * Abrir tela de gerenciamento de pacotes
	 * @param pesquisa
	 * @param model
	 * @return
	 */
	@RequestMapping("pacotes")
	public String pacotes(String pesquisa, Model model){
		try{
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			
			if(pesquisa == null)
				pesquisa = "";
			model.addAttribute("pesquisa",pesquisa);
			
			List<Pacote> pacotes = pacoteDao.listar(pesquisa);
			model.addAttribute("pacotes",pacotes);
			
			List<Canal> canais = canalDao.listarPorNome("");
			model.addAttribute("canais", canais);
			
			return "pacotes/pacotes";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::pacotes: Erro tentando listar pacotes. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("cadastrarPacote")
	public String cadastrarPacote(PacoteParser parser, HttpServletRequest request){
		try{
			Logs.info("[ConsoleController]::cadastrarPacote: Request: "+ parser.toString());
			
			Pacote pacote = parser.toPacote();
			mensagem = pacoteDao.cadastrar(pacote);
			
			return "redirect:pacotes";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::cadastrarPacote: Erro tentanto cadastrar pacote. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	@RequestMapping("atualizarPacote")
	public String atualizarPacote(PacoteParser parser, HttpServletRequest request){
		try{
			Logs.info("[ConsoleController]::atualizarPacote: Request: "+ parser.toString());
			
			Pacote cadastrado = pacoteDao.pegarPacotePorId(parser.getId());
			Pacote pacote = parser.toPacote();
			
			if(parser.getLogo() == null || parser.getLogo().getSize() < 10)
				pacote.setLogo(cadastrado.getLogo());
			
			mensagem = pacoteDao.atualizar(pacote);
			
			return "redirect:pacotes";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::atualizarPacote: Erro tentanto atualizar pacote. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Remover um pacote que est� fora de uso.
	 * @param id
	 * @return
	 */
	@RequestMapping("removerPacote")
	public String pacotes(long id){
		try{
			//TODO Verificar se h� algum cliente usando este pacote antes de tentar remove-lo
			mensagem = pacoteDao.remover(id);
			return "redirect:pacotes";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::removerPacote: Erro tentando remover pacote. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Carrega a logo do pacote na response (utilizado no atributo src de uma tag img)
	 */
	@RequestMapping("mostrarImagemLogoDoPacote")
	public void mostrarImagemLogoDoPacote(long id, HttpServletResponse response) throws Exception
	{
		byte[] logo = pacoteDao.pegarLogoDoPacote(id);
		
		if(logo != null)
		{
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(logo);
			response.getOutputStream().close();
		}
	}
	
//	XXX Gerenciar usu�rios do portal do sistema.
	@RequestMapping("clientes")
	public String usuarios(String pesquisa, Model model){
		try{
			model.addAttribute("mensagem",mensagem);
			mensagem = "";
			
			if(pesquisa == null)
				pesquisa = "";
			model.addAttribute("pesquisa",pesquisa);
			
			List<Usuario> usuarios = usuarioDao.listar("");
			model.addAttribute("clientes", usuarios);
			
			List<Pacote> pacotes = pacoteDao.listar(pesquisa);
			model.addAttribute("pacotes",pacotes);
			
			return "clientes/clientes";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::usuarios: Erro tentando listar usuarios. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * Carrega a foto do cliente na response (utilizado no atributo src de uma tag img)
	 */
	@RequestMapping("mostrarFotoDoCliente")
	public void mostrarFotoDoCliente(long id, HttpServletResponse response) throws Exception
	{
		byte[] foto = usuarioDao.pegarFotoDeUsuario(id);
		
		if(foto != null)
		{
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(foto);
			response.getOutputStream().close();
		}
	}
	
	@RequestMapping("pegarClientePorId")
	public void pegarClientePorId(long id, HttpServletResponse response){
		try{
			Usuario usuario = usuarioDao.pegarPorId(id);
			
			if(usuario != null){
				XStream xstream = new XStream(new JettisonMappedXmlDriver());
		        xstream.setMode(XStream.NO_REFERENCES);
		        xstream.processAnnotations(Usuario.class);
		        String jsonResponse = xstream.toXML(usuario);
		        
		        response.setContentType("application/json");
		        response.getWriter().append(jsonResponse);
		        response.getWriter().close();
			}
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::pegarClientePorId: Erro tentando pegar informacoes de usuario. Exception: ");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("mudarStatusDeCliente")
	public void mudarStatusDeCliente(long id, HttpServletResponse response){
		try{
			Usuario usuario = usuarioDao.pegarPorId(id);
			usuario.setAtivo(!usuario.getAtivo());
			String msg = usuarioDao.atualizar(usuario);
			String resposta = Mensagem.getOriginalMessage(msg);
			
			String json = "{\"mensagem\":\"" + resposta + "\"}";
			
			response.setContentType("application/json");
			response.getWriter().append(json);
			response.getWriter().close();
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::mudarStatusDeCliente: Erro tentando mudar status de usuario. Exception: ");
			e.printStackTrace();
		}
	}
	
}