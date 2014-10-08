package ia.lab1;

import java.util.ArrayList;

public class helicoptero {
	ArrayList<ArrayList<Integer>> vuelos_realizados;
    int id_centro;
    

    public helicoptero(int i) {
		id_centro = i;
		vuelos_realizados = new ArrayList<ArrayList<Integer>>();
	}
    
    public void set_vol(ArrayList<Integer> grups) {
    	vuelos_realizados.add(new ArrayList<Integer>(grups));
    }

    @Override
	public String toString() {
		return "helicoptero [vuelos_realizados=" + vuelos_realizados
				+ ", id_centro=" + id_centro + "]";
	}

	//Get_vuelos_realizados 
	public ArrayList<ArrayList<Integer>> getVuelos_realizados() {
		return vuelos_realizados;
	}

	//Get_id_centro
	public int getId_centro() {
		return id_centro;
	}
    
	
    
    
    
}
