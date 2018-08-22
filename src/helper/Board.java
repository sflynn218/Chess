package helper;

import java.util.ArrayList;

import pieces.*;

/**
 * This class stores and manages the 2D array that represents the board
 * @author Steve Flynn
 *
 */
public class Board {
	
	/**
	 * 2D array representing the board
	 */
	private Piece[][] board= new Piece[8][8];
	
	/**
	 * Constructor for board class
	 * Uses the list of pieces to inititialize the board
	 * @param pieces
	 */
	public Board(ArrayList<Piece> pieces) {
		//add piece locations to Board
		for(int i=0; i < pieces.size(); i++) {
			Piece current=pieces.get(i);
			int col=pieces.get(i).getCurrentLocation().getCol();
			int row=pieces.get(i).getCurrentLocation().getRow();
			
			switch(current.getType()) {
				case "rook":
					board[row][col]=((Rook)pieces.get(i));
					break;
				
				case "knight" :
					board[row][col]=((Knight)pieces.get(i));
					break;
				
				case "bishop" :
					board[row][col]=((Bishop)pieces.get(i));
					break;
					
				case "queen" :
					board[row][col]=((Queen)pieces.get(i));
					break;
					
				case "king" :
					board[row][col]=((King)pieces.get(i));
					break;
				
				case "pawn" :
					board[row][col]=((Pawn)pieces.get(i));
					break;
			}
		}
	}
	
	/**
	 * Gets the board
	 * @return- 2D array of the board
	 */
	public Piece[][] getBoard() {
		return board;
	}
	
	/**
	 * Moves a piece from one location on the board to another
	 * If an enemy piece is destroyed in the move that piece is returned
	 * @param newLocation- location to be moved to
	 * @param piece- piece to be moved
	 * @return- piece that was destroyed. If no piece was destroyed returns null
	 */
	public Piece move(Tuple newLocation, Piece piece) {
		Piece destroyed=null;
		
		if(board[newLocation.getRow()][newLocation.getCol()] != null) {
			destroyed=board[newLocation.getRow()][newLocation.getCol()];
			destroyed.setDead(true);
			destroyed.setCurrentLocation(new Tuple(-1, -1));
		}
		board[piece.getCurrentLocation().getRow()][piece.getCurrentLocation().getCol()]=null;
		board[newLocation.getRow()][newLocation.getCol()]=piece;
		piece.setCurrentLocation(new Tuple(newLocation.getRow(), newLocation.getCol()));
		if(!piece.getType().equals("pawn")) {
			piece.setMoved(true);
		}
		return destroyed;
	}
	
	/**
	 * A string representation of the board
	 * @return- board ready to be printed to the screen
	 */
	public String printBoard() {
		boolean currentColor= false;
		String stringBoard="";
		
		for(int i=board.length-1; i >=0; i--) {
			for(int x=0; x < board[i].length; x++) {
				
				currentColor = !currentColor;
				if(board[i][x] == null || board[i][x].getType().equals("ghostPawn")) {
					if(currentColor) {
						stringBoard=stringBoard + "  " + " ";
					}
					else {
						stringBoard=stringBoard + "##" + " ";
					}
					continue;
				}
				else {
					stringBoard=stringBoard+ board[i][x] + " ";
					continue;	
				} 
			}
			stringBoard=stringBoard+ i + "\n";
			currentColor = !currentColor;
		}
		//stringBoard=stringBoard+ " 0" + " " + " 1" + " " + " 2" + " " + " 3" + " " + " 4" + " " + " 5" + " " + " 6" + " "+ " 7";
		stringBoard= stringBoard+ " a" + " " + " b" + " " + " c" + " " + " d" + " " + " e" + " " + " f" + " " + " g" + " "+ " h";
		stringBoard=stringBoard+"\n";
		return stringBoard;
	}
	
