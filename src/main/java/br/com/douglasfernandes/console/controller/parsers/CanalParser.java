package br.com.douglasfernandes.console.controller.parsers;

import org.springframework.web.multipart.MultipartFile;

import br.com.douglasfernandes.console.dao.ClassificacaoDao;
import br.com.douglasfernandes.console.model.Canal;
import br.com.douglasfernandes.console.model.Classificacao;

/**
 * Auxilia como parser entre view e controller
 * @author douglas.f.filho
 *
 */
public class CanalParser {
	private long id = 0;
	private String nome;
	private MultipartFile imagem;
	private String funcionando = "true";
	private String url;
	private long classificacao_id;
	private String observacoes;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public MultipartFile getImagem() {
		return imagem;
	}
	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}
	public String getFuncionando() {
		return funcionando;
	}
	public void setFuncionando(String funcionando) {
		this.funcionando = funcionando;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getClassificacao_id() {
		return classificacao_id;
	}
	public void setClassificacao_id(long classificacao_id) {
		this.classificacao_id = classificacao_id;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	@Override
	public String toString() {
		return "CanalParser [id=" + id + ",nome=" + nome + ", imagem(tamanho)=" + imagem.getName() + "(" + imagem.getSize() + ")" + ", funcionando=" + funcionando + ", url=" + url + ", classificacao_id="
				+ classificacao_id + ", observacoes=" + observacoes + "]";
	}
	public Canal toCanal()throws Exception {
		Canal canal = new Canal();
		canal.setId(id);
		canal.setClassificacao(null);
		if(funcionando != null && !funcionando.equals("") && funcionando.equals("true"))
			canal.setFuncionando(true);
		else
			canal.setFuncionando(false);
		if(imagem != null && imagem.getSize() > 1)
			canal.setLogo(imagem.getBytes());
		canal.setNome(nome);
		canal.setObservacoes(observacoes);
		canal.setUrl(url);
		
		return canal;
	}
	public Canal toCanal(ClassificacaoDao dao)throws Exception {
		Canal canal = toCanal();
		Classificacao classificacao = dao.pegarClassificacao(classificacao_id);
		canal.setClassificacao(classificacao);
		
		return canal;
	}
}
