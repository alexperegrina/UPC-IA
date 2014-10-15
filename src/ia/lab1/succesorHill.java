package ia.lab1;

import java.util.ArrayList;
import java.util.List;

import IA.Desastres.Grupos;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class succesorHill implements SuccessorFunction {

	@Override
	public List getSuccessors(Object arg0) {
		solucion sol = (solucion) arg0;

		ArrayList<Successor> successors = new ArrayList();
		Grupos g = sol.getGrups();
		List<helicoptero> helicopteros = sol.getHelicopteros();
		//We do the change operation for each grup to all other groups
		//Each Helicopter
		for (int i = 0; i < helicopteros.size(); ++i) {
			ArrayList<ArrayList<Integer>> vuelos = helicopteros.get(i).getVuelos_realizados();
			//Each Fly of the helicopter
			for (int j = 0; j < vuelos.size(); ++j) {
				//Each Group that is saved in this fly
				ArrayList<Integer> grups = vuelos.get(j);
				for (int k = 0; k < grups.size(); ++k) {
					//Each Others helicopteros
					for (int i2 = 0; i2 < helicopteros.size(); ++i2) {
						ArrayList<ArrayList<Integer>> vuelos2 = helicopteros.get(i2).getVuelos_realizados();
						for (int j2 = 0; j2 < vuelos2.size(); ++j2) {
							if (j2 != j || i2 != i) {
								ArrayList<Integer> grups2 = vuelos2.get(j2);
								int x = sumaPassetgers(grups2, grups.get(k), g);
								for (int k2 = 0; k2 < grups2.size() && grups2.size() < 3 && x <= 15; ++k2) {
									solucion sol2 = new solucion(sol);
									ArrayList<Integer> grupsSol2 = sol2.getHelicopteros().get(i).getVuelos_realizados().get(j);
									ArrayList<Integer> grupsSol2b = sol2.getHelicopteros().get(i2).getVuelos_realizados().get(j2);
									sol2.operacion_cambiar_grupo(grupsSol2, k, grupsSol2b);
									if (grupsSol2.size() == 0) sol2.getHelicopteros().get(i).getVuelos_realizados().remove(j);
 									successors.add(new Successor(sol2.toString(), sol2));
								}
							}
						}
					}
				}
			}
		}
		
		for (int i = 0; i < helicopteros.size(); ++i) {
			ArrayList<ArrayList<Integer>> vuelos = helicopteros.get(i).getVuelos_realizados();
			//Each Fly of the helicopter
			for (int j = 0; j < vuelos.size(); ++j) {
				//Each Group that is saved in this fly
				ArrayList<Integer> grups = vuelos.get(j);
				for (int k = 0; k < grups.size(); ++k) {
					//Each Others helicopteros
					for (int i2 = 0; i2 < helicopteros.size(); ++i2) {
						ArrayList<ArrayList<Integer>> vuelos2 = helicopteros.get(i2).getVuelos_realizados();
						for (int j2 = 0; j2 < vuelos2.size(); ++j2) {
							if (j2 != j || i2 != i) {
								ArrayList<Integer> grups2 = vuelos2.get(j2);
								for (int k2 = 0; k2 < grups2.size(); ++k2) {
									solucion sol2 = new solucion(sol);
									ArrayList<Integer> grupsSol2 = sol2.getHelicopteros().get(i).getVuelos_realizados().get(j);
									ArrayList<Integer> grupsSol2b = sol2.getHelicopteros().get(i2).getVuelos_realizados().get(j2);
									sol2.operacion_intercambiar(grupsSol2, k, grupsSol2b, k2);
									successors.add(new Successor(sol2.toString(), sol2));
								}
							}
						}
					}
				}
			}
		}
		
		
		return successors;
	}

	private int sumaPassetgers(ArrayList<Integer> grups2, Integer grup, Grupos g) {
		int pass = 0;
		for (int i = 0; i < grups2.size();++i) {
			pass += g.get(grups2.get(i)).getNPersonas();
		}
		pass += g.get(grup).getNPersonas();
		return pass;
	}

}
