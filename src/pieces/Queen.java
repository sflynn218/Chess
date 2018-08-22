package pieces;

import java.util.ArrayList;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the Queen piece, every queen will be created with this class
 * Piece.java is it's super class
 * @author Steve Flynn
 *
 */
public class Queen extends Piece {
	
	/**
	 * Constructor for Queen class
	 * @param location- starting location of the queen
	 * @param newColor- color of the queen, true for white, false for black
	 * @param newId- unique id of the piece
	 * @param newType- type of the piece.  Should always be "queen"
	 */
	public Queen(Tuple location, boolean newColor, String newId, String newType) {
		super(location, newColor, newId, newType);
	}
	
	/**
	 * Overwritten method, finds all of the valid locations this piece can move to
	 * returns an array list of those locations
	 */
	@Override
	public int resetLocations(Piece[][] board) {
		//local variables
		ArrayList<Tuple> locations=new ArrayList<Tuple>();
		
		//get objects location
		Tuple location=this.getCurrentLocation();
		
		//check if piece is still alive
		if(this.isDead()) {
			this.setLocations(locations);
			return 0;
		}
		
		//row
		//check left
		for(int i=location.getCol()-1; i >= 0; i--) {
			if(board[location.getRow()][i]== null) {
				locations.add(new Tuple(location.getRow(), i));
			}
			else {
				Piece blocking= board[location.getRow()][i];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(location.getRow(), i));
					continue;
				}
				
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(location.getRow(), i));
					break;
				}
				else
					break;
			}
		}
		//check right
		for(int i=location.getCol()+1; i < board.length; i++) {
			if(board[location.getRow()][i]== null) {
				locations.add(new Tuple(location.getRow(), i));
			}
			else {
				Piece blocking= board[location.getRow()][i];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(location.getRow(), i));
					continue;
				}
				
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(location.getRow(), i));
					break;
				}
				else
					break;
			}
		}
		
		//check up
		for(int i=location.getRow()+1; i < board.length; i++) {
			if(board[i][location.getCol()]== null) {
				locations.add(new Tuple(i, location.getCol()));
			}
			else {
				Piece blocking= board[i][location.getCol()];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(i, location.getCol()));
					continue;
				}
				
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(i, location.getCol()));
					break;
				}
				else
					break;
			}
		}
				
		//check down
		for(int i=location.getRow()-1; i >=0; i--) {
			if(board[i][location.getCol()]== null) {
				locations.add(new Tuple(i, location.getCol()));
			}
			else {
				Piece blocking= board[i][location.getCol()];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(i, location.getCol()));
					continue;
				}
				
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(i, location.getCol()));
					break;
				}
				else
					break;
			}
		}
		int x=this.getCurrentLocation().getRow() + 1;
		int y=this.getCurrentLocation().getCol() + 1;
		
		while(x < board.length && y < board.length) {
			if(board[x][y]== null) {
				locations.add(new Tuple(x,y));
			}
			else {
				Piece blocking= board[x][y];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(x,y));
					x++;
					y++;
					continue;
				}
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(x,y));
					break;
				}
				else {
					break;
				}
			}
			x++;
			y++;
		}
		
		x=this.getCurrentLocation().getRow() - 1;
		y=this.getCurrentLocation().getCol() - 1;
		
		while(x >= 0 && y >= 0 ) {
			if(board[x][y]== null) {
				locations.add(new Tuple(x,y));
			}
			else {
				Piece blocking= board[x][y];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(x,y));
					x--;
					y--;
					continue;
				}
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(x,y));
					break;
				}
				else {
					break;
				}
			}
			x--;
			y--;
		}
		
		x=this.getCurrentLocation().getRow() - 1;
		y=this.getCurrentLocation().getCol() + 1;
		
		while(x >= 0 && y < board.length) {
			if(board[x][y]== null) {
				locations.add(new Tuple(x,y));
			}
			else {
				Piece blocking= board[x][y];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(x,y));
					x--;
					y++;
					continue;
				}
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(x,y));
					break;
				}
				else {
					break;
				}
			}
			x--;
			y++;
		}
		
		x=this.getCurrentLocation().getRow() + 1;
		y=this.getCurrentLocation().getCol() - 1;
		while(x < board.length && y >= 0) {
			if(board[x][y]== null) {
				locations.add(new Tuple(x,y));
			}
			else {
				Piece blocking= board[x][y];
				if(blocking instanceof GhostPawn) {
					locations.add(new Tuple(x,y));
					x++;
					y--;
					continue;
				}
				if(this.getColor() != blocking.getColor()) {
					locations.add(new Tuple(x,y));
					break;
				}
				else {
					break;
				}
			}
			x++;
			y--;
		}
		
		this.setLocations(locations);
		return 0;
	}
	
	/**
	 * to string method that prints a text representation of this piece based of the color
	 */
	@Override
	public String toString() {
		if(this.getColor()) {
			return "wQ";
		}
		else {
			return "bQ";
		}
	}

}
