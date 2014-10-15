package ia.lab1;

import aima.search.framework.HeuristicFunction;

public class heuristica1 implements HeuristicFunction {

	@Override
	public double getHeuristicValue(Object arg0) {
		solucion sol = (solucion) arg0;
		pair p = sol.calcular_coste_total();
		//como mayor heuristica mejor pero en coste es como menor mejor asi que:
		//heuristica de prueba
		return p.getFirst();
	}

}
