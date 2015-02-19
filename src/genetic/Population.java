package genetic;

import java.util.ArrayList;
import java.util.Collection;

import environment.*;

public class Population {
	public static final double percBreedAvg = 0.8;	// We can either breed with mix or avg. This determines amount bred with avg.
	public static final double percSurviving = 0.5;	// This many total agents survive
	public static final double percMutating = 0.2;	// Out of the surviving agents, this many mutate.
	
	protected ArrayList<Agent> populationAgents = new ArrayList<Agent>(World.NR_AGENTS);
	protected ArrayList<Agent> selectedSpecimens = new ArrayList<Agent>(World.NR_AGENTS);
	
	private World world;
	
	public void evolveCycle(){
		breedAvg();
		breedMix();
		evolve();
		survive();
		finalizeCycle();
	}
	
	public void breedMix(){breedMix(1-percBreedAvg);}
	public void breedAvg(){breedAvg(percBreedAvg);}
	public void evolve(){evolve(percMutating);}
	public void survive(){survive(percSurviving);}
	
	public void breedMix(double perc){
		
	}
	
	public void breedAvg(double perc){
		
	}
	
	public void evolve(double perc){
		
	}
	
	public void survive(double perc){
		
	}
	
	/**
	 * Takes whatever agents have been selected for the next cycle, and 
	 * creates a new world with these specimens as the population.
	 */
	public boolean finalizeCycle() {
		return false;
	}
	
	public double getFitnessSum() {
		double result = 0;
		for (Agent a : populationAgents) {
			result += a.getFitness();
		}
		return result;
	}
	
	public double getFitness(int index) {
		return populationAgents.get(index).getFitness();
	}

	public World getWorld() {
		return world;
	}
	
	public Collection<Agent> getAgents() {
		return populationAgents;
	}
	
	public void run(){
		world.runWorld();
	}
	
}
