package genetic;

import java.util.ArrayList;
import java.util.Collection;

import environment.*;

public class Population {
	public static final int percBreedAvg = 20;
	public static final int percBreedMix = 20;	
	public static final int percSurviving = 40;
	public static final int percMutating = 20;
	
	protected ArrayList<Agent> selectedSpecimens = new ArrayList<Agent>(World.NR_AGENTS);
	
	public void evolveCycle(){
		breedAvg();
		breedMix();
		evolve();
		survive();
		finalizeCycle();
	}
	
	public void breedMix(){breedMix(percBreedMix);}
	public void breedAvg(){breedAvg(percBreedAvg);}
	public void evolve(){evolve(percMutating);}
	public void survive(){survive(percSurviving);}
	
	public void breedMix(int perc){
		
	}
	
	public void breedAvg(int perc){
		
	}
	
	public void evolve(int perc){
		
	}
	
	public void survive(int perc){
		
	}
	
	/**
	 * Takes whatever agents have been selected for the next cycle, and 
	 * creates a new world with these specimens as the population.
	 */
	public boolean finalizeCycle();
	
	public double getFitnessSum();
	
	public double getFitness(int index);

	public World getWorld();
	
	public Collection<Agent> getAgents();
	
	public void run();//{world.runWorld()}
	
}
