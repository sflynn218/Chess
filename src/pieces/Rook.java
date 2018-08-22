package pieces;

import java.util.ArrayList;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the Rook, every rook will be created with this
 * Extends Piece
 * @author Steve Flynn
 *
 */
public class Rook extends Piece {
	
	/**
	 * Constructor for Rook class
	 * @param location- starting location of the piece
	 * @param newColor- color of the piece. True for white, false for black
	 * @param newId- unique id of this piece
	 * @param newType- type of the piece. Should be "rook"
	 */
	public Rook(Tuple location, boolean newColor, String newId, String newType) {
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
		
		for(int i=0; i < locations.size(); i++) {
			//System.out.println(locations.get(i).toString());
		}
		
		this.setLocations(locations);
		return 0;
	}
	
	/**
	 * String representation of this piece based on the color
	 */
	public String toString() {
		if(this.getColor()) {
			return "wR";
		}
		else {
			return "bR";
		}
	}

}
