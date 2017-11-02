package de.ovgu.dke.teaching.ml.tictactoe.player;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;

public class WaknaBoysTournament implements IPlayer {
	private static boolean firstRun = true;
	public int boardDim;
	Blocks blocks;
	GameStatus priviosGameStatus;
	
	
	public WaknaBoysTournament() {
		// TODO Auto-generated constructor stub
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
			priviosGameStatus = new GameStatus(boardDim);
			priviosGameStatus.board = boardCopy.clone();
			priviosGameStatus.weights = new double[] { 0.49857967802056946, -0.09000451523151855, -0.028867572994032725, -0.18819063344565734, -0.17156826056420715, -0.0085411239221823, 0.24065925105066413, 0.2355651379856246, 0.45583427857421704, 0.48693342888178215, 0.5 };
			priviosGameStatus.parameters = blocks.getParameters(boardCopy);
			priviosGameStatus.calculateVTrain();
			firstRun = false;
		}
		
		Coordinate pos = getBestPossibleBoard(boardCopy); 
		
		// do a move using the cloned board
		try {
			boardCopy.makeMove(new Move(this, new int[] {pos.x, pos.y, pos.z}));
		} catch (IllegalMoveException e) {
			// move was not allowed
		}

		// return your final decision for your next move
		return new int[] { pos.x, pos.y, pos.z };
	}
	
	//Calculates best possible move to make
	public Coordinate getBestPossibleBoard(IBoard oldBoard){
		int boardDim = oldBoard.getSize();
		
		GameStatus newBoardState = new GameStatus(boardDim);
		
		for(int x=0;x<boardDim;x++){
			for(int y=0;y<boardDim;y++){
				for(int z=0;z<boardDim;z++){
					Coordinate p = new Coordinate(x, y, z);
					if(oldBoard.getFieldValue(new int[] {p.x, p.y, p.z}) == null){
						
						IBoard tempBoard = oldBoard.clone();
						
						GameStatus tempBoardState = new GameStatus(boardDim);
						
						
						try {
							tempBoard.makeMove(new Move(this, new int[] {x, y, z} ));
						} catch (IllegalMoveException e) {
			
							e.printStackTrace();
						}
						
						tempBoardState.board = tempBoard;
						tempBoardState.parameters = blocks.getParameters(tempBoard);
						tempBoardState.weights = priviosGameStatus.weights.clone();
						tempBoardState.calculateVTrain();
						
						if(newBoardState.board == null)
						{
							newBoardState = tempBoardState;
							newBoardState.nextMove = p;
						}
						else if(tempBoardState.vTrain >= newBoardState.vTrain){
							newBoardState = tempBoardState;
							newBoardState.nextMove = p;
						}
						
					}
				}
			}
			
		}
		
		
		priviosGameStatus = newBoardState;
		return newBoardState.nextMove;
		
	}
	
	//adjust weights once the match ends. For a win we assign +10, loss -10, and draw 0
	//The weights and Vtrain value are then written to a results files
	public void onMatchEnds(IBoard board) {
		
		return;
	}


}
