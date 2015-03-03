package environment;

import java.util.ArrayList;

import neuralnetwork.InvalidInputLayerException;
import neuralnetwork.Network;
import environment.World;

public class Agent implements Comparable<Agent>{
	public static final int START_FUNDS = 10000;
	
	private Network network;
	private ArrayList<Double> stocks;
	private World world;
	private double funds;
	
	public Agent() {
		this(new Network()); 
	}

	public Agent(Network n) {
		funds = START_FUNDS;
		setNetwork(n);
		setStocks(new ArrayList<Double>(World.NR_STOCKS));
	}

	public void act () {
		for (int x = 0; x < world.NR_STOCKS; x++) {
			ArrayList<Integer> history = world.getHistory(x);
			int output = 0;
			try {
				network.input(history);
				output = network.output(0);
			} catch (InvalidInputLayerException e) {
				e.printStackTrace();
			}
			
			/* TODO: Make this non-binary (e.g 0-0.3 = buy, 0.7-1 = sell) */
			if (output == 0) {
				buy(x);
			} else if (output == 1) {
				sell(x);
			}
			
		}
	}
	
	public void buy(int stock) {
		if (stocks.get(stock) == 0) {
			double currentPrice = world.getPrice(stock);
			// Try to use START_FUNDS / NR_STOCKS 
			double goal = Math.min(funds, START_FUNDS/world.NR_STOCKS);
			
			stocks.set(stock, goal / currentPrice);
			
			funds -= goal;
		}
	}

	public void sell(int stock) {
		if (stocks.get(stock) > 0) {
			double currentPrice = world.getPrice(stock);
			funds += (stocks.get(stock) * currentPrice);
			stocks.set(stock, 0.0);
		}
	}
	
	public double getFitness() {
		double result = funds;
		for (int i = 0; i < world.NR_STOCKS; i++) {
			double currentPrice = world.getPrice(i);
			result += stocks.get(i) * currentPrice;
		}
		return result;
	}
	
	/* Accessor functions */

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public double getStock(int index) {
		return stocks.get(index);
	}

	public ArrayList<Double> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Double> stocks) {
		this.stocks = stocks;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
    @Override
    public int compareTo(Agent other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal
    	return (int) (getFitness() - other.getFitness());
    }
	
}
