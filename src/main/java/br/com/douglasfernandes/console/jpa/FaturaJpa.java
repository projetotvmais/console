package br.com.douglasfernandes.console.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.console.controller.utils.BaseParams;
import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.Param;
import br.com.douglasfernandes.console.controller.utils.Param.Modo;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;
import br.com.douglasfernandes.console.controller.utils.Periodo;
import br.com.douglasfernandes.console.controller.utils.Periodo.Intervalo;
import br.com.douglasfernandes.console.controller.utils.TriState;
import br.com.douglasfernandes.console.dao.FaturaDao;
import br.com.douglasfernandes.console.logger.Logs;
import br.com.douglasfernandes.console.model.Fatura;
import br.com.douglasfernandes.console.model.Pacote;

/**
 * Implementação do acesso a faturas de usuários.
 * @author douglas.f.filho
 *
 */
@Repository
public class FaturaJpa implements FaturaDao{

	@PersistenceContext
	EntityManager manager;

	@Override
	public boolean registrar(Fatura fatura) {
		try{
			String codigo = FMT.getStringFromCalendar(FMT.getAgora(), DateFormat.YMDHMSJ);
			codigo += FMT.getStringFromCalendar(fatura.getVencimento(), DateFormat.YMDHMSJ);
			fatura.setCodigo(codigo);
			
			fatura.setPago(false);
			
			manager.persist(fatura);
			Logs.info("[FaturaJpa]::registrar::Fatura registrada: "+fatura.toString());
			return true;
		}
		catch(Exception e){
			Logs.warn("[FaturaJpa]::registrar::Erro tentando registrar fatura. Exception: ");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Fatura pegarPorCodigo(String codigo) {
		try{
			Query q = manager.createQuery("select f from Fatura as f where f.codigo = :codigo");
			q.setParameter("codigo", codigo);
			Fatura fatura = (Fatura)q.getSingleResult();
			Logs.info("[FaturaJpa]::pegarPorCodigo::Fatura recuperada por codigo: "+fatura.toString());
			return fatura;
		}
		catch(Exception e){
			Logs.warn("[FaturaJpa]::pegarPorCodigo::Erro tentando pegar fatura por codigo. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Fatura> listar(String codigo, Periodo periodoDeVencimento, TriState pago, String pacote) {
		try{
			BaseParams params = BaseParams.getInstance();
			
			if(codigo != null && !codigo.equals("")){
				Param codparam = Param.getInstance();
				codparam.setNome("codigo");
				codparam.setModo(Modo.LIKE);
				codparam.setValor("%"+codigo+"%");
				params.addParam(codparam);
			}
			
			if(periodoDeVencimento != null){
				if(periodoDeVencimento.getIntervalo() == Intervalo.A_PARTIR_DE){
					Param dataparam = Param.getInstance();
					dataparam.setModo(Modo.MAIOR_IGUAL);
					dataparam.setNome("vencimento");
					dataparam.setValor(periodoDeVencimento.getDataInicial());
					params.addParam(dataparam);
				}
				else if(periodoDeVencimento.getIntervalo() == Intervalo.ATE){
					Param dataparam = Param.getInstance();
					dataparam.setModo(Modo.MENOR_IGUAL);
					dataparam.setNome("vencimento");
					dataparam.setValor(periodoDeVencimento.getDataFinal());
					params.addParam(dataparam);
				}
				else{
					Param dataparam1 = Param.getInstance();
					dataparam1.setModo(Modo.MAIOR_IGUAL);
					dataparam1.setNome("vencimento");
					dataparam1.setValor(periodoDeVencimento.getDataInicial());
					params.addParam(dataparam1);
					Param dataparam2 = Param.getInstance();
					dataparam2.setModo(Modo.MENOR_IGUAL);
					dataparam2.setNome("vencimento");
					dataparam2.setValor(periodoDeVencimento.getDataFinal());
					params.addParam(dataparam2);
				}
			}
			
			if(pago.getEstado() < 2){
				Param pagoparam = Param.getInstance();
				pagoparam.setNome("pago");
				pagoparam.setModo(Modo.IGUAL);
				pagoparam.setValor(pago.getEstadoAsBoolean());
				params.addParam(pagoparam);
			}
			
			if(pacote != null && !pacote.equals("")){
				Param pacoteparam = Param.getInstance();
				pacoteparam.setNome("pacote.nome");
				pacoteparam.setModo(Modo.LIKE);
				pacoteparam.setValor("%"+pacote+"%");
				params.addParam(pacoteparam);
			}
			
			Query q = params.getQuery(Fatura.class, manager);
			
			@SuppressWarnings("unchecked")
			List<Fatura> faturas = q.getResultList();
			if(faturas != null && faturas.size() > 0){
				Logs.info("[FaturaJpa]::listar::"+faturas.size()+" faturas recuperadas:");
				for(Fatura f : faturas){
					Logs.info("[FaturaJpa]::listar::-------> "+f.toString());
				}
				
				return faturas;
			}
			else{
				Logs.info("[FaturaJpa]::listar::Nao ha faturas cadastradas.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[FaturaJpa]::listar::Erro tentando listar faturas. Exception: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String pagar(Fatura fatura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mudarPacote(Pacote novoPacote) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mudarVencimento(Fatura fatura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelarFatura(Fatura fatura) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
