package environment;

import java.util.ArrayList;
import java.util.Arrays;

import neuralnetwork.InvalidInputLayerException;
import neuralnetwork.Network;
import environment.World;

public class Agent implements Comparable<Agent>{
	public static final int START_FUNDS = 10000;
	
	private Network network;
	private ArrayList<Double> stocks;
	private World world;
	private double funds;
	
	public ArrayList<Character> choices;
	public ArrayList<Character> choices2;
	
	public Agent() {
		this(new Network()); 
	}
	
	public Agent(Agent a) {
		this(a.getNetwork());
	}

	public Agent(Network n) {
		choices = new ArrayList<Character>();
		choices2 = new ArrayList<Character>();
		funds = START_FUNDS;
		network = new Network(n);
		setStocks(new ArrayList<Double>(World.NR_STOCKS));
		for (int i = 0; i < World.NR_STOCKS; ++i) {
			stocks.add(0.0);
		}
	}

	public void reset() {
		choices.clear();
		choices2.clear();
		funds = START_FUNDS;
		for (int i = 0; i < World.NR_STOCKS; ++i) {
			stocks.set(i,0.0);
		}
	}
	
	public void act (int id) {
		for (int x = 0; x < World.NR_STOCKS; x++) {
			ArrayList<Double> history = world.getHistory(x);
			
			network.input(history);
			double output1 = network.output(0);
//			double output2 = network.output(1);
//			System.out.println(output1);
//			System.out.println(id);
			if (output1 > 0.1) {
//				System.out.println("buy");
				buy(x);
//				funds = Math.max(0,funds-500);
				if (x == 0) {
					choices.add('b');
				}
			} else if (output1 < -0.1) {
//				System.out.println("sell");
				sell(x);
				if (x == 0) {
					choices.add('s');
				}
			} else {
				sell(x);
				if (x == 0) {
					choices.add('s');
					//choices.add('0');
				}
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
    
    public String toString() {
    	return getFitness() + ": " + Arrays.toString(choices.toArray());// + "\n" + Arrays.toString(choices2.toArray());
    }
	
}