	/**
	 * Gets a piece at a certain location
	 * returns null if spot is empty
	 * @param x- location to get the piece from
	 * @return- piece at location or null
	 */
	public Piece getPieceByLocaiton(Tuple x) {
		for(int r=0; r < board.length; r++) {
			for(int c=0; c < board[r].length; c++) {
				Piece temp=board[r][c];
				if(temp != null) {
					if(temp.getCurrentLocation().equals(x)) {
						return temp;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Checks if there is a path between two points on the board
	 * @param start- the starting point
	 * @param end- the ending point
	 * @return- list of all points between both points. Returns empty list if there are no points.
	 */
	public ArrayList<Tuple> findPath(Tuple start, Tuple end){
		ArrayList<Tuple> locations= new ArrayList<Tuple>();
		
		int startRow=start.getRow();
		int startCol=start.getCol();
		int endRow=end.getRow();
		int endCol=end.getCol();
		int x=0;
		int y=0;
		
		if(start.equals(end)) {
			return locations;
		}
		
		if(startRow == endRow) {
			if(startCol < endCol) {
				for(int i=startCol+1; i < endCol; i++) {
					if(board[startRow][i] == null) {
						locations.add(new Tuple(startRow, i));
					}
					else {
						break;
					}
				}
			}
			else if(startCol > endCol) {
				for(int i=startCol-1; i > endCol; i--) {
					if(board[startRow][i] == null) {
						locations.add(new Tuple(startRow, i));
					}
					else {
						break;
					}
				}
			}
		}
		else if(startCol == endCol) {
			if(startRow < endRow) {
				for(int i=startRow+1; i < endRow; i++) {
					if(board[i][startCol] == null) {
						locations.add(new Tuple(i, startCol));
					}
					else {
						return null;
					}
				}
			}
			else if(startRow > endRow) {
				for(int i=endRow-1; i > endRow; i--) {
					if(board[i][startCol] == null) {
						locations.add(new Tuple(i, startCol));
					}
					else {
						if(end.equals(new Tuple(x,y))) {
							locations.add(new Tuple(x,y));
						}
						else {
							return null;
						}
					}
				}
			}
		}
			
		//Diagonal movements
		//both coordinates are greater
		else if(startRow < endRow && startCol < endCol) {
			x=startRow+1;
			y=startCol+1;
			while(x <= endRow && y <= endCol) {
				if(board[x][y] != null) {
					if(end.equals(new Tuple(x,y))) {
						locations.add(new Tuple(x,y));
					}
					else {
						return null;
					}
				}
				else {
					locations.add(new Tuple(x,y));
				}
				x++;
				y++;
			}
			if(locations.contains(end)) {
				locations.remove(end);
			}
			else {
				return null;
			}
		}
		//both coordinates are less
		else if(startRow > endRow && startCol > endCol) {
			x=startRow-1;
			y=startCol-1;
			while(x >= endRow && y >= endCol) {
				if(board[x][y] == null) {
					locations.add(new Tuple(x,y));
				}
				else {
					if(end.equals(new Tuple(x,y))) {
						locations.add(new Tuple(x,y));
					}
					else {
						return null;
					}
				}
				x--;
				y--;
			}
			if(locations.contains(end)) {
				locations.remove(end);
			}
			else {
				return null;
			}
		}
		//row is greater
		else if(startRow < endRow && startCol > endCol) {
			x=startRow+1;
			y=startCol-1;
			while(x <= endRow && startCol >= endCol) {
				if(board[x][y] == null) {
					locations.add(new Tuple(x,y));
				}
				else {
					if(end.equals(new Tuple(x,y))) {
						locations.add(new Tuple(x,y));
					}
					else {
						return null;
					}
				}
				x++;
				y--;
			}
			if(locations.contains(end)) {
				locations.remove(end);
			}
			else {
				return null;
			}
		}
		
		//row is less
		else if(startRow > endRow && startCol < endCol) {
			System.out.println("test");
			x=startRow-1;
			y=startCol+1;
			while(x >= endRow && startCol <= endCol) {
				if(board[x][y] == null) {
					locations.add(new Tuple(x,y));
				}
				else {
					if(end.equals(new Tuple(x,y))) {
						locations.add(new Tuple(x,y));
					}
					else {
						return null;
					}
				}
				x--;
				y++;
			}
			if(locations.contains(end)) {
				locations.remove(end);
			}
			else {
				return null;
			}
			
		}
		return locations;
	}
}
