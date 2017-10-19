package otaking.com.statisticscalculator.entities.dto;

import java.util.List;
import java.util.Map;

import otaking.com.statisticscalculator.entities.entities.Tirada;


public class EstadisticaDTO {

	private Map<Integer, Integer> exitosTirada;

	private Map<Integer, Integer> mapaResultados;

	private List<Integer> tiradas;
	
	private Integer maximo;
	
	private Integer minimo;
	
	private String probabilidadExito;

	private String promedio;

	public List<Integer> getTiradas() {
		return tiradas;
	}

	public void setTiradas(List<Integer> tiradas) {
		this.tiradas = tiradas;
	}

	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	public Map<Integer, Integer> getMapaResultados() {
		return mapaResultados;
	}

	public void setMapaResultados(Map<Integer, Integer> mapaResultados) {
		this.mapaResultados = mapaResultados;
	}

	public String getProbabilidadExito() {
		return probabilidadExito;
	}

	public void setProbabilidadExito(String probabilidadExito) {
		this.probabilidadExito = probabilidadExito;
	}

	public String getPromedio() { return promedio; }

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
}
