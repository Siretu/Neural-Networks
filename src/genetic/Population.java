package genetic;

import neuralnetwork.*;

import java.util.ArrayList;

public class Population {
	private ArrayList<Network> population;
	
	public Population(int inputs, int hiddens, int outputs){
		this(10, inputs, hiddens, outputs);
	}
	
	public Population(int initialSize, int inputs, int hiddens, int outputs){
		population = new ArrayList<Network>();
		
		for(int i = 0;i<initialSize;i++){
//			System.out.println("New network");
			Network n = new Network();
			for(int j = 0;j<inputs;j++){
//				System.out.println("New input");
				n.addNeuron(2, new ConstantInput());
			}
			for(int j = 0;j<hiddens;j++){
				n.addNeuron(1, new Neuron());
			}
			for(int j = 0;j<outputs;j++){
				n.addNeuron(0, new Neuron());
			}
			population.add(n);
		}
	}
	
	public ArrayList<Network> getNetworks(){
		return population;
	}
}
