package de.ovgu.dke.teaching.ml.tictactoe.player;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;



public class Blocks {
	public ArrayList<Block> blocks;
	private static final Logger logger = LoggerFactory.getLogger(Blocks.class);
	public Blocks(IBoard board){
		blocks = new ArrayList<Block>();
		findBlocks(board.getSize());
	}
	
	//Init
	public Blocks(int boardSize){
		blocks = new ArrayList<Block>();
		findBlocks(boardSize);
	}	
	//Adds a new row to the collection of rows
	public void addBlock(Block block){
			blocks.add(block);
		}
	
	//Prints all the rows identified by the program
	public void printBlocks(){
		for(int i=0; i<blocks.size(); i++){
			System.out.println(blocks.get(i).printBlock());
		}
	}
	
	//Finds the values of the parameters used for learning
	//x1: no of rows that contain 1 X and remaining positions empty
	//x2: no of rows that contain 1 0 and remaining positions empty
	//x3: no of rows that contain 2 X and remaining positions empty
	//x4: no of rows that contain 2 0 and remaining positions empty
	//x5: no of rows that contain 3 X and remaining positions empty
	//x6: no of rows that contain 3 0 and remaining positions empty
	//x7: no of rows that contain 4 X and remaining positions empty
	//x8: no of rows that contain 4 0 and remaining positions empty
	//x9: no of rows that contain 5 X and remaining positions empty
	//x10: no of rows that contain 5 0 and remaining positions empty
	public int[] getParameters(IBoard board){
		int[] x = new int[board.getSize() * 2];
		for(int blockIndex=0; blockIndex<blocks.size(); blockIndex++){
			Block block = blocks.get(blockIndex);
			block.setNoAvbl(0);
			block.setNoPlayer1(0);
			block.setNoPlayer2(0);
			//block.noPlayer1 = block.noPlayer2 = block.noAvbl = 0;
			for(int coordIndex = 0; coordIndex < board.getSize(); coordIndex++){
				Coordinate coord = block.getCoordinate(coordIndex);
				IPlayer player = board.getFieldValue(new int[] {coord.x, coord.y, coord.z});
				if(player == null)
				{
					block.setNoAvbl(block.getNoAvbl()+1);
				}
					//block.noAvbl++;
				else{
					String playerName = player.getName();
					if(playerName == "WaknaBoys")
					{
						block.setNoPlayer1(block.getNoPlayer1()+1);
						//block.noPlayer1++;
					}
					else
					{
						block.setNoPlayer2(block.getNoPlayer2()+1);
						//block.noPlayer2++;
					}
				}
				
			}
			
			if(block.getNoPlayer1() == 1 && block.getNoAvbl() == 4)
				x[0]++;
			if(block.getNoPlayer2() == 1 && block.getNoAvbl() == 4)
				x[1]++;
			
			if(block.getNoPlayer1() == 2 && block.getNoAvbl() == 3)
				x[2]++;
			if(block.getNoPlayer2() == 2 && block.getNoAvbl() == 3)
				x[3]++;
			
			if(block.getNoPlayer1() == 3 && block.getNoAvbl() == 2)
				x[4]++;
			if(block.getNoPlayer2() == 3 && block.getNoAvbl() == 2)
				x[5]++;
			
			if(block.getNoPlayer1() == 4 && block.getNoAvbl() == 1)
				x[6]++;
			if(block.getNoPlayer2() == 4 && block.getNoAvbl() == 1)
				x[7]++;
				
			if(block.getNoPlayer1() == 5 && block.getNoAvbl() == 0)
				x[8]++;
			if(block.getNoPlayer2() == 5 && block.getNoAvbl() == 0)
				x[9]++;
		}
			
		return x;
	}
	
