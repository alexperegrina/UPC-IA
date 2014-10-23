package ia.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import IA.Desastres.Centro;
import IA.Desastres.Centros;
import IA.Desastres.Grupo;
import IA.Desastres.Grupos;

public class solucion {
	ArrayList<helicoptero> helicopteros;
	Grupos grups;
	Centros centres;
	
	Comparator<Object> comparar_Npersonas = new Comparator<Object>() {
		@Override
		public int compare(Object g1, Object g2) {
			return new Integer(grups.get((Integer)g1).getNPersonas()).compareTo(new Integer(grups.get((Integer)g2).getNPersonas()));
		}
	};
	
	public solucion(Centros centres, Grupos grups){
		this.grups = grups;
		this.centres = centres;
        int nCentros = centres.size();
        helicopteros = new ArrayList<helicoptero>();
        for (int i=0; i < nCentros; i++){
            int numHelicopteros = centres.get(i).getNHelicopteros();
            for (int j = 0; j < numHelicopteros; ++j) {
            	helicopteros.add(new helicoptero(i));
            }
        }
    }
	
	public solucion(solucion s) {
    	this.grups = s.grups;
    	this.centres = s.centres;

        helicopteros = new ArrayList<helicoptero>();

		List<helicoptero> helicopteros_orig = s.getHelicopteros();
      //Each Helicopter
  		for (int i = 0; i < helicopteros_orig.size(); ++i) {
  			helicoptero h = helicopteros_orig.get(i);
  			helicopteros.add(new helicoptero(h.getId_centro()));
  			ArrayList<ArrayList<Integer>> vuelos = h.getVuelos_realizados();
  			helicoptero h_new = helicopteros.get(i);
  			for (int j = 0; j < vuelos.size(); ++j) {
  				ArrayList<Integer> x = new ArrayList<Integer>(vuelos.get(j));
  				h_new.set_vol(x);
  			}
  		}
    }

	/**
	 * Creacion de una solucion inicial 1 un vol cada helicopter si tots helicopters un vol 
	 * el primer fa un segon vol..
	 */
	public void solucionInicial1() {
		int indexGrup = 0;
		int passetgers = 0;
		int numGrups = 0;
        ArrayList<Integer> grups_vol = new ArrayList<Integer>();
		Grupo g = grups.get(0);
		passetgers += g.getNPersonas();
		
		for (int i = 0; i < helicopteros.size(); ++i) {
			helicoptero h = helicopteros.get(i);
			while (passetgers < 15 && numGrups < 3 && indexGrup < grups.size()) {
				grups_vol.add(indexGrup);
				++numGrups;
				++indexGrup;
				if (indexGrup < grups.size()) {
					g = grups.get(indexGrup);
					passetgers += g.getNPersonas();
				}
			}
			if (grups_vol.size() != 0) h.set_vol(grups_vol);
			grups_vol.clear();
			passetgers = g.getNPersonas();
			numGrups = 0;
			if (i == helicopteros.size()-1 && indexGrup < grups.size()) i = -1; 
		}
    }
	
	/**
	 * Solucion en la que se separa en dos conjuntos los grupos (prioritarios, no prioritarios)
	 * se ordena cada conjunto de menor a mayor en funcion al numero de miembros en el grupos.
	 */
	@SuppressWarnings("unchecked")
	public void solucionInicial2() {
		ArrayList<Integer> prioridad = new ArrayList<Integer>();
		ArrayList<Integer> no_prioridad = new ArrayList<Integer>();
		for(int i = 0; i < grups.size(); i++) {
			if(grups.get(i).getPrioridad() == 1) {
				prioridad.add(i);
			}
			else {
				no_prioridad.add(i);
			}
		}
		
		Collections.sort(prioridad,comparar_Npersonas);
		Collections.sort(no_prioridad,comparar_Npersonas);
		
		//concatenamos los dos arraylist
		prioridad.addAll(no_prioridad);
		procesarSolucionInicial(prioridad);
	}
	
	/**
	 *  Solucion inicial en la qual se ordenan todos los grupos de menor a mayor 
	 *  segun el numero de personas que forman el grupo.
	 */
	public void solucionInicial3() {
		ArrayList<Integer> prioridad = new ArrayList<Integer>();
		for(int i = 0; i < grups.size(); i++) {
			prioridad.add(i);
		}
		Collections.sort(prioridad,comparar_Npersonas);
		procesarSolucionInicial(prioridad);
	}
	
