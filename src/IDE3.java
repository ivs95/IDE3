import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class IDE3 {

	private final int POSICIONRESULTADO = 4;
	// La clave es el nº del atributo en orden de aparición en el txt y en el
	// segundo el nombre del atributo
	private HashMap<Integer, String> listaAtributos;
	// Primer HashMap contiene como clave el nº del ejemplo y como segundo otro
	// HashMap que tiene
	// como clave la clave del atributo y como valor el valor del atributo
	private HashMap<Integer, HashMap<Integer, String>> listaEjemplos;
	// Listas de enteros con los índices de las filas de los ejemplos positivos y
	// negativos en la tabla
	private List<Integer> ejemplosPositivos = new ArrayList<Integer>();
	private List<Integer> ejemplosNegativos = new ArrayList<Integer>();
	private List<CalculadoraValores> calculadoras;
	private ArbolDecision arbol;

	// Cola de prioridad con los nº de los atributos ordenados
	// de mayor a manor por merito
	private PriorityQueue<Integer> meritos;
	private boolean terminado = false;

	public IDE3(HashMap<Integer, String> listaAtributos, HashMap<Integer, HashMap<Integer, String>> listaEjemplos,
			int numEjemplos, List<Integer> ejemplosPositivos, List<Integer> ejemplosNegativos) {
		this.listaAtributos = listaAtributos;
		this.listaEjemplos = listaEjemplos;
		this.ejemplosPositivos = ejemplosPositivos;
		this.ejemplosNegativos = ejemplosNegativos;
//		this.ventana = new NodoVista();

		// Calcular los meritos
		if (numEjemplos > 0) {
			compruebaPositivos();
			if (terminado) {
				arbol = new ArbolDecision("+");

			} else {
				compruebaNegativos();
				if (terminado) {
					arbol = new ArbolDecision("-");

				} else {
					if (listaAtributos.size() <= 1) {
						try {
							throw new Exception("Lista de atributos vacia");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						arbol = this.calculaMeritos();
						for (String s : calculadoras.get(0).getValores())
							arbol.añadeHijo(new ArbolDecision(s));
						int contador = 0;
						for (String s : calculadoras.get(0).getValores()) {
							ArbolDecision aux = arbol.getHijo(contador);
							HashMap<Integer, String> listaAtributosAux = eliminarAtributo(calculadoras.get(0));
							HashMap<Integer, HashMap<Integer, String>> listaEjemplosAux = this.eliminarEjemplos(s,
									calculadoras.get(0).getIndice());
							List<Integer> ejemplosNegativosAux = parseaNegativos(listaEjemplosAux,
									calculadoras.get(0).getIndice());
							List<Integer> ejemplosPositivosAux = parseaPositivos(listaEjemplosAux,
									calculadoras.get(0).getIndice());
							IDE3 aux2 = new IDE3(listaAtributosAux, listaEjemplosAux,
									listaEjemplosAux.get(POSICIONRESULTADO).size(), ejemplosPositivosAux,
									ejemplosNegativosAux);
							aux.añadeHijo(aux2.getArbol());
							contador++;

						}

					}
				}
			}
		}

	}

	public ArbolDecision getArbol() {
		return this.arbol;
	}

	private List<Integer> parseaNegativos(HashMap<Integer, HashMap<Integer, String>> listaEjemplosAux, int indice) {
		List<Integer> ejemplos = new ArrayList<Integer>();
		for (Integer i : listaEjemplosAux.get(POSICIONRESULTADO).keySet())
			if (listaEjemplosAux.get(POSICIONRESULTADO).get(i).equalsIgnoreCase("no")) {
				ejemplos.add(i);
			}
		// TODO Auto-generated method stub
		return ejemplos;
	}

	private List<Integer> parseaPositivos(HashMap<Integer, HashMap<Integer, String>> listaEjemplosAux, int indice) {
		List<Integer> ejemplos = new ArrayList<Integer>();
		for (Integer i : listaEjemplosAux.get(POSICIONRESULTADO).keySet())
			if (listaEjemplosAux.get(POSICIONRESULTADO).get(i).equalsIgnoreCase("si")) {
				ejemplos.add(i);
			}
		// TODO Auto-generated method stub
		return ejemplos;
	}

	private ArbolDecision calculaMeritos() {
		// TODO Auto-generated method stub
		calculadoras = new ArrayList<CalculadoraValores>();
		for (Integer i : this.listaAtributos.keySet()) {
			if (!this.listaAtributos.get(i).equalsIgnoreCase("Jugar")) {
				calculadoras.add(new CalculadoraValores(this.listaAtributos.get(i), this.listaEjemplos, i,
						ejemplosPositivos, ejemplosNegativos));
			}
		}

		calculadoras.sort(new Comparator<CalculadoraValores>() {

			@Override
			public int compare(CalculadoraValores o1, CalculadoraValores o2) {
				// TODO Auto-generated method stub
				return Double.compare(o1.getMerito(), o2.getMerito());
			}
		});

		ArbolDecision d = new ArbolDecision(calculadoras.get(0).getAtributo());

		return d;
	}

	private HashMap<Integer, String> eliminarAtributo(CalculadoraValores calculadoraValoresImp) {
		HashMap<Integer, String> retorno = new HashMap<Integer, String>();
		for (Integer s : listaAtributos.keySet())
			retorno.put(s, listaAtributos.get(s));
		retorno.remove(calculadoraValoresImp.getIndice());
		return retorno;

	}

	private HashMap<Integer, HashMap<Integer, String>> eliminarEjemplos(String valor, Integer indice) {
		HashMap<Integer, HashMap<Integer, String>> retorno = new HashMap<Integer, HashMap<Integer, String>>();
		for (Integer z : listaEjemplos.keySet()) {
			retorno.put(z, new HashMap<Integer, String>());
		}
		retorno.remove(indice);
		for (Integer s : listaEjemplos.get(indice).keySet()) {
			if (listaEjemplos.get(indice).get(s).equals(valor)) {
				for (Integer z : retorno.keySet()) {
					retorno.get(z).put(s, listaEjemplos.get(z).get(s));
				}
			}
		}
		return retorno;

	}

	private void compruebaNegativos() {
		setTerminado(ejemplosPositivos.isEmpty());
	}

	public PriorityQueue<Integer> getMeritos() {
		return this.meritos;
	}

	private void compruebaPositivos() {
		setTerminado(ejemplosNegativos.isEmpty());
	}

	private void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public boolean getTerminado() {
		return this.terminado;
	}
}
