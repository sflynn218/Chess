package pieces;

import java.util.ArrayList;
import java.util.Iterator;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the king piece, every king piece will be created with this
 * Piece.java is it's super class
 * Castling is excluded from the locations field and satisfied elsewhere
 * @author Steve Flynn
 *
 */
public class King extends Piece {
	/**
	 * This boolean stores the castle state of this king
	 */
	private boolean canCastle;
	
	/**
	 * Constructor for King class
	 * @param location- starting location of the King
	 * @param newColor- color of the king
	 * @param newId- unique id of the piece
	 * @param newType- type of the piece, should always be "king"
	 */
	public King(Tuple location, boolean newColor, String newId, String newType) {
		super(location, newColor, newId, newType);
	}
	
	
	
	/**
	 * Overwritten method, finds all of the valid locations this piece can move to
	 * returns an array list of those locations
	 * Castling moves are excluded from this function and added elsewhere
	 */
	@Override
	public int resetLocations(Piece[][] board) {
		
		//local variables
		ArrayList<Tuple> locations=new ArrayList<Tuple>();
		
		//get objects location
		Tuple location=this.getCurrentLocation();
		
		//add locations
		//left/right
		locations.add(new Tuple(location.getRow(), location.getCol() + 1));
		locations.add(new Tuple(location.getRow(), location.getCol() - 1));
		
		//up/down
		locations.add(new Tuple(location.getRow()+1, location.getCol()));
		locations.add(new Tuple(location.getRow()-1, location.getCol()));
		
		//diagonal
		locations.add(new Tuple(location.getRow()+1, location.getCol()+1));
		locations.add(new Tuple(location.getRow()+1, location.getCol()-1));
		locations.add(new Tuple(location.getRow()-1, location.getCol()+1));
		locations.add(new Tuple(location.getRow()-1, location.getCol()-1));
		
		//iterate through list and remove certain Tuples
		 Iterator<Tuple> itr = locations.iterator();
	      while(itr.hasNext()) {
	         Tuple element = (Tuple)itr.next();
	         try {
	        	 if(board[element.getRow()][element.getCol()] instanceof GhostPawn) {
	        		 continue;
	        	 }
	        	 if(board[element.getRow()][element.getCol()] != null &&
	        			 board[element.getRow()][element.getCol()].getColor() == this.getColor()) {
	        		 itr.remove();
	        	 }
	         }
	         catch(ArrayIndexOutOfBoundsException exception) {
	        	 itr.remove();
	         }
	      }
	      
		this.setLocations(locations);
		return 0;
	}
	
	/**
	 * To string method that prints the king to the screen based on it's color
	 */
	public String toString() {
		if(this.getColor()) {
			return "wK";
		}
		else {
			return "bK";
		}
	}

	/**
	 * Accesses the canCastle field to show if this king can castle
	 * @return- can castle state
	 */
	public boolean canCastle() {
		return this.canCastle;
		
	}
	
	/**
	 * sets the castle flag
	 * @param x- new state of castle field
	 */
	public void setCastle(boolean x) {
		this.canCastle=x;
	}
}
