package otaking.com.statisticscalculator.backend.bo.impl;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import otaking.com.statisticscalculator.backend.bo.EstadisticaBO;
import otaking.com.statisticscalculator.backend.bo.TiradaBO;
import otaking.com.statisticscalculator.backend.dao.DadoDAO;
import otaking.com.statisticscalculator.backend.dao.impl.DadoDAOImpl;
import otaking.com.statisticscalculator.entities.dto.EstadisticaDTO;
import otaking.com.statisticscalculator.entities.dto.TiradaDTO;
import otaking.com.statisticscalculator.entities.entities.Tirada;

public class EstadisticaBOImpl implements EstadisticaBO {
	
	private DadoDAO dadoDAO;

	private TiradaBO tiradaBO;

	public EstadisticaBOImpl(){
		this.dadoDAO = new DadoDAOImpl();
		this.tiradaBO = new TiradaBOImpl();
	}

	public EstadisticaDTO estadisticaTirada(TiradaDTO dto, Integer muestra){
		EstadisticaDTO estadisticaDto = new EstadisticaDTO();

		Map<Integer, Integer> mapaResultados = new TreeMap<>();
		Integer maximo = Integer.MIN_VALUE;
		Integer minimo = Integer.MAX_VALUE;
		Integer total = 0;

		for (int prueba = 0; prueba < muestra; prueba++) {
			Tirada emboque = tiradaBO.hacerTirada(dto);

			Integer subtotal = emboque.getTotal();

			//Maxima cantidad de exitos
			if (subtotal > maximo) maximo = subtotal;

			//Minima cantidad de exitos
			if (subtotal < minimo) minimo = subtotal;

			//Total de daño, para calcular el promedio
			total += subtotal;

			//Voy creando un mapa con exitos / cantidad de veces
			Integer tempExitos = mapaResultados.get(subtotal);

			if (tempExitos != null)
				tempExitos++;
			else
				tempExitos = 1;

			mapaResultados.put(subtotal, tempExitos);
		}

		estadisticaDto.setMaximo(maximo);
		estadisticaDto.setMinimo(minimo);
		estadisticaDto.setPromedio(this.calcularPromedio(total, muestra));
		estadisticaDto.setMapaResultados(mapaResultados);
		//Para calcular la prob. necesitaria poner un objetivo...
		//estadisticaDto.setProbabilidadExito(this.calcularProbabilidad());

		return estadisticaDto;
	}

	private String calcularPromedio(Integer total, Integer muestra){
		String resultado;

		Double promedio = (double) total / muestra;

		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		resultado = format.format(promedio);

		return resultado;
	}

}