	public Coordinate checkLoosingAndWinningCondition(IBoard board){
		Coordinate coordinate = new Coordinate(-1,-1,-1);
		for(int blockIndex=0; blockIndex<blocks.size(); blockIndex++){
			Block block = blocks.get(blockIndex);
			block.setNoAvbl(0);
			block.setNoPlayer1(0);
			block.setNoPlayer2(0);
			for(int coordIndex = 0; coordIndex < board.getSize(); coordIndex++){
				Coordinate coord = block.getCoordinate(coordIndex);
				IPlayer player = board.getFieldValue(new int[] {coord.x, coord.y, coord.z});
				if(player == null)
				{
					block.setNoAvbl(block.getNoAvbl()+1);
				}
					//block.noAvbl++;
				else{
					String playerName = player.getName();
					if(playerName == "WaknaBoys")
					{
						block.setNoPlayer1(block.getNoPlayer1()+1);
						if(block.getNoPlayer1()==4 && block.getNoAvbl()==1)
						{
							
							ArrayList<Coordinate> tempBlock = new ArrayList<Coordinate>();
							tempBlock=block.getBlock();
							for(int j=0;j<tempBlock.size();j++)
							{
								int[] pos = new int[]{tempBlock.get(j).x, tempBlock.get(j).y, tempBlock.get(j).z};
								IPlayer playertemp=board.getFieldValue(pos);
								if(playertemp==null)
								{
									logger.info("Found contineous 4 for player1="+pos[0]+","+pos[1]+","+pos[2]);
									coordinate.x=pos[0];
									coordinate.y=pos[1];
									coordinate.z=pos[2];
								}
							}
							
							return coordinate;
						}
						//block.noPlayer1++;
					}
					else
					{
						block.setNoPlayer2(block.getNoPlayer2()+1);
						if(block.getNoPlayer2()==4 && block.getNoAvbl()==1)
						{ 
							
							ArrayList<Coordinate> tempBlock = new ArrayList<Coordinate>();
							tempBlock=block.getBlock();
							for(int j=0;j<tempBlock.size();j++)
							{
								int[] pos = new int[]{tempBlock.get(j).x, tempBlock.get(j).y, tempBlock.get(j).z};
								IPlayer playertemp=board.getFieldValue(pos);
								if(playertemp==null)
								{
									logger.info("Found contineous 4 for player2="+pos[0]+","+pos[1]+","+pos[2]);
									coordinate.x=pos[0];
									coordinate.y=pos[1];
									coordinate.z=pos[2];
								}
							}
							
							return coordinate;
						}

					}
				}
				
			}

		}
			
		return coordinate;
	}
	
