package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

//import genetic.Evolution;
//import genetic.Population;
import neuralnetwork.*;
import environment.*;

public class NeuralTesting {
	private static final String HISTORY_FILENAME = "history";

	public static void main(String[] args) throws InvalidInputLayerException {
		World w = new World();
		System.out.println(Arrays.toString(parseHistory()));
		//TODO: Neural Testing
	}
	
	public static double[] parseHistory() {
		ArrayList<Double> result = new ArrayList<Double>();
		try {
			Scanner in = new Scanner(new FileReader(HISTORY_FILENAME));
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] segments = line.split(",");
				result.add(Double.parseDouble(segments[2]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO: Handle file not found
			System.err.println("File not found: "+HISTORY_FILENAME);
			e.printStackTrace();
		}
		double[] result2 = new double[result.size()];
		for (int i = 0; i < result.size(); i++) {
			result2[i] = result.get(i);
		}
		return result2;
		
	}

}
