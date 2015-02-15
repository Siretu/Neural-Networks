package environment;

import java.util.ArrayList;

public class World {
	public static final int nrStocks = 20;
	public static final int historySize = 100;
	
	public double[][] stockHistory;
	
	public World() {
		System.out.println("Let there be light.");
		System.out.println("And it was still dark. You were likely to be eaten by a grue.");
		System.out.println("Let there be stocks.");
		stockHistory = new double[nrStocks][historySize];
		System.out.println("And there were stocks");
		
	}
	
}
