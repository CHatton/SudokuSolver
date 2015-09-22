import java.awt.Color;

import javax.swing.JOptionPane;

public abstract class Solver {

	
	static boolean isSolving = false;
	final static int SIZE = 9;

	public static boolean isValid(int[][] board, int row, int col, int num) {
		boolean result = true; // assume position is valid

		for (int i = 0; i < SIZE; i++) {
			if (board[i][col] == num) {
				result = false;
			}
		} // check if the row contains the same value

		for (int i = 0; i < SIZE; i++) {
			if (board[row][i] == num) {
				result = false;
			}
		} // check if the col contains the same value

		// TOP ROW
		if (row < 3 && col < 3) { // top left segment

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}

		else if (row < 3 && (col < 6 && col > 2)) { // top mid segment

			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 6; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}
		}

		else if (row < 3 && col > 5) { // top right segment *

			for (int i = 0; i < 3; i++) {
				for (int j = 6; j < SIZE; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}
			// TOP ROW

			// MID ROW
		}

		else if ((row > 2 && row < 6) && col < 3) { // mid left *

			for (int i = 3; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}

		else if ((row > 2 && row < 6) && (col > 2 && col < 6)) { // mid *

			for (int i = 3; i < 6; i++) {
				for (int j = 3; j < 6; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}

		else if ((row > 2 && row < 6) && col > 5) { // mid right

			for (int i = 3; i < 6; i++) {
				for (int j = 6; j < SIZE; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			} // MID ROW

			// BOTTOM ROW
		} else if (row > 5 && col < 3) { // bottom left

			for (int i = 6; i < SIZE; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}

		else if (row > 5 && (col > 2 && col < 6)) {// bottom mid

			for (int i = 6; i < SIZE; i++) {
				for (int j = 3; j < 6; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}

		else { // bottom right

			for (int i = 6; i < SIZE; i++) {
				for (int j = 6; j < SIZE; j++) {
					if (board[i][j] == num) {
						result = false;
					}
				}
			}

		}
		// BOTTOM ROW

		return result;
	}// isValid

	public static boolean solve(int[][] board, int row, int col, boolean showSteps) {

		if (showSteps) {
			try {
				Thread.sleep(GameBoard.waitTime);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

		GameBoard.showBoard(board);

		int nextRow = (row + 1) % SIZE;
		int nextCol = (nextRow == 0) ? col + 1 : col;

		if (board[row][col] > 0) {
			if (row == SIZE - 1 && col == SIZE - 1) {
				return true; // solved the board!
			}

			return solve(board, nextRow, nextCol, showSteps);

		}
		// if it's an empty space == 0

		for (int num = 1; num <= SIZE; num++) {
			if (isValid(board, row, col, num)) {

				board[row][col] = num;
				GameBoard.grid[row][col].setBackground(new Color(0x007D00)); // green

				// assign a valid number to the position

				if (row == SIZE - 1 && col == SIZE - 1) {
					GameBoard.showBoard(board);

					// showBoard needed to print final value won't be reached
					// need to add one here to update board for last time
					return true;
				} // end of board

				// compute next position
				boolean isSolved = solve(board, nextRow, nextCol, showSteps);

				if (isSolved) {
					return true;
				}

			} // inner for
		} // outer for

		board[row][col] = 0;
		GameBoard.grid[row][col].setBackground(new Color(0xB20000)); // red
		// reset position for when we attempt again
		return false;
	} // solve

	public static void solveWithPause(int[][] board){
		isSolving = true;
		solve(board,0,0,true);
		isSolving = false;
		JOptionPane.showMessageDialog(null, "Puzzle Solved!");
		
	}
	
	public static boolean getSolving(){
		return isSolving;
	}
	
} // Solver Class
