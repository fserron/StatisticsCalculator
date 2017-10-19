package otaking.com.statisticscalculator.backend.dao;

import java.util.List;

public interface DadoDAO {

	List<Integer> hacerTiradaSimple(Integer cantidad);
	
	List<Integer> hacerTiradaVariable(Integer caras, Integer cantidad);
	
}
