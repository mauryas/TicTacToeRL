package de.ovgu.dke.teaching.ml.tictactoe.player;

import java.io.*;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;

/**
 * WaknaBoys Learning Player
 * 
 * @author Shivam Maurya, Mukul Salhotra, Gaurav Sharma
 */
public class WaknaBoysLearner implements IPlayer {
	
	public int boardDim;
	public static boolean firstRun = true;
	Blocks blocks;
	GameStatus previosGameStatus;
	
	public WaknaBoysLearner(){
		
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return "WaknaBoys"; 
	}

	public int[] makeMove(IBoard board) {
		
	// create a clone of the board that can be modified
	IBoard boardCopy = board.clone();
				
	if(firstRun == true){
		boardDim = board.getSize();
		blocks = new Blocks(boardCopy);
		
		//Initializing weights
		previosGameStatus = new GameStatus(boardDim);
		previosGameStatus.board = boardCopy.clone();
		previosGameStatus.initialWeights();
		previosGameStatus.parameters = blocks.getParameters(boardCopy);
		previosGameStatus.calculateVTrain();
		firstRun = false;
	}
	
	Coordinate coord = getBestPossibleBoard(boardCopy); 
	
	Coordinate coord2=blocks.checkLoosingAndWinningCondition(boardCopy);
	if((coord2.x==-1)&&(coord2.y==-1)&&(coord2.z==-1))
	{
	// do a move using the cloned board
	try {
		boardCopy.makeMove(new Move(this, new int[] {coord.x, coord.y, coord.z}));
	} catch (IllegalMoveException e) {
	// move was not allowed
	}
	return new int[] { coord.x, coord.y, coord.z };
	}
	else
	{
		try {
			boardCopy.makeMove(new Move(this, new int[] {coord2.x, coord2.y, coord2.z}));
		} catch (IllegalMoveException e) {
		// move was not allowed
		}
		return new int[] { coord2.x, coord2.y, coord2.z };
	}
	// return your final decision for your next move
	
	}
	//Calculates best possible move to make
	public Coordinate getBestPossibleBoard(IBoard oldBoard){
		int boardSize = oldBoard.getSize();
		
		GameStatus newBoardState = new GameStatus(boardSize);
			
		for(int x=0;x<boardSize;x++){
			for(int y=0;y<boardSize;y++){
				for(int z=0;z<boardSize;z++){
					Coordinate c = new Coordinate(x, y, z);
					if(oldBoard.getFieldValue(new int[] {c.x, c.y, c.z}) == null){
						
						IBoard tempBoard = oldBoard.clone();
						
						GameStatus tempBoardState = new GameStatus(boardSize);
						
						
						try {
							tempBoard.makeMove(new Move(this, new int[] {x, y, z} ));
						} catch (IllegalMoveException e) {
			
								e.printStackTrace();
						}
							
						tempBoardState.board = tempBoard;
						tempBoardState.parameters = blocks.getParameters(tempBoard);
						tempBoardState.weights = previosGameStatus.weights.clone();
						tempBoardState.calculateVTrain();
						
						if(newBoardState.board == null)
						{
							newBoardState = tempBoardState;
							newBoardState.nextMove = c;
						}
						else if(tempBoardState.vTrain >= newBoardState.vTrain){
							newBoardState = tempBoardState;
							newBoardState.nextMove = c;
						}
						
					}
				}
			}
				
		}
		
		newBoardState.adjustWeights(previosGameStatus.vTrain);
		previosGameStatus = newBoardState;
		return newBoardState.nextMove;	
	}
	//adjust weights once the match ends. For a win we assign +10, loss -10, and draw 0
	//The weights and Vtrain value are then written to a results files
	public void onMatchEnds(IBoard board) {
		GameStatus finalBoardState = new GameStatus(board.getSize());
		finalBoardState.board = board.clone();
		finalBoardState.weights = previosGameStatus.weights.clone();
		finalBoardState.parameters = blocks.getParameters(board.clone());
		
		IPlayer winner = board.getWinner();
			
		if(winner == null)
			finalBoardState.vTrain = 0;
		else if(winner.getName() == "WaknaBoys")
			finalBoardState.vTrain = 10;
		else
			finalBoardState.vTrain = -10;
			
		finalBoardState.adjustWeights(previosGameStatus.vTrain);
		int BoardSize=board.getSize();
		BufferedWriter output = null;
		File file = new File("C:\\Users\\ShivamMaurya\\Documents\\WaknaBoys_log.csv" );
		try {
		   output = new BufferedWriter(new FileWriter(file, true));
		   
		   StringBuffer TempString = new StringBuffer();
		   
		   for(int i=0;i<=BoardSize*2;i++)
		   {
			   TempString.append(String.format("%s, ",finalBoardState.weights[i]));
			   if(i==BoardSize*2)
				   TempString.append(finalBoardState.vTrain+"\r\n");
		   }
		   /*
	       String text = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s \r\n", 
	       		finalBoardState.weights[0],
	       		finalBoardState.weights[1],
	       		finalBoardState.weights[2],
	       		finalBoardState.weights[3],
	       		finalBoardState.weights[4],
	      		finalBoardState.weights[5],
	       		finalBoardState.weights[6],
	       		finalBoardState.weights[7],
	       		finalBoardState.weights[8],
	       		finalBoardState.weights[9],
	       		finalBoardState.weights[10],
	       		finalBoardState.vTrain);
		*/
			output.write(TempString.toString());
			output.close();
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
