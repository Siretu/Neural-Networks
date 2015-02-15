package genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import neuralnetwork.InvalidInputLayerException;
import neuralnetwork.Network;

public class Evolution {
	private HashMap<ArrayList<Integer>,ArrayList<Integer>> correctOutput;
	private Population initialPop;
	private int initialSize;
	private Random generator;
	private double generationDeath;
	private int killUnlucky;
	
	public Evolution(HashMap<ArrayList<Integer>,ArrayList<Integer>> correctOutput, Population initialPop){
		this.correctOutput = correctOutput;
		this.initialPop = initialPop;
		generator = new Random();
		killUnlucky = 2;
		generationDeath = 0.5;
	}
	
	public void run() throws InvalidInputLayerException{
		initialSize = initialPop.getNetworks().size();
		while(true){
//			HashMap<Network,Integer> results = new HashMap<Network,Integer>();
			
			// Each input is a list of integers. We try each list and add the result to results.
			for(ArrayList<Integer> input : correctOutput.keySet()){
				
				// We test each network for each input list.
				for(Network n : initialPop.getNetworks()){
					
					// Set the input
					n.input(input);
					
					// Go through the correct answers and add points for everyone that matches the network output.
					for(int i = 0;i<n.getLayer(0).size();i++){
						if(correctOutput.get(input).get(i) == n.output(i)){
							n.setScore(n.getScore()+5);
						}
					}
					int oldOutput = n.output(0);
					ArrayList<Integer> randomInput = new ArrayList<Integer>();
					for(int i = 0;i<input.size();i++){
						randomInput.add(generator.nextInt(2));
					}
					n.input(randomInput);
					for(int i = 0;i<n.getLayer(0).size();i++){
						
						if(n.output(i) == 0){
							n.setScore(n.getScore()+1);
						} else {
							System.out.println("Got 1");
							System.out.println("Result: "+n.getScore());
						}
					}
					System.out.println("Output1: "+oldOutput+" | Output2: "+n.output(0));
				}
			}
			for(Network n : initialPop.getNetworks()){
				System.out.print(n.getScore()+", ");
			}
			for(int i = 0;i<killUnlucky;i++){
				initialPop.getNetworks().remove(generator.nextInt(initialPop.getNetworks().size()));
			}
			Collections.sort(initialPop.getNetworks());
		
			int limit = (int) (initialSize*generationDeath);
			
		}
	}
}
