package genetic;


public class Evolver {
	
	Population pop = new Population();

	
	
	public void cycle(){
		boolean nomoreloops = false;
		do{
			pop.run();
			
			pop.evolveCycle();
			System.out.println(pop.getAgents().get(0).getFitness());
			//nomoreloops = true;
		} while(!nomoreloops);
	}
}
