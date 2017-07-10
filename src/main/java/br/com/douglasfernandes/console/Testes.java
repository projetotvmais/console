package br.com.douglasfernandes.console;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import br.com.douglasfernandes.console.model.Usuario;

public class Testes {

	public static void main(String[] args) {
		try{
			Usuario usuario = new Usuario();
			usuario.setId(1);
			usuario.setNome("Douglas Fernandes");
			usuario.setEmail("douglasf.filho@gmail.com");
			usuario.setIdentificacao("084.683.314-06");
			usuario.setTelefone("(81)9 9672-9491");
			usuario.setEndereco("Rua Argina Aguiar");
			usuario.setNumero("206A - Casa");
			usuario.setBairro("Tejipipó");
			usuario.setCidade("Recife");
			usuario.setEstado("PE");
			usuario.setCep("50.920-600");
			usuario.setObservacoes("Otimo cliente");
			usuario.setAtivo(true);
			
			if(usuario != null){
				XStream xstream = new XStream(new JettisonMappedXmlDriver());
		        xstream.setMode(XStream.NO_REFERENCES);
		        xstream.processAnnotations(Usuario.class);
		        String jsonResponse = xstream.toXML(usuario);
		        
		        System.out.println(jsonResponse);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
