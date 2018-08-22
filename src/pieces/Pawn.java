package pieces;

import java.util.ArrayList;
import java.util.Iterator;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the Pawn, every Pawn will be created with this
 * Extends Piece
 * 
 * @author Steve Flynn
 *
 */
public class Pawn extends Piece {
	/**
	 * set true if it is possible this piece can be promoted
	 */
	private boolean promotion;
	
	/**
	 * set true if this piece can have the en passant rule used against it
	 */
	private boolean enPassantPossible;

	/**
	 * Constructor for the Pawn class
	 * @param location- starting location of the piece
	 * @param newColor- color of the piece. True for white, false for black
	 * @param newId- unique id of the piece
	 * @param newType- type of the piece. Should be "pawn"
	 */
	public Pawn(Tuple location, boolean newColor, String newId, String newType) {
		super(location, newColor, newId, newType);
		promotion=false;
	}
	
	
	
	/**
	 * Overwritten method, finds all of the valid locations this piece can move to
	 * returns an array list of those locations
	 */
	@Override
	public int resetLocations(Piece[][] board) {
		
		//promotion check
		if(this.getColor()) {
			if(this.getCurrentLocation().getRow()== 7) {
				this.setPromotion(true);
			}
		}
		else {
			if(this.getCurrentLocation().getRow() == 0) {
				this.setPromotion(true);
			}
		}
		//local variables
		ArrayList<Tuple> locations=new ArrayList<Tuple>();
		
		//get objects location
		int row=this.getCurrentLocation().getRow();
		int col=this.getCurrentLocation().getCol();
		
		//check if piece is still alive
		if(this.isDead()) {
			this.setLocations(locations);
			return 0;
		}
		
		if(this.getColor()) { 
			if(!this.hasMoved()) {
				if(board[row+2][col] == null && board[row+1][col] == null) {
					locations.add(new Tuple(row + 2, col));
					this.setEnPassantPossible(true);
				}
			}
			if(row + 1 < board.length) {
				if(board[row+1][col] == null) {
					locations.add(new Tuple(row + 1, col));
				}
			}
			
			if(row + 1 < board.length && col - 1 >= 0) {
				Piece temp= board[row + 1][col-1];
				if(temp!= null && !temp.getColor()) {
					locations.add(new Tuple(row + 1, col - 1));
				}
			}
			
			if(row + 1 < board.length && col + 1 < board.length) {
				Piece temp= board[row + 1][col+1];
				if(temp != null) {
					if(!temp.getColor()) {
						locations.add(new Tuple(row + 1, col + 1));
					}
				}
			}
		}
		else {
			if(!this.hasMoved()) {
				if(board[row-2][col] == null && board[row-1][col] == null) {
					locations.add(new Tuple(row - 2, col));
				}
			}
			if(row - 1 >= 0) {
				if(board[row-1][col] == null) {
					locations.add(new Tuple(row - 1, col));
				}
			}
			
			if(row - 1 >= 0 && col - 1 >= 0) {
				Piece temp= board[row - 1][col-1];
				if(temp != null && this.getColor()!=temp.getColor()) {
					locations.add(new Tuple(row - 1, col - 1));
				}
			}
			
			if(row - 1 >= 0 && col + 1 < board.length) {
				Piece temp= board[row - 1][col+1];
				if(temp != null && this.getColor()!=temp.getColor()) {
					locations.add(new Tuple(row - 1, col + 1));
				}
			}
		}
		
		this.setLocations(locations);
		
		return 0;
	}
	
	/**
	 * String representation of the piece based on color
	 */
	@Override
	public String toString() {
		if(this.getColor()) {
			return "wp";
		}
		else {
			return "bp";
		}
	}
	
	/**
	 * Sets the promotion state of this pawn
	 * @param x- the new state
	 */
	public void setPromotion(boolean x) {
		this.promotion=x;
	}
	
	/**
	 * Checks if this piece can be promoted
	 * @return- state of promotion
	 */
	public boolean canPromote() {
		return this.promotion;
	}

	/**
	 * Checks whether it is possible for this piece to have enPassant used against it.
	 * Meaning it has just moved up two spaces
	 * @return- state of enpassant
	 */
	public boolean isEnPassantPossible() {
		return enPassantPossible;
	}
	
	/**
	 * Sets the state of enPassant
	 * @param enPassantPossible- the new state of en Passant
	 */
	public void setEnPassantPossible(boolean enPassantPossible) {
		this.enPassantPossible = enPassantPossible;
	}

}
