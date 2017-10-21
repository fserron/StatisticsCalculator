package otaking.com.statisticscalculator.backend.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import otaking.com.statisticscalculator.backend.bo.TiradaBO;
import otaking.com.statisticscalculator.backend.dao.DadoDAO;
import otaking.com.statisticscalculator.backend.dao.impl.DadoDAOImpl;
import otaking.com.statisticscalculator.entities.dto.DadoDTO;
import otaking.com.statisticscalculator.entities.dto.TiradaDTO;
import otaking.com.statisticscalculator.entities.entities.Tirada;

public class TiradaBOImpl implements TiradaBO {
	
	private DadoDAO DadoDAO;
	private static int caraInvalida = 6;
	
	public TiradaBOImpl(){
		DadoDAO = new DadoDAOImpl();
	}

	public Tirada hacerTirada(TiradaDTO tiradaDTO){
		Tirada tirada;
		boolean fatidica = tiradaDTO.isFatidica();
		boolean explosiva = tiradaDTO.isExplosiva();
		int totalDados = tiradaDTO.getDados();

		List<Integer> resultadoTirada;

		DadoDTO dadoDTO = new DadoDTO();
		dadoDTO.setCaras(tiradaDTO.getCaras());
		dadoDTO.setCantidad(totalDados);

		//Calculamos la cara invalida
		if (tiradaDTO.getAtributo() == 20) { //Si el atributo esta en 20, ninguna cara es invalida
			caraInvalida = 99; //Valor imposible
		} else {
			caraInvalida = tiradaDTO.getCaras(); //El numero maximo del dado
		}

		if (explosiva) {
			if (fatidica){
				//Si es una tirada fatidica y gasto suerte, ambos se anulan.
				dadoDTO.setCantidad(tiradaDTO.getSuerte());
				resultadoTirada = DadoDAO.hacerTirada(dadoDTO);
			} else {
				resultadoTirada = hacerTiradaRecursiva(tiradaDTO.getSuerte(), dadoDTO, 0);
			}
		} else {
			resultadoTirada = DadoDAO.hacerTirada(dadoDTO);
		}

		//Ordenamos los resultados
		Collections.sort(resultadoTirada);

		//Descartamos los dados sin valor
		resultadoTirada = this.descartar(tiradaDTO, resultadoTirada);

		//Calculamos el total
		Integer total = this.calcularTotal(resultadoTirada);

		tirada = new Tirada();
		tirada.setResultadoTirada(resultadoTirada);
		tirada.setTotal(total);

		return tirada;
	}

	/**
	 * Metodo que se encarga de los re-rolleos
	 *
	 * @param numRepeticion el numero que decidira si ese dado se re-rollea o no.
	 * @param dadoDTO la cantidad de dados que se van a re-rollear.
	 * @param contador la cantidad de re-rolleos totales que van.
	 * @return lista con los resultados acumulados hasta el momento.
	 */
	private List<Integer> hacerTiradaRecursiva(Integer numRepeticion, DadoDTO dadoDTO, Integer contador){

		//Hacemos la tirada
		List<Integer> resultadoTirada = DadoDAO.hacerTirada(dadoDTO);

		//Evaluamos los resultados
		int dadosRerollear = 0; //Dados que se van a volver a tirar

		for (Integer dado : resultadoTirada){
			if (dado != caraInvalida && dado <= numRepeticion) dadosRerollear++;
		}

		if (dadosRerollear > 0 && contador < numRepeticion) {
			contador++;
			dadoDTO.setCantidad(dadosRerollear);
			resultadoTirada.addAll(hacerTiradaRecursiva(numRepeticion, dadoDTO, contador));
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

	private Integer calcularTotal(List<Integer> resultadoTirada) {
		Integer totalDano = 0;

		for (Integer dano : resultadoTirada) {
			totalDano += dano;
		}

		return totalDano;
	}
}
