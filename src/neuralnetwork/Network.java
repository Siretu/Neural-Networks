package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;


public class Network implements Comparable<Network>{
	private ArrayList<ArrayList<Neuron>> layers;
	private int maxWeight;
	private Random generator;
	private int score;
	
	public Network(){
		layers = new ArrayList<ArrayList<Neuron>>();
		maxWeight = 100;
		generator = new Random();
		score = 0;
	}
	
	public Network(int maxWeight){
		this();
		this.maxWeight = maxWeight;
	}
	
	public void addLayer(){
		layers.add(new ArrayList<Neuron>());
	}
	
	public void addLayer(ArrayList<Neuron> layer){
		layers.add(layer);
	}
	
	public ArrayList<Neuron> getLayer(int layer){
		return layers.get(layer);
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addNeuron(int layer, Neuron n){
		while(layers.size() <= layer){
			addLayer();
		}
		layers.get(layer).add(n);
		if(layer<layers.size()-1){
			for(Neuron subnode : layers.get(layer+1)){
				n.addInput(subnode, (generator.nextFloat()-0.5)*maxWeight);
			}
		}
	}
	
	public void input(ArrayList<Integer> input) throws InvalidInputLayerException{
		// TODO: Figure out what this actually does and comment it properly.
		ArrayList<Neuron> inputLayer = layers.get(layers.size()-1);
		for(int i = 0;i<inputLayer.size();i++){
			if(inputLayer.get(i).getClass().equals(ConstantInput.class)){
				((ConstantInput)(inputLayer.get(i))).setValue(input.get(i));
			} else {
				throw new InvalidInputLayerException("");
			}
			
		}
	}
	
	public int output(int node) throws InvalidInputLayerException{
		return layers.get(0).get(node).output();
	}

	@Override
	public int compareTo(Network n) {
		return this.getScore() - n.getScore();
	}
}
