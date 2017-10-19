package otaking.com.statisticscalculator.entities.dto;

public class TiradaDTO {

	public TiradaDTO(){
		this.caras = 6;
		this.atributo = 1;
		this.suerte = 1;
		this.dados = 0;
		this.bono = 0;
		this.fatidica = false;
		this.explosiva = false;
	}

	//Cantidad de caras de dado
	private int caras;

	//Atributo del personaje que hace la tirada
	private int atributo;

	//Habilidad del personaje que hace la tirada
	private int dados;

	//Suerte del personaje que hace la tirada
	private int suerte;

	//Bonos totales
	private int bono;

	//Indicador de si es una tirada fatidica
	private boolean fatidica;

	//Indicador de si es una tirada explosiva
	private boolean explosiva;


	public int getCaras() { return caras; }

	public void setCaras(int caras) {
		this.caras = caras;
	}

	public int getAtributo() {
		return atributo;
	}

	public void setAtributo(int atributo) {
		this.atributo = atributo;
	}

	public int getDados() { return dados; }

	public void setDados(int dados) {
		this.dados = dados;
	}

	public int getSuerte() { return suerte; }

	public void setSuerte(int suerte) {
		this.suerte = suerte;
	}

	public int getBono() {
		return bono;
	}

	public void setBono(int bono) {
		this.bono = bono;
	}

	public boolean isFatidica() {
		return fatidica;
	}

	public void setFatidica(boolean fatidica) {
		this.fatidica = fatidica;
	}

	public boolean isExplosiva() {
		return explosiva;
	}

	public void setExplosiva(boolean lucky) {
		this.explosiva = lucky;
	}
}
