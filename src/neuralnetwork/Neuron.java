package neuralnetwork;


import java.util.ArrayList;

public class Neuron {
	
	private ArrayList<Axon> inputs;	//NEEDS to stay an ordered collection
	private double threshold;
	private double value;
	
	public Neuron(){
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
//		System.out.println(activation);
//		int A = -300;
//		int B = 300;
//		int a = -1;
//		int b = 1;
//		double result = (activation - A)*(b-a)/(B-A) + a;
		double result = activation;
//		System.out.println(result);
//		value = Math.tanh(result);
//		value = 1 + (1 + Math.pow(Math.E,-result));
		value = result / (1 + Math.abs(result));
//		value = activation;
		
	}
}
