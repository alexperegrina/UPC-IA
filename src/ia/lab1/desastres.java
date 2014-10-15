package ia.lab1;

import java.util.Random;

import IA.Desastres.Centros;
import IA.Desastres.Grupos;

public class desastres {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		// Creamos los grupos y los centros de resccate 100 grupos, 5 centros para primer ejercicio
		// y las semillas de aletoriedad
		Grupos groups = new Grupos(100, rand.nextInt());
		Centros centers = new Centros(5, 1, rand.nextInt());
		solucion s = new solucion(centers, groups);
		s.solucioninicial1();
		s.print_solucion();
		pair p = s.calcular_coste_total();
		System.out.println(p.getFirst());
		System.out.println(p.getSecond());
	}

}
