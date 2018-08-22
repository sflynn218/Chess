package pieces;

import java.util.ArrayList;
import helper.Tuple;

/**
 * Manages the pieces. Contains an arrayList of pieces and manages that list
 * @author Steve Flynn
 *
 */

public class PieceManager {
	/**
	 * Stores the number of new pieces that this class created. This is used for creating the piece ID's
	 */
	private int newPieceCounter=0;
	
	/**
	 * White king. The king for the white player
	 */
	private King whiteKing;
	
	/**
	 * Black king. The king for the black player.
	 */
	private King blackKing;
	
	/**
	 * Array list of pieces
	 * Includes every piece even if they have been captured and Ghost pawns
	 */
	private ArrayList<Piece> pieces=new ArrayList<Piece>();
	
	/**
	 * Constructor for PieceManager class
	 * creates all the pieces and adds them to the list
	 */
	public PieceManager() {
		//create kings
		whiteKing=new King(new Tuple(0,4), true, "kw", "king");
		blackKing=new King(new Tuple(7,4), false, "kb", "king");
		pieces.add(whiteKing);
		pieces.add(blackKing);
		
		//create rooks
		pieces.add(new Rook(new Tuple(0, 0), true, "rw1", "rook"));
		pieces.add(new Rook(new Tuple(0, 7), true, "rw2", "rook"));

		pieces.add(new Rook(new Tuple(7, 0), false, "rb1", "rook"));
		pieces.add(new Rook(new Tuple(7, 7), false, "rb2", "rook"));
		
		//create knights
		pieces.add(new Knight(new Tuple(0, 1), true, "kw1", "knight"));
		pieces.add(new Knight(new Tuple(0, 6), true, "kw2", "knight"));
		pieces.add(new Knight(new Tuple(7, 1), false, "kb1", "knight"));
		pieces.add(new Knight(new Tuple(7, 6), false, "kb2", "knight"));
		
		//create bishops
		pieces.add(new Bishop(new Tuple(0, 2), true, "bw1", "bishop"));
		pieces.add(new Bishop(new Tuple(0, 5), true, "bw2", "bishop"));
		pieces.add(new Bishop(new Tuple(7, 2), false, "bb1", "bishop"));
		pieces.add(new Bishop(new Tuple(7, 5), false, "bb2", "bishop"));
		
		//create queens
		pieces.add(new Queen(new Tuple(0, 3), true, "qw", "queen"));
		pieces.add(new Queen(new Tuple(7, 3), false, "qb", "queen"));
		
		//create white Pawns
		for(int p=0; p < 8; p++) {
			pieces.add(new Pawn(new Tuple(1, p), true, "pw" + (p+1), "pawn"));
		}
		
		//create black pawns
		for(int p=0; p < 8; p++) {
			pieces.add(new Pawn(new Tuple(6, p), false, "pb" + (p+1), "pawn"));
		}
	}
	
	/**
	 * Gets the white king
	 * @return- white king
	 */
	public King getWhiteKing() {
		return whiteKing;
	}
	
	/**
	 * Gets the black king
	 * @return- black king
	 */
	public King getBlackKing() {
		return blackKing;
	}
	
	/**
	 * Gets the list of pieces
	 * @return- list of all of the pieces
	 */
	public ArrayList<Piece> getList(){
		return this.pieces;
	}
	
	/**
	 * Iterates through all of the pieces and resets their avaliable locations
	 * @param board
	 */
	public void resetAllLocations(Piece[][] board) {
		for(int i=0; i<pieces.size(); i++) {
			pieces.get(i).resetLocations(board);
		}
	}
	
	/**
	 * Adds a piece to the list
	 * @param x- the piece to add
	 */
	public void add(Piece x) {
		this.pieces.add(x);
	}
	
	/**
	 * Remove a piece from the list
	 * @param x- the piece to remove
	 */
	public void remove(Piece x) {
		this.pieces.remove(x);
	}
	
