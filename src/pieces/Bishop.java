package pieces;

import java.util.ArrayList;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the Bishop, every Bishop will be created with this
 * Piece.java is it's super class
 * @author Steve Flynn
 *
 */
public class Bishop extends Piece {
	
	/**
	 * Constructor for Bishop class
	 * @param location- starting location of the piece
	 * @param newColor- color of the piece. True for white, false for black
	 * @param newId- unique id of the piece
	 * @param newType- type of the piece. Should be "bishop"
	 */
	public Bishop(Tuple location, boolean newColor, String newId, String newType) {
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
		
		int x=this.getCurrentLocation().getRow() + 1;
		int y=this.getCurrentLocation().getCol() + 1;
		
		//check if piece is still alive
		if(this.isDead()) {
			this.setLocations(locations);
			return 0;
		}
		
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
	 * To string giving a text representation of the Bishop based on the color
	 */
	@Override
	public String toString() {
		if(this.getColor()) {
			return "wB";
		}
		else {
			return "bB";
		}
	}

}

