package otaking.com.statisticscalculator.backend.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import otaking.com.statisticscalculator.backend.bo.TiradaBO;
import otaking.com.statisticscalculator.backend.dao.DadoDAO;
import otaking.com.statisticscalculator.backend.dao.impl.DadoDAOImpl;
import otaking.com.statisticscalculator.entities.dto.TiradaDTO;
import otaking.com.statisticscalculator.entities.entities.Tirada;

public class TiradaBOImpl implements TiradaBO {
	
	private DadoDAO DadoDAO;
	
	public TiradaBOImpl(){
		DadoDAO = new DadoDAOImpl();
	}

	public Tirada hacerTirada(TiradaDTO dto){
		Tirada tirada;
		boolean fatidica = dto.isFatidica();
		boolean explosiva = dto.isExplosiva();
		int totalDados = dto.getDados();

		List<Integer> resultadoTirada;

		if (explosiva) {
			if (fatidica){
				//Si es una tirada fatidica y gasto suerte, ambos se anulan.
				resultadoTirada = DadoDAO.hacerTiradaSimple(dto.getSuerte());
			} else {
				resultadoTirada = hacerTiradaRecursiva(dto.getSuerte(), totalDados, 0);
			}
		} else {
			resultadoTirada = DadoDAO.hacerTiradaSimple(totalDados);
		}

		//Ordenamos los resultados
		Collections.sort(resultadoTirada);

		//Descartamos los dados sin valor
		resultadoTirada = this.descartar(dto, resultadoTirada);

		//Calculamos el daño
		Integer dano = this.calcularDano(resultadoTirada);

		tirada = new Tirada();
		tirada.setResultadoTirada(resultadoTirada);
		tirada.setTotal(dano);

		return tirada;
	}

	/**
	 * Metodo que se encarga de los re-rolleos
	 *
	 * @param numRepeticion el numero que decidira si ese dado se re-rollea o no.
	 * @param cantidad la cantidad de dados que se van a re-rollear.
	 * @param contador la cantidad de re-rolleos totales que van.
	 * @return lista con los resultados acumulados hasta el momento.
	 */
	private List<Integer> hacerTiradaRecursiva(Integer numRepeticion, Integer cantidad, Integer contador){

		//Hacemos la tirada
		List<Integer> resultadoTirada = DadoDAO.hacerTiradaSimple(cantidad);

		//Evaluamos los resultados
		int dadosRerollear = 0; //Dados que se van a volver a tirar

		for (Integer dado : resultadoTirada){
			if (dado != 6 && dado <= numRepeticion) dadosRerollear++;
		}

		if (dadosRerollear > 0 && contador < numRepeticion) {
			contador++;
			resultadoTirada.addAll(hacerTiradaRecursiva(numRepeticion, dadosRerollear, contador));
		}

		return resultadoTirada;
	}

	private List<Integer> descartar(TiradaDTO dto, List<Integer> resultadoTirada) {
		List<Integer> listaFiltrada = new ArrayList<>();

		for (int i = 0; i < resultadoTirada.size(); i++){
			if (resultadoTirada.get(i) <= dto.getAtributo()){
				listaFiltrada.add(resultadoTirada.get(i));
			}
		}

		return listaFiltrada;
	}

	private Integer calcularDano(List<Integer> resultadoTirada) {
		Integer totalDano = 0;

		for (Integer dano : resultadoTirada) {
			totalDano += dano;
		}

		return totalDano;
	}
}
