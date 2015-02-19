package environment;

import java.util.ArrayList;

public class World {
	public static final int NR_STOCKS = 20;
	public static final int HISTORY_SIZE = 100;
	public static final int INPUT_SIZE = 20;
	public static final int NR_AGENTS = 200;
	
	private ArrayList<Agent> agents;
	public double[][] stockHistory;
	
	private int currentTick;
	
	public World() {
		stockHistory = new double[NR_STOCKS][HISTORY_SIZE];
		currentTick = INPUT_SIZE;
		
		agents = new ArrayList<Agent>(NR_AGENTS);
		System.out.println(agents.size());
		System.out.println(NR_AGENTS);
		for (int i = 0; i < NR_AGENTS; i++) {
			Agent a = new Agent();
			a.setWorld(this);
			agents.add(a);
		}
	}
	
	public void runWorld() {
		while (currentTick < HISTORY_SIZE) {
			tick();
			currentTick++;
		}
	}
	
	private void tick() {
		for (Agent a : agents) {
			a.act();
		}
	}
	
	public ArrayList<Integer> getHistory(int stock) {
		ArrayList<Integer> prices = new ArrayList<Integer>(INPUT_SIZE);
		int j = 0;
		for (int i = currentTick - INPUT_SIZE; i <= currentTick; i++) {
			prices.set(j,(int)stockHistory[stock][i]);	// TODO: We should probably use doubles. Neural network only handles ints though. Fix
			j += 1;
		}
		return prices;
	}
	
	public double getPrice(int stock) {
		return stockHistory[stock][currentTick];
	}
	
	/* Accessor functions */

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
