package br.com.douglasfernandes.console.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import br.com.douglasfernandes.console.controller.utils.Mensagem;
import br.com.douglasfernandes.console.dao.CanalDao;
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
			mensagem = Mensagem.getInfo("Voc� saiu do sistema.");
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
			List<Classificacao> classificacoes = mockDeClassificacoes();
			model.addAttribute("classificacoes",classificacoes);
			Logs.info("[ConsoleController]::canais: lista de classificacoes: "+classificacoes.toString());
			
			List<Canal> canais = mockDeCanais();
			model.addAttribute("canais", canais);
			Logs.info("[ConsoleController]::canais: lista de canais: "+classificacoes.toString());
			
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
	@RequestMapping(value = "cadastrarCanal", method = RequestMethod.POST)
	public String cadastrarCanal(Canal canal, HttpSession session, HttpServletRequest request){
		try{
			canal.setDefaultLogo();
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("imagem");
			
			byte[] conteudo = multipartFile.getBytes();
			
			if(multipartFile != null && conteudo != null && conteudo.length > 1){
				byte[] logo = multipartFile.getBytes();
				canal.setLogo(logo);
			}
			mensagem = canalDao.cadastrar(canal);
			return "redirect:canais";
		}
		catch(Exception e){
			Logs.warn("[ConsoleController]::cadastrarCanal: Erro tentanto cadastrar canal. Exception: ");
			e.printStackTrace();
			return "erro/banco";
		}
	}
	
	/**
	 * XXX Mock de canais
	 * @return
	 */
	private List<Canal> mockDeCanais(){
		ArrayList<Canal> canais = new ArrayList<Canal>();
		
		try{
			Canal canal = null;
			Classificacao classificacao = new Classificacao();
			classificacao.setId(1L);
			classificacao.setNome("Esportes");
			
			for(int i = 1;i <= 10;i++){
				canal = new Canal();
				canal.setId(i);
				canal.setClassificacao(classificacao);
				canal.setDefaultLogo();
				canal.setFuncionando(true);
				canal.setNome("Canal Teste "+i);
				canal.setUrl("http://www.youtube.com/canal?id="+i);
				canal.setObservacoes("Este � um canal de testes de funcionalidade de p�gina. CANAL = "+i);
				
				canais.add(canal);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return canais;
	}
	
	/**
	 * XXX Mock de classifica��es
	 * @return
	 */
	private List<Classificacao> mockDeClassificacoes(){
		ArrayList<Classificacao> classificacoes = new ArrayList<Classificacao>();
		
		Classificacao classificacao1 = new Classificacao();
		classificacao1.setId(1L);
		classificacao1.setNome("Esportes");
		
		Classificacao classificacao2 = new Classificacao();
		classificacao2.setId(2L);
		classificacao2.setNome("Erotico");
		
		classificacoes.add(classificacao1);
		classificacoes.add(classificacao2);
		
		return classificacoes;
	}
	
	/**
	 * Carrega a logo do canal na response (utilizado no atributo src de uma tag img
	 */
	@RequestMapping("carregarLogoDoCanal")
	public void carregarLogoDoCanal(long id, HttpServletResponse response) throws Exception
	{
//TODO		Canal canal = canaisDao.getLogoDoCanal(id);
		Canal canal = new Canal();
		canal.setId(id);
		canal.setDefaultLogo();
		byte[] foto = canal.getLogo();
		
		if(foto != null)
		{
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(foto);
			response.getOutputStream().close();
		}
	}
	
	/**
	 * M�todo que devolve o canal para determinada tag de video do front.
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
