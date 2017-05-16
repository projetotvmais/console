package br.com.douglasfernandes.console.controller.parsers;

import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;
import br.com.douglasfernandes.console.dao.CanalDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Token;

public class TokenParser {
	private long canal;
	private String validade;
	
	
	public long getCanal() {
		return canal;
	}
	public void setCanal(long canal) {
		this.canal = canal;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	private static String getStringAsHex(String string){
		char[] chars = string.toCharArray();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
                    hex.append(Integer.toHexString((int)chars[i]));
        }
        return hex.toString();
	}
	
	@Override
	public String toString() {
		return "canal=" + canal + ", validade=" + validade + "]";
	}
	
	public Token toToken(CanalDao canalDao){
		try{
			String frase = ""+canal+""+validade;
			String hex = getStringAsHex(frase);
			Token token = new Token();
			token.setNome(hex);
			token.setCanal(canalDao.pegarPorId(canal));
			token.setValidade(FMT.getCalendarFromString(validade, DateFormat.DMYTHM));
			Logs.info("[TokenParser]:toToken:Token gerado: "+token.toString());
			return token;
		}
		catch(Exception e){
			Logs.info("[TokenParser]:toToken:Erro ao gerar token. Exception: ");
			e.printStackTrace();
			return null;
		}
	}
}
