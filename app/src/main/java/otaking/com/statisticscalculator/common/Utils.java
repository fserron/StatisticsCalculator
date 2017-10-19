package otaking.com.statisticscalculator.common;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
	
	public static final String TAB = "\t";
	public static final String LINE_BREAK = "\n";
	
	// El formateador de numeros al formato Argentino.
	private static NumberFormat formatter = NumberFormat.getInstance(new Locale("es", "AR"));
	
	/**
	 * Funcion que evalua y convierte un numero a formato espaniol.
	 * @param number el numero a ser convertido
	 * @param digitos el numero de digitos despues de la coma
	 * @return el numero formateado
	 */
	public static String format(float number, int digitos){
		String result = null;
		float minFrac = (float) Math.pow(10, (0 - digitos));
		if (number > minFrac || digitos == 0){
			formatter.setMaximumFractionDigits(digitos);
			result = formatter.format(number);	
		}
		
		return result;
	}
	
}