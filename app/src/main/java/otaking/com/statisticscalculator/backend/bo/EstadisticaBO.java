package otaking.com.statisticscalculator.backend.bo;

import otaking.com.statisticscalculator.entities.dto.EstadisticaDTO;
import otaking.com.statisticscalculator.entities.dto.TiradaDTO;

public interface EstadisticaBO {

	EstadisticaDTO estadisticaTirada(TiradaDTO dto, Integer muestra);
	
}
