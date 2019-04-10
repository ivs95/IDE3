import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LectorFichero {

	private static final String VALORPOSITIVO = "si";
	private HashMap<Integer, String> listaAtributos = new HashMap<Integer, String>();
	// Primer HashMap contiene como clave la clave del atributo y como segundo otro
	// HashMap que tiene
	// como clave el nº del ejemplo y como valor el valor del atributo
	private HashMap<Integer, HashMap<Integer, String>> listaEjemplos = new HashMap<Integer, HashMap<Integer, String>>();
	// Listas de enteros con los índices de las filas de los ejemplos positivos y
	// negativos en la tabla
	private List<Integer> ejemplosPositivos = new ArrayList<Integer>();
	private List<Integer> ejemplosNegativos = new ArrayList<Integer>();

	private final String FICHEROATRIBUTOS = "AtributosJuego.txt";
	private final String FICHEROVALORES = "Juego.txt";
	private final int NUMATRIBUTOS = 5;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LectorFichero();

	}

	public LectorFichero() {
		try {

			File fichAtributos = new File(FICHEROATRIBUTOS);
			File fichValores = new File(FICHEROVALORES);
			Scanner scanner = new Scanner(fichAtributos);
			String atributos = scanner.nextLine();
			String[] atributosAux = atributos.split(",");
			for (int i = 0; i < NUMATRIBUTOS; i++) {
				listaAtributos.put(i, atributosAux[i]);
				listaEjemplos.put(i, new HashMap<Integer, String>());
			}
			String valores = null;
			int contador = 0;
			scanner.close();
			scanner = new Scanner(fichValores);
			while (scanner.hasNextLine()) {
				valores = scanner.nextLine();
				String[] valoresAux = valores.split(",");
				for (int i = 0; i < NUMATRIBUTOS; i++) {
					listaEjemplos.get(i).put(contador, valoresAux[i]);
				}
				if (valoresAux[4].equalsIgnoreCase(VALORPOSITIVO)) {
					ejemplosPositivos.add(contador);
				} else {
					ejemplosNegativos.add(contador);
				}
				contador++;
			}
			IDE3 ide = new IDE3(listaAtributos, listaEjemplos, contador, ejemplosPositivos, ejemplosNegativos);
			new Vista(ide.getArbol());
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("he petao");
			e.printStackTrace();
		}
	}
}
