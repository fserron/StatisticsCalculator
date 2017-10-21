package otaking.com.statisticscalculator.backend.dao;

import java.util.List;

import otaking.com.statisticscalculator.entities.dto.DadoDTO;

public interface DadoDAO {

	List<Integer> hacerTirada(DadoDTO dto);
	
	Integer tirarDado(Integer caras);
	
}
