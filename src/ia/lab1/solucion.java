package ia.lab1;

import java.util.ArrayList;

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

	//Pintem solucio
	public void print_solucion() {
		for (int i = 0; i < helicopteros.size(); ++i) {
			helicoptero h = helicopteros.get(i);
			System.out.println(h.toString());
		}
			
	}
}
