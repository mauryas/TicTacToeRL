package de.ovgu.dke.teaching.ml.tictactoe.player;

/*
 * Provides coordinates on the board
 * 
 */
public class Coordinate {
	int x;
	int y;
	int z;
	
	public Coordinate() {
		
	}
	
	//initialize position
	public Coordinate(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	//checks if two positions are equal
	public boolean equals(Coordinate coord){
		if(this.x == coord.x && this.y == coord.y && this.z == coord.z)
			return true;
		else
			return false;
	}
	
	//prints the position in the format of (x, y, z)
	public String printCoordinate(){
		return String.format("(%s, %s, %s)", x, y, z);
	}

}
