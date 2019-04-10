import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class ArbolDecision {

	private String dato;
	private ArbolDecision padre;
	private LinkedList<ArbolDecision> hijos;
	private Tipo tipo;

	public ArbolDecision(String datoRaiz) {

		this.dato = datoRaiz;
		this.hijos = new LinkedList<ArbolDecision>();
		setTipo();

	}

	private void setTipo() {
		if (dato.equalsIgnoreCase("si"))
			this.tipo = Tipo.SI;
		else if (dato.equalsIgnoreCase("no"))
			this.tipo = Tipo.NO;
		else if (dato.equalsIgnoreCase("tiempoexterior") || dato.equalsIgnoreCase("humedad")
				|| dato.equalsIgnoreCase("temperatura") || dato.equalsIgnoreCase("viento"))
			this.tipo = Tipo.ATRIBUTO;
		else
			this.tipo = Tipo.VALOR;
	}

	public ArbolDecision() {
	}

	public ArbolDecision añadeHijo(ArbolDecision hijo) {

		hijo.padre = this;
		hijos.add(hijo);
		return hijo;
	}

	public LinkedList<ArbolDecision> getHijos() {
		return this.hijos;
	}

	public String getRaiz() {
		// TODO Auto-generated method stub
		return this.dato;
	}

	public ArbolDecision getHijo(int i) {
		return this.hijos.get(i);
	}

	public String toString() {
		return this.dato;
	}

	public MutableTreeNode getPadre() {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode padre = new DefaultMutableTreeNode(this.dato);
		return padre;
	}

	public String getDato() {
		// TODO Auto-generated method stub
		return this.dato;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ArbolDecision) {
			ArbolDecision otra = (ArbolDecision) o;
			if (otra.padre == this.padre && otra.dato == this.dato)
				return true;
			else
				return false;
		} else
			return false;

	}

	public Tipo getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}
}
