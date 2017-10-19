package otaking.com.statisticscalculator.backend.dao.impl;

import java.util.ArrayList;
import java.util.List;

import otaking.com.statisticscalculator.backend.dao.DadoDAO;

public class DadoDAOImpl implements DadoDAO {

	public List<Integer> hacerTiradaSimple(Integer cantidad){
		List<Integer> resultados = new ArrayList<>();
		
		for (int i = 0; i < cantidad; i++){
			resultados.add(tirarDadoSimple());
		}
		
		return resultados;
	}
	
	public List<Integer> hacerTiradaVariable(Integer caras, Integer cantidad){
		List<Integer> resultados = new ArrayList<>();
		
		for (int i = 0; i < cantidad; i++){
			resultados.add(tirarDadoVariable(caras));
		}
		
		return resultados;
	}

	private Integer tirarDadoSimple(){
		return (int) Math.ceil(6 * Math.random());
	}

	private Integer tirarDadoVariable(Integer caras){
		return (int) Math.ceil(caras * Math.random());
	}
	
}
