package de.ovgu.dke.teaching.ml.tictactoe.player;
import java.util.ArrayList;

/*Keep track of moves of player and opponent in a block of 5 places and it can in horizontal, 
 *vertical or diagonal direction. 
 * 
 */

public class Block {
//Continuous pos. that forms a block
	private ArrayList<Coordinate> block;
//no. of pos. taken by WaknaBoys on the block
	private int noPlayer1;
	
//no. of pos. taken by the opponent on the block 
	private int noPlayer2;
	
//no. of pos. avaliable in a block
	private int noAvbl;
	
	public Block(){
		block = new ArrayList<Coordinate>();
		noAvbl = 0;
		noPlayer1 = 0;
		noPlayer2 = 0;
	}
	
	//Adds a new position to the row
	public void addCoordinate(Coordinate coord){
		block.add(coord);
	}
	
	
	//Returns the position on the row specified by the index
	public Coordinate getCoordinate(int index){
		return block.get(index);
	}
	
	//Prints the blocks as [(bx0, by0, bz0), (bx1, by1, bz1),....]
	public String printBlock(){
		ArrayList<String> coord = new ArrayList<String>(); 
		
		for(int i=0; i<block.size(); i++){
			coord.add(block.get(i).printCoordinate());
		}
		return String.format(coord.toString());
	}

	public ArrayList<Coordinate> getBlock() {
		return block;
	}

	public void setBlock(ArrayList<Coordinate> block) {
		this.block = block;
	}

	public int getNoPlayer1() {
		return noPlayer1;
	}

	public void setNoPlayer1(int noPlayer1) {
		this.noPlayer1 = noPlayer1;
	}

	public int getNoPlayer2() {
		return noPlayer2;
	}

	public void setNoPlayer2(int noPlayer2) {
		this.noPlayer2 = noPlayer2;
	}

	public int getNoAvbl() {
		return noAvbl;
	}

	public void setNoAvbl(int noAvbl) {
		this.noAvbl = noAvbl;
	}
	
	
	

}
