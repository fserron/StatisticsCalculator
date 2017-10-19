package otaking.com.statisticscalculator.entities.entities;

import java.util.ArrayList;
import java.util.List;

public class Tirada {

	private int total;

	private List<Integer> resultadoTirada;
	
	public Tirada(){
		this.resultadoTirada = new ArrayList<>();
		this.total = 0;
	}
	
	public List<Integer> getResultadoTirada() {
		return resultadoTirada;
	}

	public void setResultadoTirada(List<Integer> resultadoTirada) {
		this.resultadoTirada = resultadoTirada;
	}

	public int getTotal() { return total; }

	public void setTotal(int total) {
		this.total = total;
	}

}
