package neuralnetwork;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Neuron {
	private static final boolean DEBUGGING_NEURON = false;
	private static void debug_println(String s){
		if(DEBUGGING_NEURON) System.out.println(s);
	}
	
	private ArrayList<Axon> inputs;	//NEEDS to stay an ordered collection
	private double threshold;
	private Random generator;
	private double value;
	
	public Neuron(){
		generator = new Random();
		inputs = new ArrayList<Axon>();
		//threshold = generator.nextInt(200)-100;
	}
	
	public Neuron(double threshold){
		this();
		this.threshold = threshold;
	}
	
	/**
	 * copy constructor
	 * XXX: DOES NOT COPY AXON LINKS - 
	 * do this manually, since you need to point to other neurons
	 */
	public Neuron(Neuron n){
		this();
		threshold = n.threshold;
	}
	
	public ArrayList<Axon> getInputs() {
		return inputs;
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

	public double output(){
		return value;
	}
	
	
	public void setValue(double i) {
		value = i;
	}
	
	public void setValue(){
		double activation = 0;

		// Measure weights from all neurons.
		for(Axon i : inputs){
			activation += i.getWeight()*i.getNeuron().output();
		}
		int A = -300;
		int B = 300;
		int a = -10;
		int b = 10;
		double result = (activation - A)*(b-a)/(B-A) + a;
//		System.out.println(result);
		value = result / (1 + Math.abs(result));
		
	}
}
