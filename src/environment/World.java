package environment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
	private static final String HISTORY_PREFIX = "history";
	public static int NR_STOCKS = 2;
	public static final int HISTORY_SIZE = 262;
	public static final int INPUT_SIZE = 30;
	public static final int NR_AGENTS = 300; //keep multiple of 100 to avoid percentile issues
	
	private ArrayList<Agent> agents;
	public double[][] stockHistory;
	public double[][] stockHistoryBackup;
	
	
	public ArrayList<ArrayList<Double>> currentHistory;
	
	private int currentTick;
	
	public World() {
		currentHistory = new ArrayList<ArrayList<Double>>();
		stockHistory = new double[NR_STOCKS][HISTORY_SIZE];
		stockHistoryBackup = new double[1][HISTORY_SIZE];
		currentTick = INPUT_SIZE;
		
		agents = new ArrayList<Agent>(NR_AGENTS);
		System.out.println(agents.size());
		System.out.println(NR_AGENTS);
		for (int i = 0; i < NR_AGENTS; i++) {
			Agent a = new Agent();
			a.setWorld(this);
			agents.add(a);
		}
		
		for (int i = 0; i < NR_STOCKS; ++i) {
			stockHistory[i] = parseHistory(i);	
		}
		stockHistoryBackup[0] = parseHistory(2);

	}
	
	public void swapStocks() {
		double[][] temp = stockHistory;
		stockHistory = stockHistoryBackup;
		stockHistoryBackup = temp;
	}
	
	public void reset() {
		currentTick = INPUT_SIZE;
		for (Agent a : agents) {
			a.reset();
		}
	}
	
	public static double[] parseHistory(int id) {
		ArrayList<Double> result = new ArrayList<Double>();
		try {
			Scanner in = new Scanner(new FileReader(HISTORY_PREFIX + id));
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] segments = line.split(",");
				result.add(Double.parseDouble(segments[2]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO: Handle file not found
			System.err.println("File not found: "+HISTORY_PREFIX);
			e.printStackTrace();
		}
//		Double max = 0.0;
//		for (Double d : result) {
//			if (d > max) {
//				max = d;
//			}
//		}
//		for (int i = 0; i < result.size(); ++i) {
//			result.set(i,result.get(i)*(1/max));
//		}
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
		currentHistory.clear();
		
		for (int stock = 0; stock < NR_STOCKS; ++stock) {
			currentHistory.add(new ArrayList<Double>());
			initializeHistory(stock);
		}
		int i = 0;
		for (Agent a : agents) {
			a.act(i);
			++i;
		}

	}
	
	public ArrayList<Double> getHistory(int stock) {
		return currentHistory.get(stock);
	}
	
	public void initializeHistory(int stock) {
		ArrayList<Double> prices = new ArrayList<Double>(INPUT_SIZE);
		int j = 0;
		for (int i = currentTick - INPUT_SIZE; i <= currentTick; i++) {
			prices.add(stockHistory[stock][i]/stockHistory[stock][currentTick]);
			j += 1;
		}
		currentHistory.set(stock,prices);
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
	
	public int getCurrentTick() {
		return currentTick;
	}
}
