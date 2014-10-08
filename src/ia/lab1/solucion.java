package ia.lab1;

import java.util.ArrayList;

import IA.Desastres.Centro;
import IA.Desastres.Centros;
import IA.Desastres.Grupo;
import IA.Desastres.Grupos;

public class solucion {
	ArrayList<helicoptero> helicopteros;
	Grupos grups;
	Centros centres;
	
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
    	this.helicopteros = new ArrayList<helicoptero>(s.helicopteros);
    }

	
	//Creacion de una solucion inicial 1 un vol cada helicopter si tots helicopters un vol el primer fa un segon vol..
	public void solucioninicial1() {
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
			h.set_vol(grups_vol);
			grups_vol.clear();
			passetgers = g.getNPersonas();
			numGrups = 0;
			if (i == helicopteros.size()-1 && indexGrup < grups.size()) i = -1; 
		}
    }
	
	
	//Operacions
	public solucion operacion_cambiar_grupo(int helicoptero_origen, int vuelo_origen, int grupo_vuelo, int helicoptero_destino, int vuelo_destino) {
    	solucion s1 = new solucion(this);
    	//Do a lot of stuf to change this group
    	return s1;
    }

    public solucion operacion_intercambiar(int helicoptero1, int vuelo1, int grupo1, int helicoptero2, int vuelo2, int grupo2) {
    	solucion s1 = new solucion(this);
    	//Do a lot of stuff to swap this groups
      	return s1;
    }

	//Pintem solucio
	public void print_solucion() {
		for (int i = 0; i < helicopteros.size(); ++i) {
			helicoptero h = helicopteros.get(i);
			System.out.println(h.toString());
		}
			
	}
	
	//calculing cost of the solution
	public double calcular_coste_total(){
		double cost = 0;
		int xAnt = 0,yAnt = 0, xOrg = 0, yOrg = 0;
		for (int i = 0; i < helicopteros.size(); ++i) {

			System.out.println("Helicoptero: " + i);
			double costHel = 0;
			Centro c = centres.get(helicopteros.get(i).getId_centro());
			for (int j = 0; j < helicopteros.get(i).getVuelos_realizados().size(); ++j) {
				double costVol = 0;
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
				costHel += costVol;
			}
			System.out.println(costHel);
			if (costHel > cost) cost = costHel;
		}
		System.out.println();
		System.out.println("El coste de la solucion es: "+ cost);
		return cost;
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
