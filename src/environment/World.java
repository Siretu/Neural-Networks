package environment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
	private static final String HISTORY_FILENAME = "history";
	public static final int NR_STOCKS = 1;
	public static final int HISTORY_SIZE = 105;
	public static final int INPUT_SIZE = 30;
	public static final int NR_AGENTS = 200; //keep multiple of 100 to avoid percentile issues
	
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
		
		stockHistory[0] = parseHistory();
	}
	
	public void reset() {
		currentTick = INPUT_SIZE;
		for (Agent a : agents) {
			a.reset();
		}
	}
	
	public static double[] parseHistory() {
		ArrayList<Double> result = new ArrayList<Double>();
		try {
			Scanner in = new Scanner(new FileReader(HISTORY_FILENAME));
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] segments = line.split(",");
				result.add(Double.parseDouble(segments[2]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO: Handle file not found
			System.err.println("File not found: "+HISTORY_FILENAME);
			e.printStackTrace();
		}
		double[] result2 = new double[result.size()];
		for (int i = 0; i < result.size(); i++) {
			result2[i] = result.get(i);
		}
		return result2;
		
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
			prices.add((int)stockHistory[stock][i]);	// TODO: We should probably use doubles. Neural network only handles ints though. Fix
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