	//Identify all the possible blocks that can be formed in a 3D board
	//Need further improvement: Remove duplicate rows. It should be ok for now as everytime any calculation is done we consider the duplicates
	private void findBlocks(int boardSize) {
		
		//keeping x constant
		for(int x=0; x<boardSize; x++){
			//adding horizontal and vertical rows
			for(int y=0; y<boardSize; y++){
				Block horizontal = new Block();
				Block vertical = new Block();
					for(int z=0; z<boardSize; z++){
					horizontal.addCoordinate(new Coordinate(x, y, z));
					vertical.addCoordinate(new Coordinate(x, z, y));
				}
				addBlock(horizontal);
				addBlock(vertical);
			}
			
			//adding diagonals
			Block diagonal1 = new Block();
			for(int i=0; i<boardSize; i++){
				diagonal1.addCoordinate(new Coordinate(x, i, i));
			}
			
			Block diagonal2 = new Block();
			for(int i=0, j=boardSize-1; i<boardSize && j>=0; i++, j--){
				diagonal2.addCoordinate(new Coordinate(x, i, j));
			}
			
			addBlock(diagonal1);
			addBlock(diagonal2);
		}
		
		//keeping y constant
		for(int y=0; y<boardSize; y++){
			//adding horizontal rows
			for(int x=0; x<boardSize; x++){
				Block horizontal = new Block();
				Block vertical = new Block();
					for(int z=0; z<boardSize; z++){
					horizontal.addCoordinate(new Coordinate(x, y, z));
					vertical.addCoordinate(new Coordinate(z, y, x));
				}
				addBlock(horizontal);
				addBlock(vertical);
			}
		
			//adding diagonals
			Block diagonal1 = new Block();
			for(int i=0; i<boardSize; i++){
				diagonal1.addCoordinate(new Coordinate(i, y, i));
			}
			
			Block diagonal2 = new Block();
			for(int i=0, j=boardSize-1; i<boardSize && j>=0; i++, j--){
				diagonal2.addCoordinate(new Coordinate(i, y, j));
			}
			
			addBlock(diagonal1);
			addBlock(diagonal2);
		}
		
		
		//keeping z constant
		for(int z=0; z<boardSize; z++){
			//adding horizontal rows
			for(int x=0; x<boardSize; x++){
				Block horizontal = new Block();
				Block vertical = new Block();
					for(int y=0; y<boardSize; y++){
					horizontal.addCoordinate(new Coordinate(x, y, z));
					vertical.addCoordinate(new Coordinate(y, x, z));
				}
				addBlock(horizontal);
				addBlock(vertical);
			}
			
			//adding diagonals
			Block diagonal1 = new Block();
			for(int i=0; i<boardSize; i++){
				diagonal1.addCoordinate(new Coordinate(i, i, z));
			}
			
			Block diagonal2 = new Block();
			for(int i=0, j=boardSize-1; i<boardSize && j>=0; i++, j--){
				diagonal2.addCoordinate(new Coordinate(i, j, z));
			}
			
			addBlock(diagonal1);
			addBlock(diagonal2);
		}
		
		//for diagonal planes
		for(int x = 0, y =0; x < boardSize && y < boardSize; x++, y++){
			Block block = new Block();
			for(int z=0; z<boardSize; z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int z=0; z<boardSize; z++){
			Block block = new Block();
			for(int x = 0, y =0; x < boardSize && y < boardSize; x++, y++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int x = 0, z =0; x < boardSize && z < boardSize; x++, z++){
			Block block = new Block();
			for(int y=0; y<boardSize; y++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int y=0; y<boardSize; y++){
			Block block = new Block();
			for(int x = 0, z =0; x < boardSize && z < boardSize; x++, z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int y = 0, z =0; y < boardSize && z < boardSize; y++, z++){
			Block block = new Block();
			for(int x=0; x<boardSize; x++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int x=0; x<boardSize; x++){
			Block block = new Block();
			for(int y = 0, z =0; y < boardSize && z < boardSize; y++, z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		
		
		for(int x = 4, y =0; x >= 0 && y < boardSize; x--, y++){
			Block block = new Block();
			for(int z=0; z<boardSize; z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int z=0; z<boardSize; z++){
			Block block = new Block();
			for(int x = 4, y =0; x >= 0 && y < boardSize; x--, y++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int x = 4, z =0; x >= 0 && z < boardSize; x--, z++){
			Block block = new Block();
			for(int y=0; y<boardSize; y++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int y=0; y<boardSize; y++){
			Block block = new Block();
			for(int x = 4, z =0; x >= 0 && z < boardSize; x--, z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int y = 4, z =0; y >= 0 && z < boardSize; y--, z++){
			Block block = new Block();
			for(int x=0; x<boardSize; x++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		for(int x=0; x<boardSize; x++){
			Block block = new Block();
			for(int y = 4, z =0; y >= 0 && z < boardSize; y--, z++){
				block.addCoordinate(new Coordinate(x, y, z));
			}
			addBlock(block);
		}
		
		//Diagonals
		
		Block d1 = new Block();
		Block d2 = new Block();
		Block d3 = new Block();
		Block d4 = new Block();
		
		for(int i=0;i<=4;i++)
		{
			d1.addCoordinate(new Coordinate(i, i, i));
		}
		
		addBlock(d1);
		
		d2.addCoordinate(new Coordinate(0, 0, 4));
		d2.addCoordinate(new Coordinate(1, 1, 3));
		d2.addCoordinate(new Coordinate(2, 2, 2));
		d2.addCoordinate(new Coordinate(3, 3, 1));
		d2.addCoordinate(new Coordinate(4, 4, 0));
		addBlock(d2);
		
		d3.addCoordinate(new Coordinate(4, 0, 0));
		d3.addCoordinate(new Coordinate(3, 1, 1));
		d3.addCoordinate(new Coordinate(2, 2, 2));
		d3.addCoordinate(new Coordinate(1, 3, 3));
		d3.addCoordinate(new Coordinate(0, 4, 4));
		addBlock(d3);
		
		d4.addCoordinate(new Coordinate(4,0,4));
		d4.addCoordinate(new Coordinate(3,1,3));
		d4.addCoordinate(new Coordinate(2,2,2));
		d4.addCoordinate(new Coordinate(1,3,1));
		d4.addCoordinate(new Coordinate(0,4,0));
		addBlock(d4);

	}
	

}