	/**
	 * Metodo que aplica la misma estrategia que SolucionInicial1 pero 
	 * le pasamos por parametros un array con los grupos ordenados a nuestro antojo
	 * @param grupos {@link ArrayList} que contiene los id de los grupos.
	 */
	private void procesarSolucionInicial(ArrayList<Integer> grupos) {
		int indexGrup = 0;
		int passetgers = 0;
		int numGrups = 0;
        ArrayList<Integer> grups_vol = new ArrayList<Integer>();
        Grupo g = grups.get(grupos.get(0));
		passetgers += g.getNPersonas();
		
		for (int i = 0; i < helicopteros.size(); ++i) {
			helicoptero h = helicopteros.get(i);
			while (passetgers < 15 && numGrups < 3 && indexGrup < grups.size()) {
				grups_vol.add(grupos.get(indexGrup));
				++numGrups;
				++indexGrup;
				if (indexGrup < grups.size()) {
					g = grups.get(grupos.get(indexGrup));
					passetgers += g.getNPersonas();
				}
			}
			if (grups_vol.size() != 0) h.set_vol(grups_vol);
			grups_vol.clear();
			passetgers = g.getNPersonas();
			numGrups = 0;
			if (i == helicopteros.size()-1 && indexGrup < grups.size()) i = -1; 
		}
    }
	
	
	//Operacions
	public void operacion_cambiar_grupo(ArrayList<Integer> grups, int k, ArrayList<Integer> grups2) {
		Integer grup = grups.get(k);
		grups.remove(k);
		grups2.add(grup);
	}
	
	public void operacion_intercambiar(ArrayList<Integer> grups, int k, ArrayList<Integer> grups2, int k2) {
		Integer grup1 = grups.get(k);
		Integer grup2 = grups2.get(k2);
		grups.remove(k);
		grups2.remove(k2);
		grups.add(grup2);
		grups2.add(grup1);
		
	}
	
	public void operacion_intercambiar_helicopteros(int h1, int h2) {
		ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>(helicopteros.get(h1).getVuelos_realizados());
		ArrayList<ArrayList<Integer>> tmp2 = new ArrayList<ArrayList<Integer>>(helicopteros.get(h2).getVuelos_realizados());
		helicopteros.get(h1).getVuelos_realizados().clear();
		helicopteros.get(h2).getVuelos_realizados().clear();
		helicopteros.get(h1).setVuelos_realizados(tmp2);
		helicopteros.get(h2).setVuelos_realizados(tmp);
	}

	//Pintem solucio
	public void print_solucion() {
		for (int i = 0; i < helicopteros.size(); ++i) {
			helicoptero h = helicopteros.get(i);
			System.out.println(h.toString());
		}
			
	}
	
	//calculing cost of the solution
	public pair calcular_coste_total(){
		double cost = 0;
		double costUrgent = 0;
		int xAnt = 0,yAnt = 0, xOrg = 0, yOrg = 0;
		for (int i = 0; i < helicopteros.size(); ++i) {

			double costHel = 0;
			double costHelUrg = 0;
			Centro c = centres.get(helicopteros.get(i).getId_centro());
			for (int j = 0; j < helicopteros.get(i).getVuelos_realizados().size(); ++j) {
				double costVol = 0;
				double costPersonesUrg = 0;
				boolean volPrioritat = false;
				if (j != 0) costVol += 10; //penalizacion parada entre vuelos;
				for (int k = 0; k < helicopteros.get(i).getVuelos_realizados().get(j).size(); ++k) {
					Grupo g = grups.get(helicopteros.get(i).getVuelos_realizados().get(j).get(k));
					if (k == 0) { 
						xAnt = xOrg = c.getCoordX();
						yAnt = yOrg = c.getCoordY();
					}
					//System.out.println("coordenadas anada entre punt i punt: " );
					//System.out.println("x1="+ xAnt +" y1="+ yAnt +" x2="+ g.getCoordX() + " y2="+ g.getCoordY());
					double dist = calcular_distancia(xAnt, yAnt, g.getCoordX(), g.getCoordY());

					costVol += calcular_time(dist);
					if (g.getPrioridad() == 1) {
						costPersonesUrg += g.getNPersonas()*2;
						//System.out.println("helicoptero = " + i + "vuelo = "+ j + "grupo = " + k + " PRIORIDAD");
						volPrioritat = true;
					} else {
						costVol += g.getNPersonas();
						//System.out.println("helicoptero = " + i + "vuelo ="+ j + "grupo =" + k  + " NO PRIORIDAD");
						
					}
					xAnt = g.getCoordX();
					yAnt = g.getCoordY();
				}
				//suma temps tornada
				//System.out.println("coordenadas anada entre puntUltimGrup i puntCentre: " );
				//System.out.println("x1="+ xAnt +" y1="+ yAnt +" x2="+ xOrg + " y2="+ yOrg);
				double dist = calcular_distancia(xAnt, yAnt, xOrg, yOrg);
				costVol += calcular_time(dist);
				//System.out.println("temps vol :"+ j + " es de: " + costVol );
				//System.out.println();
				costHel += costVol + costPersonesUrg;
				if (volPrioritat) {
					costHelUrg = costHel;
				}
			}
			cost += costHel;
			if (costHelUrg > costUrgent) costUrgent = costHelUrg;
		}
		pair p = new pair(cost, costUrgent);
		return p;
    }
	
	
	public ArrayList<helicoptero> getHelicopteros() {
		return helicopteros;
	}

	public Grupos getGrups() {
		return grups;
	}

	public Centros getCentres() {
		return centres;
	}

	public Comparator<Object> getComparar_Npersonas() {
		return comparar_Npersonas;
	}

	//distance between to points;
	private double calcular_distancia(int x1, int y1, int x2, int y2) {
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
