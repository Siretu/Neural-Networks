package environment;

import java.util.ArrayList;

import neuralnetwork.Network;
import environment.World;

public class Agent {
	private Network network;
	private ArrayList<Double> stocks;
	
	public Agent() {
		setNetwork(new Network());
		setStocks(new ArrayList<Double>(World.nrStocks));
	}
	
	public Agent(Network n) {
		setNetwork(n);
		setStocks(new ArrayList<Double>(World.nrStocks));
	}

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
	
}
