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
	private String nome;
	private MultipartFile imagem;
	private String url;
	private long classificacao_id;
	private String observacoes;
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
		return "CanalParser [nome=" + nome + ", imagem(tamanho)=" + imagem.getName() + "(" + imagem.getSize() + ")" + ", url=" + url + ", classificacao_id="
				+ classificacao_id + ", observacoes=" + observacoes + "]";
	}
	public Canal toCanal()throws Exception {
		Canal canal = new Canal();
		canal.setId(0);
		canal.setClassificacao(null);
		canal.setFuncionando(true);
		if(imagem != null && imagem.getSize() > 1)
			canal.setLogo(imagem.getBytes());
		else
			canal.setDefaultLogo();
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
