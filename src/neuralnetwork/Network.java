package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;

import environment.World;


public class Network{
	private ArrayList<ArrayList<Neuron>> layers;
	private int maxWeight;
	

	public Network(){
		layers = new ArrayList<ArrayList<Neuron>>();
		maxWeight = 1;
		for (int i = 0; i <= World.INPUT_SIZE;++i) {
			addNeuron(3, new Neuron());
		}
		for (int i = 0; i < 10 ;++i) {
			addNeuron(2, new Neuron());
		}
		for (int i = 0; i < 10 ;++i) {
			addNeuron(1, new Neuron());
		}
		addNeuron(0, new Neuron());
//		addNeuron(0, new Neuron());

	}

	public Network(int maxWeight){
		this();
		this.maxWeight = maxWeight;
	}
	
	/**
	 * Copy constructor - deep copies the given network.
	 * 
	 * The created network should be 'safe', i.e. not contain
	 * any references to the copied network.
	 */
	public Network(Network n){
		this();
		maxWeight = n.maxWeight;
		
		ArrayList<ArrayList<Neuron>> temp = n.getLayers();
		for (int i_ = layers.size()-1; i_ >= 0; --i_){
			ArrayList<Neuron> aln = temp.get(i_);
			for(Neuron nn : aln){
				Neuron tempneu = new Neuron(nn);
				//aln.add(tempneu);
				copyNeuronLinksToLayer(tempneu, nn, i_+1);
			}
		}
	}
	
	/**
	 * Helper function to link a given neuron to the
	 * given layer, using the weights of the old neuron.
	 * 
	 * Assumes that the layer in question has exactly the 
	 * same number of neurons as the corresponding layer
	 * in the copied network.
	 */
	private void copyNeuronLinksToLayer(Neuron active, Neuron old, int layer){
		if(layer >= layers.size()) return;
		ArrayList<Neuron> linkto = layers.get(layer);
		int i = 0;
		for(Axon ax : old.getInputs()){
			active.addInput(linkto.get(i), ax.getWeight());
			++i;
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
				n.addInput(subnode, (Math.random()-0.5)*maxWeight);
			}
		}
	}

	public void input(ArrayList<Double> input) {
		ArrayList<Neuron> inputLayer = layers.get(layers.size()-1);
		for(int i = 0;i<inputLayer.size();i++){
			inputLayer.get(i).setValue(input.get(i));
		}

		for (int i = layers.size() - 2; i >= 0; --i) {
			for (int j = 0; j < layers.get(i).size(); ++j) {
				layers.get(i).get(j).setValue();
			}
		}
	}

	public double output(int node) {
		return layers.get(0).get(node).output();
	}

}
