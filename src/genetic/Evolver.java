package genetic;


public class Evolver {
	
	Population pop;

	
	
	public void cycle(){
		boolean nomoreloops = false;
		do{
			pop.run();
			
			pop.evolveCycle();
			
			nomoreloops = true;
		}while(!nomoreloops);
	}
}
