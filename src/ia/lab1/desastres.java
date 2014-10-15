package ia.lab1;

import java.util.Random;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
		s.solucionInicial1();
		System.out.println("initial solution");
		s.print_solucion();
		pair reslt = s.calcular_coste_total();
	    System.out.println("Coste Total = "+ reslt.getFirst() + " Coste Prioridad = "+ reslt.getSecond());
		
		Problem p = new Problem(s, new succesorHill(), new solucionFinal(), new heuristica1());
        Search s2 = new HillClimbingSearch();
        try {
        	
			SearchAgent agent = new SearchAgent(p,s2);
			//printActions(agent.getActions());
		    //printInstrumentation(agent.getInstrumentation());
		    solucion fin = (solucion) s2.getGoalState();
		    fin.print_solucion();

		    reslt = fin.calcular_coste_total();
		    System.out.println("Coste Total = "+ reslt.getFirst() + " Coste Prioridad = "+ reslt.getSecond());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

}
