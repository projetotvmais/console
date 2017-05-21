package br.com.douglasfernandes.console.controller.parsers;

import org.springframework.web.multipart.MultipartFile;

import br.com.douglasfernandes.console.model.Pacote;

/**
 * Ajuda na obtencao de valores da request para utilizar na aplicacao.
 * @author douglas.f.filho
 *
 */
public class PacoteParser {
	private long id = 0;
	private String nome;
	private MultipartFile logo = null;
	private String valor;
	private String canais;
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
	public MultipartFile getLogo() {
		return logo;
	}
	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getCanais() {
		return canais;
	}
	public void setCanais(String canais) {
		this.canais = canais;
	}
	@Override
	public String toString() {
		String len = "0";
		if(logo != null)
			len = ""+logo.getSize();
		
		return "PacoteParser [id=" + id + ", nome=" + nome + ", logo=" + len + "bytes, valor=" + valor + ", canais="
				+ canais + "]";
	}
	
	public Pacote toPacote(){
		Pacote pacote = new Pacote();
		pacote.setId(id);
		pacote.setNome(nome);
		pacote.setValor(Double.parseDouble(valor));
		pacote.setCanais(canais);
		try{
			if(logo == null || logo.getSize() < 10){
				pacote.setDefaultLogo();
			}
			else{
				pacote.setLogo(logo.getBytes());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pacote;
	}
}
