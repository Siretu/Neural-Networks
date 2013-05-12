package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	private ArrayList<Axon> inputs;
	private double threshold;
	private Random generator;
	
	public Neuron(){
		generator = new Random();
		inputs = new ArrayList<Axon>();
		threshold = generator.nextInt(200)-100;
	}
	
	public Neuron(double threshold){
		this();
		this.threshold = threshold;
	}

	public void addInput(Axon neuron){
		inputs.add(neuron);
	}
	
	public void addInput(Neuron neuron, double weight){
		inputs.add(new Axon(neuron, weight));
	}
	
	public void setThreshold(double threshold){
		this.threshold = threshold;
	}
	
	public double getThreshold(){
		return threshold;
	}

	public int output(){
		double activation = 0;
		
		// Measure weights from all neurons.
		for(Axon i : inputs){
			activation += i.getWeight()*i.getNeuron().output();
		}
		
		if(activation >= threshold){
			System.out.println("Activation: "+activation + " | Threshold: "+threshold);
			return 1;
		} else {
			System.out.println("Threshold: "+threshold + " | Activation: "+activation);
			return 0;
		}
		
	}
}
