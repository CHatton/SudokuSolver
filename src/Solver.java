import java.awt.Color;
import java.util.HashSet;

import javax.swing.JOptionPane;

public abstract class Solver {

	static boolean isSolving = false;
	final static int SIZE = 9;

	public static boolean isValid(int[][] board, int row, int col, int num) {
		// determines if the position is valid for a given number
		for (int i = 0; i < SIZE; i++) {
			if (board[i][col] == num) {
				return false;
			}
		} // check if the row contains the same value

		for (int i = 0; i < SIZE; i++) {

			if (board[row][i] == num) {
				return false;
			}
		} // check if the col contains the same value

		// TOP ROW
		if (row < 3 && col < 3) { // top left segment

			if (checkSegment(board, 0, 3, 0, 3, num) == false) {
				return false;
			}

		}

		else if (row < 3 && (col < 6 && col > 2)) { // top mid segment

			if (checkSegment(board, 0, 3, 3, 6, num) == false) {
				return false;
			}
		}

		else if (row < 3 && col > 5) { // top right segment *

			if (checkSegment(board, 0, 3, 6, SIZE, num) == false) {
				return false;
			}
		}

		else if ((row > 2 && row < 6) && col < 3) { // mid left *

			if (checkSegment(board, 3, 6, 0, 3, num) == false) {
				return false;
			}
		}

		else if ((row > 2 && row < 6) && (col > 2 && col < 6)) { // mid *

			if (checkSegment(board, 3, 6, 3, 6, num) == false) {
				return false;
			}
		}

		else if ((row > 2 && row < 6) && col > 5) { // mid right

			if (checkSegment(board, 3, 6, 6, SIZE, num) == false) {
				return false;
			}

			// BOTTOM ROW
		} else if (row > 5 && col < 3) { // bottom left
			if (checkSegment(board, 6, SIZE, 0, 3, num) == false) {
				return false;
			}
		}

		else if (row > 5 && (col > 2 && col < 6)) {// bottom mid

			if (checkSegment(board, 6, SIZE, 3, 6, num) == false) {
				return false;
			}
		}

		else { // bottom right

			if (checkSegment(board, 6, SIZE, 6, SIZE, num) == false) {
				return false;
			}
		}
		// BOTTOM ROW

		return true;
	}// isValid

	public static boolean checkSegment(int[][] board, int rowFrom, int rowTo, int colFrom, int colTo, int num) {

		for (int i = rowFrom; i < rowTo; i++) {
			for (int j = colFrom; j < colTo; j++) {
				if (board[i][j] == num) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean validBoardState(int[][] board) {
		HashSet<Integer> set = new HashSet<Integer>();
		int rootSize = (int) Math.sqrt(SIZE);

		for (int row = 0; row < SIZE; row++) {
			for (int i = 0; i < SIZE; i++) {

				if (set.contains(board[row][i]))
					return false;

				if (board[row][i] != 0)
					set.add(board[row][i]);
			}
			set.clear();
		} // check rows

		for (int col = 0; col < SIZE; col++) {
			for (int i = 0; i < SIZE; i++) {

				if (set.contains(board[i][col]))
					return false;

				if (board[i][col] != 0)
					set.add(board[i][col]);
			}
			set.clear();
		} // check col

		// TOP LEFT
		for (int row = 0; row < rootSize; row++) {
			for (int col = 0; col < rootSize; col++) {

				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// TOP MID
		for (int row = 0; row < rootSize; row++) {
			for (int col = rootSize; col < rootSize * 2; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// TOP RIGHT
		for (int row = 0; row < rootSize; row++) {
			for (int col = rootSize * 2; col < SIZE; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// MID LEFT
		for (int row = rootSize; row < rootSize * 2; row++) {
			for (int col = 0; col < rootSize; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// MID MID
		for (int row = rootSize; row < rootSize * 2; row++) {
			for (int col = rootSize; col < rootSize * 2; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// MID RIGHT
		for (int row = rootSize; row < rootSize * 2; row++) {
			for (int col = rootSize * 2; col < SIZE; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// BOTTOM LEFT
		for (int row = rootSize * 2; row < SIZE; row++) {
			for (int col = 0; col < rootSize; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// BOTTOM MID
		for (int row = rootSize * 2; row < SIZE; row++) {
			for (int col = rootSize; col < rootSize * 2; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}

		// BOTTOM RIGHT
		for (int row = rootSize * 2; row < SIZE; row++) {
			for (int col = rootSize * 2; col < SIZE; col++) {
				if (set.contains(board[row][col]))
					return false;

				if (board[row][col] != 0)
					set.add(board[row][col]);
			}
			set.clear();
		}
		return true; // if every secion is valid, the board is valid
	}

	public static boolean solve(int[][] board, int row, int col, boolean showSteps) {

		if (showSteps) {
			pause();
		}

		GameBoard.showBoard(board);

		int nextRow = (row + 1) % SIZE;
		int nextCol = (nextRow == 0) ? col + 1 : col;

		if (board[row][col] > 0) {

			GameBoard.grid[row][col].setBackground(new Color(0x007D00)); // green

		
			
			if (row == SIZE - 1 && col == SIZE - 1) {
				return true; // solved the board!
			}

			boolean isSolvable = solve(board, nextRow, nextCol, showSteps);

			if (!isSolvable) {
				GameBoard.grid[row][col].setBackground(new Color(0x518191));
			}
			
			if (showSteps) {
				pause();
			}

			return isSolvable;
		}

		// if it's an empty space == 0
		for (int num = 1; num <= SIZE; num++) {

			if (isValid(board, row, col, num)) {

				board[row][col] = num; // assign a valid number to the position
				GameBoard.grid[row][col].setBackground(new Color(0x007D00)); // green
				GameBoard.showBoard(board);

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

			} // if valid
		} // outer for

		GameBoard.grid[row][col].setBackground(new Color(0xB20000)); // red
		board[row][col] = 0; // reset position for when we attempt again
		GameBoard.showBoard(board);

		if (showSteps) {
			pause();
		}

		GameBoard.grid[row][col].setBackground(new Color(0x518191)); // grey
		GameBoard.showBoard(board);

		return false;
	} // solve

	public static void solveWithPause(int[][] board) {
		isSolving = true;
		solve(board, 0, 0, true);
		isSolving = false;
		JOptionPane.showMessageDialog(null, "Puzzle Solved!");

	}

	public static void pause() {

		try {
			Thread.sleep(GameBoard.waitTime);
			// program will pause after every "waitTime"
			// in order to show steps, otherwise it
			// happens too quickly
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static boolean getSolving() {
		return isSolving;
	}

} // Solver Class
