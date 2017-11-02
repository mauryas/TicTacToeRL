package de.ovgu.dke.teaching.ml.tictactoe.player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;

//Class the helps in evaluating the board state
public class GameStatus {
	//It defines the learning rate of the game
	private double learnRate = 0.0001;
	
	//A copy of the current board
	public IBoard board;
	
	//Stores the parameters calculate x0-x9
	public int[] parameters;
	
	//Stores the weights for the state of the board w0, w1, w2, ....
	public double[] weights;
	
	//Stores the value of the training for the state of the board
	public double vTrain;
	
	//Is used to store the possible next move to played
	public Coordinate nextMove;
	
	//Initializations
	public GameStatus(int boardSize){
		parameters = new int[boardSize * 2];
		weights = new double[boardSize*2 +1];
	}
	
	//Weights adjusted according to LMS algorithm
		//w(i) = w(i) + learning rate * (VTrainOld - VTrainNew) * x(i)
		public void adjustWeights(double vTrainOld) {
			double vTrainAdj = vTrainOld - vTrain;
			weights[0] = weights[0] + learnRate * vTrainAdj;
			for(int i=0; i<parameters.length; i++){
				weights[i+1] = weights[i+1] + learnRate * vTrainAdj * parameters[i];
			}
		}
	
	//Initialize the weights. The very first time the program runs we initialize the weights to 0.5. 
	//After the end of each game we write the values of the weights and vTrain to a file as specified in the path.
	//During the next game the latest values of the weights are read from this file
	public void initialWeights(){
		Path path = Paths.get("C:\\Users\\ShivamMaurya\\Documents\\WaknaBoys_log.csv");
		File f = path.toFile();
		if(f.exists() && !f.isDirectory()){
			try {
				//Read weights from file
				List<String> lines = Files.readAllLines(path);
				String[] fields = lines.get(lines.size() - 1).split(",");
				for(int i =0; i<weights.length; i++){
					weights[i] =  Double.parseDouble(fields[i]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			//For first time initialize weights to 0.5 and create the file
			for(int i=0; i<=10; i++){
				weights[i] = 0.5D;
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//VTrain = w0 + w1x1 + w2x2 + ... + w10x10
	public void calculateVTrain(){
		vTrain = weights[0];
		for(int i=0; i<parameters.length; i++){
			vTrain += weights[i+1] * parameters[i];
		}
	}
	
	
	
}
