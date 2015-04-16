package environment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
	
	// Which data in the history file to use. Format: Ticker, Date (yyyymmdd), Open, High, Low, Close, Volume
	private static final int dataIndex = 5;
	
	private static final String HISTORY_PATH = "historical/weekly/";
	private static final String HISTORY_PREFIX = "history";
	public static final int MAIN_NR_STOCKS = 13;
	public static int NR_STOCKS = 13;
	public static final int HISTORY_SIZE = 200;
	public static final int INPUT_SIZE = 20;
	public static final int NR_AGENTS = 300; //keep multiple of 100 to avoid percentile issues
	
	private ArrayList<Agent> agents;
	public double[][] stockHistory;
	public double[][] stockHistoryBackup;
	
	
	public ArrayList<ArrayList<Double>> currentHistory;
	
	private int currentTick;
	
	public World() {
		World.NR_STOCKS = MAIN_NR_STOCKS;
		currentHistory = new ArrayList<ArrayList<Double>>();
		stockHistory = new double[NR_STOCKS][HISTORY_SIZE];
		stockHistoryBackup = new double[NR_STOCKS][HISTORY_SIZE];
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
		for (int i = 0; i < NR_STOCKS; ++i) {
			stockHistoryBackup[i] = parseHistory(13+i);	
		}

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
			Scanner in = new Scanner(new FileReader(HISTORY_PATH + HISTORY_PREFIX + id + ".csv"));
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] segments = line.split(",");
//				System.out.println(id);
				result.add(Double.parseDouble(segments[dataIndex]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO: Handle file not found
			System.err.println("File not found: "+HISTORY_PREFIX);
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
		for (Agent a : agents) {
			for (int x = 0; x < NR_STOCKS; x++) {
				a.sell(x);
			}
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
		for (int i = currentTick - INPUT_SIZE; i <= currentTick; i++) {
//			System.out.println(stock);
//			System.out.println(currentTick);
//			System.out.println(i);
			prices.add(stockHistory[stock][i]/stockHistory[stock][currentTick]);
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
