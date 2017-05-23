package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.controller.utils.Periodo;
import br.com.douglasfernandes.console.controller.utils.TriState;
import br.com.douglasfernandes.console.model.Fatura;
import br.com.douglasfernandes.console.model.Pacote;

/**
 * Interface com banco de dados para operar sobre faturas.
 * @author douglas.f.filho
 *
 */
public interface FaturaDao {
	public boolean registrar(Fatura fatura);
	public Fatura pegarPorCodigo(String codigo);
	public List<Fatura> listar(String codigo, Periodo periodoDeVencimento, TriState pago, String pacote);
	public String pagar(Fatura fatura);
	public String mudarPacote(Pacote novoPacote);
	public String mudarVencimento(Fatura fatura);
	public String cancelarFatura(Fatura fatura);
}
