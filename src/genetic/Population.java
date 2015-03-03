package genetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import neuralnetwork.Axon;
import neuralnetwork.Neuron;

import environment.*;

public class Population {
	public static final double percBreedAvg = 0.0;	// We can either breed with mix or avg. This determines amount bred with avg.
	public static final double percSurviving = 0.5;	// This many total agents survive
	public static final double percMutating = 0.2;	// Out of the surviving agents, this many mutate.
	
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
		evolve();
		world.reset();
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
			Agent c = breedMix(a,b);
			world.getAgents().set(insertIndex, c);
			++insertIndex;
		}

	}
	
	public Agent breedMix(Agent a, Agent b){
		Agent c = new Agent(a);
		c.setWorld(a.getWorld());
		for (int layer = 0; layer < a.getNetwork().getLayers().size() - 1; ++layer) {
			ArrayList<Neuron> currLayer = a.getNetwork().getLayer(layer);
			for (int node = 0; node < currLayer.size(); ++node) {
				Neuron currNeuron = currLayer.get(node);
				for (int input = 0; input < currNeuron.getInputs().size(); ++input) {
					Axon currAxon = currNeuron.getInputs().get(input);
					double weight = 0;
					double weight1 = a.getNetwork().getLayer(layer).get(node).getInputs().get(input).getWeight();
					double weight2 = b.getNetwork().getLayer(layer).get(node).getInputs().get(input).getWeight();

					weight = (weight1 + weight2) / 2;
					c.getNetwork().getLayer(layer).get(node).getInputs().get(input).setWeight(weight);
				}
			}
		}

		return c;
	}
	
	public Agent breedAvg(Agent a, Agent b){
		return null;
	}
	
	public void evolve(){
		for (Agent a : world.getAgents()) {
			if (Math.random() < percMutating){
				evolveAgent(a);
			}
		}
	}
	
	public void evolveAgent(Agent a) {
		
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
