package ia.lab1;

import java.util.ArrayList;

import IA.Desastres.Centro;
import IA.Desastres.Centros;
import IA.Desastres.Grupo;
import IA.Desastres.Grupos;
import aima.search.framework.HeuristicFunction;

public class heuristica2 implements HeuristicFunction{

	private Boolean DEBUG = false;
	@Override
	public double getHeuristicValue(Object state) {
		solucion s = (solucion) state;
		double h = 0;
		pair p = new pair();
		
		for(int i = 0; i < s.helicopteros.size(); i++) {
			helicoptero heli = s.helicopteros.get(i);
			for(int j = 0; j < heli.getVuelos_realizados().size(); j++) {
				ArrayList<Integer> grupos = heli.getVuelos_realizados().get(j);
				p = calcular_heuristica_vuelo(s.getCentres().get(heli.getId_centro()), 
						s.getGrups(), grupos,j);
				
				h += p.getFirst();
				if(p.getSecond() == 0) {
					h +=  Math.log(p.getFirst());
				}
			}
		}
		if(DEBUG) {
			System.out.println("h: "+Double.toString(h));
		}
		return h;
	}
	
	/**
	 * Calcula el tiempo de realizar el vuelo, teniendo en cuenta el en que orden se realiza el rescate de los 
	 * grupos prioritarios obteniendo menor resultado cuanto antes se rescata los prioritarios.
	 * Miramos el numero de vuelo que se realiza y dentro de este vuelo en que orden se realizar,
	 * obteniendo el calculo de "time = timeVuelo*numVuelo + timeVuelo*i" donde "i" indica la posicion del grupo 
	 * dentro del vuelo.
	 * @param c
	 * @param g
	 * @param grupos
	 * @param numVuelo, integer para saver el numero de vuelo. 
	 * @return double tiempo
	 */
	private pair calcular_heuristica_vuelo(Centro c,Grupos g, ArrayList<Integer> grupos, int numVuelo) {
		if(DEBUG) {
			System.out.println("Heuristica_vuelo");
		}

		double prioridad = 0;
		double distTemp = 0;
		double time = 0;
		double timeTemp = 0;
		pair start = new pair();
		pair end = new pair();
		
		for(int i = 0; i < grupos.size(); i++) {
			end.setFirst((double)g.get(grupos.get(i)).getCoordX());
			end.setSecond((double)g.get(grupos.get(i)).getCoordY());

			if(i == 0) {
				distTemp = calcular_distancia(c.getCoordX(), c.getCoordY(), 
						end.getFirst().intValue(), end.getSecond().intValue());   
			}
			else if (i == grupos.size()-1) {
				distTemp = calcular_distancia(
						end.getFirst().intValue(), end.getSecond().intValue(),
						c.getCoordX(), c.getCoordY());  
			}
			else {
				distTemp = calcular_distancia(
						start.getFirst().intValue(), start.getSecond().intValue(), 
						end.getFirst().intValue(), end.getSecond().intValue());
			}
			
			timeTemp = calcular_time(distTemp);
			if(g.get(grupos.get(i)).getPrioridad() == 1) {
				prioridad = 1;
//				time += timeTemp*numVuelo + timeTemp*i;
//				time += Math.pow(timeTemp, numVuelo);
				time += Math.pow(Math.pow(timeTemp, numVuelo),2);
//				time += timeTemp;
			}
			else {
				time += Math.pow(timeTemp, 2);
//				time += timeTemp;
			}
			
			start.setFirst(end.getFirst());
			start.setSecond(end.getSecond());
		}
		
		return new pair(time,prioridad);
	}
	
	//distance between to points;
	private double calcular_distancia(int x1, int y1, int x2, int y2) {
		if(DEBUG) {
			System.out.println("start-x: "+ Integer.toString(x1) + " " + "start-y: "+ Integer.toString(y1));
			System.out.println("end-x: "+ Integer.toString(x2) + " " + "end-y: "+ Integer.toString(y2));
		}
		
    	double rx = (x1 - x2)*(x1 - x2);
    	double ry = (y1 - y2)*(y1 - y2);
    	return Math.sqrt(rx+ry);
	}
	
	private double calcular_time(double distance) {
		double speed = 100.0d; // kmph

		double speed_in_meters_per_minute = ( speed * 1000 ) / 60; // mpm

		// now calculate time in minutes
		double time = (double)distance*1000 / speed_in_meters_per_minute ;
		return time;
	}
}