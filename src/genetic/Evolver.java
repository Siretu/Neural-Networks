package genetic;


public class Evolver {
	
	Population pop = new Population();

	
	
	public void cycle(){
		boolean nomoreloops = false;
		do{
			pop.run();
			
			pop.evolveCycle();
			
			//nomoreloops = true;
		} while(!nomoreloops);
	}
}
