package main;

import java.util.ArrayList;
import java.util.HashMap;

import genetic.Evolution;
import genetic.Population;
import neuralnetwork.*;

public class NeuralTesting {

	public static void main(String[] args) throws InvalidInputLayerException {
		HashMap<ArrayList<Integer>,ArrayList<Integer>> output = new HashMap<ArrayList<Integer>,ArrayList<Integer>>();
		ArrayList<Integer> key = new ArrayList<Integer>();
		ArrayList<Integer> value = new ArrayList<Integer>();
		key.add(1);
		key.add(1);
		key.add(1);
		key.add(0);
		key.add(0);
		key.add(1);
		key.add(0);
		key.add(0);
		key.add(1);
		value.add(1);
		output.put(key, value);
		
		Population pop = new Population(9, 7, 1);
		Evolution foo = new Evolution(output,pop);
		foo.run();

	}

}
