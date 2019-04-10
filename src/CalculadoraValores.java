import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalculadoraValores {

	private String atributo;
	private int indice;
	private int numValores;
	private List<String> valores = new ArrayList<String>();// pila de valores sin repetir que toma el atributo
	private int[] totalValores; // la posicion i indica el numero de ejemplos que toman el valor i, siendo i el
								// orden de entrada de valores no repetidos (último de la pila de valores);
	private double total;// total de ejemplos
	private double[] positivos;// numero de ejemplos positivos/total. mismo orden que totalValores
	private double[] negativos;// numero de ejemplos negativos/total. mismo orden que totalValores
	private double[] proporcionValores; // numero de ejemplos/total. mismo orden que totalValores
	private double[] informaciones;
	private double merito;

	public CalculadoraValores(String atributo, HashMap<Integer, HashMap<Integer, String>> listaEjemplos, int indice,
			List<Integer> ejemplosPositivos, List<Integer> ejemplosNegativos) {
		this.atributo = atributo;
		this.indice = indice;
		parseaValores(listaEjemplos);
		parseaTotal(listaEjemplos);
		calculaResto(listaEjemplos, ejemplosPositivos, ejemplosNegativos);
	}

	private void parseaValores(HashMap<Integer, HashMap<Integer, String>> listaEjemplos) {
		int contador = 0;
		for (int i : listaEjemplos.get(indice).keySet()) {
			if (!this.valores.contains(listaEjemplos.get(indice).get(i))) {
				this.valores.add(contador, listaEjemplos.get(indice).get(i));
				contador++;
			}
		}
		numValores = this.valores.size();
	}

	private void parseaTotal(HashMap<Integer, HashMap<Integer, String>> listaEjemplos) {
		this.total = listaEjemplos.get(indice).size();
	}

	private void calculaResto(HashMap<Integer, HashMap<Integer, String>> listaEjemplos, List<Integer> ejemplosPositivos,
			List<Integer> ejemplosNegativos) {
		calculaTotalValores(listaEjemplos, ejemplosPositivos, ejemplosNegativos);
		for (int i = 0; i < positivos.length; i++) {
			positivos[i] = positivos[i] / totalValores[i];
		}
		for (int i = 0; i < negativos.length; i++) {
			negativos[i] = negativos[i] / totalValores[i];
		}
		for (int i = 0; i < proporcionValores.length; i++) {
			proporcionValores[i] = totalValores[i] / total;
		}
		calculaMerito();

	}

	private void calculaTotalValores(HashMap<Integer, HashMap<Integer, String>> listaEjemplos,
			List<Integer> ejemplosPositivos, List<Integer> ejemplosNegativos) {
		this.totalValores = new int[numValores];
		this.proporcionValores = new double[numValores];
		this.informaciones = new double[numValores];
		this.positivos = new double[numValores];
		this.negativos = new double[numValores];

		for (Integer i : listaEjemplos.get(indice).keySet()) {
			int aux = 0;
			while (aux <= numValores && !listaEjemplos.get(indice).get(i).equals(valores.get(aux)))
				aux++;
			totalValores[aux]++;
			if (!ejemplosPositivos.contains(i))
				negativos[aux]++;
			else
				positivos[aux]++;

		}

	}

	private void calculaInformaciones() {
		for (int i = 0; i < informaciones.length; i++) {
			informaciones[i] = -calculaInfo(positivos[i]) - calculaInfo(negativos[i]);
		}
	}

	private double calculaInfo(double d) {
		double retorno;
		if (d == 0)
			retorno = 0;
		else
			retorno = d * (Math.log(d) / Math.log(2));
		// TODO Auto-generated method stub
		return retorno;
	}

	private void calculaMerito() {
		calculaInformaciones();
		for (int i = 0; i < informaciones.length; i++) {
			merito += proporcionValores[i] * informaciones[i];
		}
	}

	public double getMerito() {
		return this.merito;
	}

	public String getAtributo() {
		// TODO Auto-generated method stub
		return this.atributo;
	}

	public int getIndice() {
		// TODO Auto-generated method stub
		return this.indice;
	}

	public List<String> getValores() {
		// TODO Auto-generated method stub
		return valores;
	}
}
