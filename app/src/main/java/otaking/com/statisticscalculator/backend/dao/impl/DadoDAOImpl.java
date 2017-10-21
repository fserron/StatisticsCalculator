package otaking.com.statisticscalculator.backend.dao.impl;

import java.util.ArrayList;
import java.util.List;

import otaking.com.statisticscalculator.backend.dao.DadoDAO;
import otaking.com.statisticscalculator.entities.dto.DadoDTO;

public class DadoDAOImpl implements DadoDAO {

	public List<Integer> hacerTirada(DadoDTO dto){
		List<Integer> resultados = new ArrayList<>();
		
		for (int i = 0; i < dto.getCantidad(); i++){
			resultados.add(tirarDado(dto.getCaras()));
		}
		
		return resultados;
	}

	public Integer tirarDado(Integer caras){
		return (int) Math.ceil(caras * Math.random());
	}
	
}
