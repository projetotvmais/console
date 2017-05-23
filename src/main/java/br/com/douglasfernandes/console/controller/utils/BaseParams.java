package br.com.douglasfernandes.console.controller.utils;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Auxilia a passar parametros em String de consulta a banco de dados.
 * @author douglas.f.filho
 *
 */
public class BaseParams {
	private ArrayList<Param> params;
	
	private BaseParams(){
		this.params = new ArrayList<Param>();
	}
	
	public static BaseParams getInstance(){
		return new BaseParams();
	}
	
	public void addParam(Param param){
		this.params.add(param);
	}
	
	@SuppressWarnings("rawtypes")
	public Query getQuery(Class model, EntityManager manager){
		String entity = model.getName();
		String alias = model.getName().substring(0,3).toLowerCase();
		
		Query q = null;
		
		String criteria = "select "+alias+" from "+entity+" as "+alias+" where 1 = 1";
		if(this.params != null && this.params.size() > 0){
			Param param = null;
			for(int i = 0;i < this.params.size();i++){
				param = this.params.get(i);
				criteria += " and "+param.getNome()+param.getModo()+":valor"+i;
			}
			q = manager.createQuery(criteria);
			for(int i = 0;i < this.params.size();i++){
				param = this.params.get(i);
				q.setParameter("valor"+i, param.getValor());
			}
		}
		else{
			q = manager.createQuery(criteria);
		}
		
		return q;
	}
}
