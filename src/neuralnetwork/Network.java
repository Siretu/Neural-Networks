package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;


public class Network{
	private ArrayList<ArrayList<Neuron>> layers;
	private int maxWeight;
	private Random generator;

	public Network(){
		layers = new ArrayList<ArrayList<Neuron>>();
		maxWeight = 100;
		generator = new Random();
	}

	public Network(int maxWeight){
		this();
		this.maxWeight = maxWeight;
	}
	
	public Network(Network n){
		this();
		maxWeight = n.maxWeight;
		
		ArrayList<ArrayList<Neuron>> temp = n.getLayers();
		for (int i_ = layers.size()-1; i_ >= 0; --i_){
			ArrayList<Neuron> aln = temp.get(i_);
			for(Neuron nn : aln){
				Neuron tempneu = new Neuron(nn);
				aln.add(tempneu);
				//TODO: add links to previous layer
			}
			
		}
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
	
	public ArrayList<ArrayList<Neuron>> getLayers() {
		return layers;
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

}
