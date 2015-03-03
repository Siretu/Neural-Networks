package neuralnetwork;

public class ConstantInput extends Neuron{
	private int value;
	
	
	public ConstantInput(double threshold) {
		super(threshold);
	}
	
	public ConstantInput(int value){
		super();
		this.value = value;
	}
	
	public ConstantInput(){
		super();
	}
	
	public void setValue(int v){
		value = v;
	}

}
