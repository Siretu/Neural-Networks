package genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import neuralnetwork.Axon;
import neuralnetwork.Neuron;

import environment.*;

public class Population {
	public static final double percBreedAvg = 1.0;	// We can either breed with mix or avg. This determines amount bred with avg.
	public static final double percSurviving = 0.5;	// This many total agents survive
	public static final double percMutating = 0.1;	// Out of the surviving agents, this many mutate.
	
	protected ArrayList<Agent> populationAgents = new ArrayList<Agent>(World.NR_AGENTS);
	protected ArrayList<Agent> selectedSpecimens = new ArrayList<Agent>(World.NR_AGENTS);
	
	private World world;
	private int insertIndex = 0;
	
	public Population() {
		world = new World();
	}
	
	public void evolveCycle() {
		kill();
		breed();
		
		System.out.println("-------");
		System.out.println(getAgents().get(0));
		double top = getAgents().get(0).getFitness();
		int perc = 0;
		for (Agent a : getAgents()) {
			if (a.getFitness() == top) {
				perc++;
			}
		}
//		System.out.println(getAgents().get(1));
//		System.out.println(getAgents().get(2));
//		System.out.println(perc + " / " + getAgents().size());
		mutate();
//		System.out.println(world.getHistory(0));
//		world.reset();
//		world.swapStocks();
//		World.NR_STOCKS = 2;
//		run();
//		System.out.println(getAgents().get(0));
//		System.out.println(getAgents().get(1));
//		System.out.println(getAgents().get(2));
//		System.out.println(world.getHistory(0));
		world.reset();
		//world.swapStocks();
		World.NR_STOCKS = World.MAIN_NR_STOCKS;
		
	}
	
	public void kill() {
		Collections.sort(world.getAgents(),Collections.reverseOrder());
		insertIndex = (int) (world.getAgents().size() * percSurviving);
	}
	
	public Agent roulette() {
		double size = getFitnessSum();
		double index = (size * Math.random());
		int currentAgent = 0;
		while (index > 0) {
			index -= getFitness(currentAgent);
			++currentAgent;
		}
		--currentAgent;
		
		return world.getAgent(currentAgent);
	}
	
	public void breed() {
		while (insertIndex < world.getAgents().size()) {
			Agent a = roulette();
			Agent b = roulette();
			Agent c = breedAgents(a,b);
			world.getAgents().set(insertIndex, c);
			++insertIndex;
		}

	}
	
	public Agent breedAgents(Agent a, Agent b){
		Agent c = new Agent(a);
		c.setWorld(a.getWorld());
		for (int layer = 0; layer < a.getNetwork().getLayers().size() - 1; ++layer) {
			ArrayList<Neuron> currLayer = a.getNetwork().getLayer(layer);
			for (int node = 0; node < currLayer.size(); ++node) {
				Neuron currNeuron = currLayer.get(node);
				for (int input = 0; input < currNeuron.getInputs().size(); ++input) {
					Axon currAxon = currNeuron.getInputs().get(input);
					double weight;
					double weight1 = a.getNetwork().getLayer(layer).get(node).getInputs().get(input).getWeight();
					double weight2 = b.getNetwork().getLayer(layer).get(node).getInputs().get(input).getWeight();
					
					if (Math.random() < percBreedAvg) {
						weight = breedAvg(weight1, weight2);
					} else {
						weight = breedMix(weight1, weight2);
					}
					c.getNetwork().getLayer(layer).get(node).getInputs().get(input).setWeight(weight);
				}
			}
		}

		return c;
	}
	
	public double breedMix(double w1, double w2) {
		if (Math.random() > 0.5) {
			return w1;
		} else {
			return w2;
		}
	}
	
	public double breedAvg(double w1, double w2) {
		return (w1+w2)/2;
	}
	
	public void mutate(){
//		for (Agent a : world.getAgents()) {
//			if (Math.random() < percMutating){
//				mutateAgent(a);
//			}
//		}
		for (int i = 1; i < world.getAgents().size(); ++i) {
			if (Math.random() < percMutating){
				mutateAgent(world.getAgents().get(i));
			}
		}
	}
	
	public void mutateAgent(Agent a) {
		for (int layer = 0; layer < a.getNetwork().getLayers().size() - 1; ++layer) {
			ArrayList<Neuron> currLayer = a.getNetwork().getLayer(layer);
			for (int node = 0; node < currLayer.size(); ++node) {
				Neuron currNeuron = currLayer.get(node);
				for (int input = 0; input < currNeuron.getInputs().size(); ++input) {
					Axon currAxon = currNeuron.getInputs().get(input);
					currAxon.setWeight(currAxon.getWeight() + (Math.random()-0.5));
					
				}
			}
		}
	}
	
	public double getFitnessSum() {
		double result = 0;
		for (Agent a : world.getAgents()) {
			result += a.getFitness();
		}
		return result;
	}
	
	public double getFitness(int index) {
		return world.getAgents().get(index).getFitness();
	}

	public World getWorld() {
		return world;
	}
	
	public ArrayList<Agent> getAgents() {
		return world.getAgents();
	}
	
	public void run(){
		world.runWorld();
	}
	
}