	/**
	 * Uses the board and current turn to determine if the enemy is in check
	 * @param board- 2D array representing the board
	 * @param turn- boolean representing current player
	 * @return- true if the king is in check, false if not
	 */
	public boolean isEnemyChecked(Piece[][] board, boolean turn) {
		King king=null;
		
		
		if(turn) {
			king=(King)this.pieces.get(1);
		}
		else {
			king=(King)this.pieces.get(0);
		}
		for(int i=0; i < this.pieces.size(); i++) {
			Piece temp=pieces.get(i);
			if(temp.getType().equals("ghostPawn")) {
				continue;
			}
			if(temp.isDead()) {
				continue;
			}
			if(temp.getId().equals(king.getId()) || temp.getColor() == king.getColor()) {
				continue;
			}
			if(temp.getLocations().contains(king.getCurrentLocation())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the number of checks of the current player's king
	 * @param board- 2D array representing board
	 * @param turn- current players turn
	 * @return- number of enemy pieces attacking the current player's king
	 */
	public int getNumberOfChecks(Piece[][] board, boolean turn) {
		int number=0;
		King king=null;
		
		//find king
		if(turn) {
			king=(King)this.pieces.get(0);
		}
		else {
			king=(King)this.pieces.get(1);
		}
		
		//iterate through all of the pieces and see if it is accaking king
		for(int i=0; i < this.pieces.size(); i++) {
			Piece temp=pieces.get(i);
			if(temp.isDead()) {
				continue;
			}
			if(temp.getId().equals(king.getId()) || temp.getColor() == king.getColor()) {
				continue;
			}
			//if attacking current king increment number
			if(temp.getLocations().contains(king.getCurrentLocation())) {
				number++;
			}
		}
		return number;
	}
	
	/**
	 * Helper function used to determine if a queen side castle is possible
	 * @param king- king that castling is being determined on
	 * @param board- 2D array representing board
	 * @param newLocation- the new location the player selected in input. The location they are requesting to move to
	 * @return- true if a queen side castle is possible, false otherwise
	 */
	private boolean castleQueenSide(King king, Piece[][] board, Tuple newLocation) {
		int currentRow=king.getCurrentLocation().getRow();
		int currentCol=king.getCurrentLocation().getCol();
		Piece rook;
		
		//check if king has moved
		if(king.hasMoved()) {
			return false;
		}
		
		//check if user is even asking for a castle
		if(!newLocation.equals(new Tuple(king.getCurrentLocation().getRow(), 2))) {
			return false;
		}
		
		//check specific spaces
		for(int i=currentCol-1; i > 0; i--) {
			if(board[currentRow][i] != null) {
				return false;
			}
			inner:for(int p=0; p < pieces.size(); p++) {
				Piece temp= pieces.get(p);
				if(temp.getLocations()==null) {
					continue;
				}
				if(temp.getType().equals(king.getType())) {
					continue inner;
				}
				if(temp.getColor() != king.getColor()) {
					if(temp.getLocations().contains(new Tuple(currentRow, i)) ) {
						return false;
					}
				}
			}
		}
		//check rook
		try {
			if(board[currentRow][0] == null) {
				return false;
			}
			else {
				rook=board[currentRow][0];
				if(rook.hasMoved())
					return false;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}
		
	/**
	 * Helper function used to determine if a king side castle is possible
	 * @param king- king that castling is being determined on
	 * @param board- 2D array representing board
	 * @param newLocation- the new location the player selected in input. The location they are requesting to move to
	 * @return- true if a king side castle is possible, false otherwise
	 */
	private boolean castleKingSide(King king, Piece[][] board, Tuple newLocation) {
		int currentRow=king.getCurrentLocation().getRow();
		int currentCol=king.getCurrentLocation().getCol();
		
		if(king.hasMoved()) {
			return false;
		}
		
		//check if user is even asking for a castle
		if(!newLocation.equals(new Tuple(king.getCurrentLocation().getRow(), 6))) {
			return false;
		}
		//check specific spaces
		for(int i=currentCol+1; i < board.length-1; i++) {
			if(board[currentRow][i] != null) {
				return false;
			}
			inner:for(int p=0; p < this.pieces.size(); p++) {
				Piece temp= this.pieces.get(p);
				if(temp.getLocations()==null) {
					continue inner;
				}
				if(temp.getId().equals(king.getId())) {
					continue inner;
				}
				if(temp.getLocations().contains(new Tuple(currentRow, i)) 
					&& temp.getColor() != king.getColor()) {
					return false;
				}
			}
		}
		//check rook
		try {
			if(board[currentRow][7] == null) {
				return false;
			}
			else {
				Piece temp=board[currentRow][7];
				if(temp.hasMoved())
					return false;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Thie public function used to actually handle the mechanics involved with caslting
	 * Uses the two private helper functions: canQueenSideCastle() and canKingSideCastle()
	 * @param king- king that castling is being determined on
	 * @param board- 2D array representing board
	 * @param newLocation- the new location the player selected in input. The location they are requesting to move to
	 * @param turn- the current player's turn
	 * @return- true if castle happend false otherwise
	 */
	public boolean castleHandeler(King king, Piece[][] board, Tuple newLocation, boolean turn) {
		int currentRow=king.getCurrentLocation().getRow();
		Piece rook=null;
		
		if(this.isLocationAttacked(king.getCurrentLocation(), !turn)) {
			return false;
		}
		
		//check if king has moved
		if(king.hasMoved()) {
			return false;
		}
		
		//check if king is in check
		if(this.isLocationAttacked(king.getCurrentLocation(), turn)) {
			return false;
		}

		if(castleKingSide(king, board, newLocation)) {
			//add castling location to king's locations
			ArrayList<Tuple> temp=king.getLocations();
			temp.add(new Tuple(currentRow, 6));
			king.setLocations(temp);
			
			//move king and rook
			rook=board[currentRow][7];
			board[currentRow][5]=rook;
			board[currentRow][7]=null;
			rook.setCurrentLocation(new Tuple(currentRow, 5));
			rook.setMoved(true);
			
			board[currentRow][6]=king;
			board[currentRow][4]=null;
			king.setCurrentLocation(newLocation);
			king.setMoved(true);
			
			return true;
		}
		
		if(castleQueenSide(king, board, newLocation)) {
			//add castling location to king's locations
			ArrayList<Tuple> temp=king.getLocations();
			temp.add(new Tuple(currentRow, 2));
			king.setLocations(temp);
			
			//move rook and king
			rook=board[currentRow][7];
			board[currentRow][3]=rook;
			board[currentRow][0]=null;
			rook.setCurrentLocation(new Tuple(currentRow, 3));
			rook.setMoved(true);
			
			board[currentRow][2]=king;
			board[currentRow][4]=null;
			king.setCurrentLocation(newLocation);
			king.setMoved(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Handles pawn promotion. 
	 * @param pawn- pawn to be promoted
	 * @param newPieceType- type of the piece the user wants to promote to. Null for default
	 * @param newLocation- the new location the player selected in input. The location they are requesting to move to
	 * @param board- 2D array representing board
	 * @return boolean- this is necessary since the promotion is determined by the resetLocation method in Pawn class
	 */
	public boolean promotion(Pawn pawn, String newPieceType, Tuple newLocation, Piece[][] board) {
		int row=pawn.getCurrentLocation().getRow();
		int col=pawn.getCurrentLocation().getCol();
		Piece newPiece=null;
		board[row][col]=null;
		
		//remove pawn
		this.pieces.remove(pawn);
		pawn.setCurrentLocation(new Tuple(-1,-1));
		pawn.setDead(true);
		
		//create new piece
		if(newPieceType == null || newPieceType.equals("Q")) {
			newPiece=new Queen(new Tuple(row, col), pawn.getColor(), "nQ" + this.newPieceCounter, "queen");
			board[row][col]=newPiece;
			newPiece.resetLocations(board);
		}
		else if(newPieceType.equals("R")) {
			newPiece=new Rook(new Tuple(row, col), pawn.getColor(), "nR" + this.newPieceCounter, "rook");
			board[row][col]=newPiece;
			newPiece.resetLocations(board);
		}
		else if(newPieceType.equals("B")) {
			newPiece=new Bishop(new Tuple(row, col), pawn.getColor(), "nB" + this.newPieceCounter, "bishop");
			board[row][col]=newPiece;
			newPiece.resetLocations(board);
		}
		else if(newPieceType.equals("N")) {
			newPiece=new Knight(new Tuple(row, col), pawn.getColor(), "nK" + this.newPieceCounter, "knight");
			board[row][col]=newPiece;
			newPiece.resetLocations(board);
		}
		
		newPiece.resetLocations(board);
		this.pieces.add(newPiece);
		newPieceCounter++;
		
		return true;
	}
	
	/**
	 * Checks if a pawn needs to spawn a Ghost
	 * @param board- 2D array representing board
	 * @param original- original pawn
	 * @param newLocation- the new location the player selected in input. The location they are requesting to move to
	 * @return- true if a ghost pawn was spawned
	 */
	public boolean checkPawn(Piece[][] board, Pawn original, Tuple newLocation) {
		
		if(original.hasMoved()) {
			return false;
		}
		if(original.getColor()) {
			if(!newLocation.equals(new Tuple(3, original.getCurrentLocation().getCol()))) {
				return false;
			}
		}
		else {
			if(!newLocation.equals(new Tuple(4, original.getCurrentLocation().getCol()))) {
				return false;
			}
		}
		
		this.spawnGhost(board, original);
		return true;
	}
	
	/**
	 * Helper function that spawns a ghost pawn
	 * @param board- 2D array representing board
	 * @param original- original pawn
	 */
	private void spawnGhost(Piece[][] board, Pawn original) {
		int row;
		int col;
		if(original.getColor()) {
			row=original.getCurrentLocation().getRow()-1;
			col=original.getCurrentLocation().getCol();
		}
		else {
			row=original.getCurrentLocation().getRow()+1;
			col=original.getCurrentLocation().getCol();
		}
		GhostPawn gp= new GhostPawn(new Tuple(row, col),
						original.getColor(), "gp", "ghostPawn", original);
		
		this.pieces.add(gp);
		board[row][col]=gp;
	}
	
	/**
	 * Removes ghost pawn
	 * @param board- 2D array representing piece
	 * @param turn- current player. True for what, false for black
	 */
	public void removeGhost(Piece[][] board, boolean turn) {
		Piece temp = null;		
		for(int i=0; i< this.pieces.size(); i++) {
			temp=this.pieces.get(i);
			if(temp.getType().equals("ghostPawn") && temp.getColor() != turn) {
				this.pieces.remove(temp);
				if(!temp.getCurrentLocation().equals(new Tuple(-1,-1))) {
					board[temp.getCurrentLocation().getRow()][temp.getCurrentLocation().getCol()]=null;
				}
				temp.setCurrentLocation(new Tuple(-1, -1));
			}
		}
	}
	
	/**
	 * Gets the ALL of the enemy pieces able to attack an enemy king
	 * @param board- 2D array representing board
	 * @param turn- current player. True for white, false for black
	 * @return- list of all pieces able to attack king
	 */
	public ArrayList<Piece> getAttackingPieces(Piece[][] board, boolean turn) {
		ArrayList<Piece> attackingPieces=new ArrayList<Piece>();
		King king=null;
		if(turn) {
			king=this.getWhiteKing();
		}
		else {
			king=this.getBlackKing();
		}
		
		for(int i=0; i < this.pieces.size(); i++) {
			Piece temp=pieces.get(i);
			if(temp.getColor() == turn) {
				continue;
			}
			if(temp.getType().equals("ghostPawn")) {
				continue;
			}
			if(temp.getLocations().contains(king.getCurrentLocation())) {
				attackingPieces.add(temp);
			}
		}
		return attackingPieces;
	}
	
	/**
	 * Checks if location can be attacked by an enemy piece
	 * @param square- tuple that needs to be checked
	 * @param turn- current player. True for white, false for black
	 * @return- true if location can be attacked by an enemy piece
	 */
	public boolean isLocationAttacked(Tuple square, boolean turn) {
		for(int i=0; i<this.pieces.size(); i++) {
			Piece temp=this.pieces.get(i);
			if(temp.getType().equals("ghostPawn")) {
				continue;
			}
			if(temp.getColor() != turn) {
				continue;
			}
			if(temp.getLocations().contains(square)) {
				return true;
			}
		}
		return false;
	}
}
