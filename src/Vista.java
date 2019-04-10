import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Vista {

	private static DefaultTreeModel modelo;
	private static ArbolDecision arbol;
	private static DefaultMutableTreeNode actual;
	private static JTree tree;

	public Vista(ArbolDecision arb) {
		// TODO Auto-generated constructor stub
		arbol = arb;
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(Vista.arbol);
		modelo = new DefaultTreeModel(raiz);
		tree = new JTree(modelo);
		for (int i = 0; i < arb.getHijos().size(); i++) {
			construyeArbol(raiz, arb.getHijos().get(i), i);
		}
		muestra();
	}

	public static void pintaValores(String[] valores) {
		int i = 1;
		for (String s : valores) {
			DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(s);
			modelo.insertNodeInto(hijo, actual, i);
			i++;
		}

		actual = (DefaultMutableTreeNode) actual.getChildAt(1);
	}

	public static void construyeArbol(DefaultMutableTreeNode raiz, ArbolDecision b, int pos) {
		if (b == null)
			return;

		DefaultMutableTreeNode arbol = new DefaultMutableTreeNode(b.getDato());
		modelo.insertNodeInto(arbol, raiz, pos);
		for (int i = 0; i < b.getHijos().size(); i++) {
			construyeArbol(arbol, b.getHijos().get(i), i);
		}
	}

	public void muestra() {
		// Construccion y visualizacion de la ventana
		JFrame v = new JFrame();
		v.setPreferredSize(new Dimension(400,600));
		JScrollPane scroll = new JScrollPane(tree);
		v.getContentPane().add(scroll);
		v.pack();
		v.setVisible(true);
		v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
