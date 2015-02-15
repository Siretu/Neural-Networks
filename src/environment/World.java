package environment;

import java.util.ArrayList;

public class World {
	public static final int nrStocks = 20;
	public static final int historySize = 100;
	
	private ArrayList<Agent> agents;
	public double[][] stockHistory;
	
	public World() {
		stockHistory = new double[nrStocks][historySize];
		
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	public Agent getAgent(int index) {
		return agents.get(index);
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	
	public void addAgent(Agent a) {
		agents.add(a);
	}
	
}
