package otaking.com.statisticscalculator.backend.bo;

import otaking.com.statisticscalculator.entities.dto.TiradaDTO;
import otaking.com.statisticscalculator.entities.entities.Tirada;

public interface TiradaBO {

	Tirada hacerTirada(TiradaDTO dto);
	
}
