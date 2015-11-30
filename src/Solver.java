
/*
 * Sudoku Solver
 * Cian Hatton - G00265311
 * CHatton on GitHub
*/
import java.awt.Color;
import java.util.HashSet;

import javax.swing.JOptionPane;

public abstract class Solver {

	static boolean isSolving = false;
	// variables used to see if the "SHOW" button is currently being used
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

		/*
		 * The checkSegment method is used to check if the number exists in a
		 * given 3x3 grid.
		 */

		// TOP ROW
		if (row < 3 && col < 3) { // top left segment

			if (checkSegment(board, 0, 3, 0, 3, num) == false) {
				return false;
				// if this is false, then the number exists in that segment.
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
	/*
	 * This method takes in a 2D array of ints, a position through a row and
	 * column, and a number. It then checks to see if that number is valid in
	 * that position in that gameBoard. It is used to test values 1-9 in the
	 * main Solve method.
	 * 
	 * the method checks every row, column and 3x3 grid to see if that number
	 * exists already. If it exists, then it returns false, meaning that the
	 * number in that position is not valid
	 */

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
	/*
	 * This method is just used in the other methods in order to check a given
	 * 3x3 segment.
	 */

	public static boolean validBoardState(int[][] board) {
		HashSet<Integer> set = new HashSet<Integer>();
		// HashSet used to check whether or not there are duplicates

		int rootSize = (int) Math.sqrt(SIZE);
		/*
		 * getting the sqrt of the number allows for different sized board to be
		 * tested
		 */

		for (int row = 0; row < SIZE; row++) {
			for (int i = 0; i < SIZE; i++) {

				if (set.contains(board[row][i]))
					return false;
				// if the set contains the number, then it is a duplicate

				if (board[row][i] != 0)
					set.add(board[row][i]);
				/*
				 * if we've gotten here, there hasn't been a duplicate yet, so
				 * we add it to the set.
				 */
			}
			set.clear();
			// clear the set for use with the next section
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
	/*
	 * In isValidBoardState method, a HashSet is used to check the rows, cols
	 * and 3x3 segments for duplicates. If there are duplicates in any of these
	 * places, then the board is invalid. You only need a single duplicate and
	 * the entire board is invalid.
	 */

	public static boolean solve(int[][] board, int row, int col, boolean showSteps) {

		if (showSteps) {
			pause();
		} // pauses the thread

		GameBoard.showBoard(board); // updates display

		int nextRow = (row + 1) % SIZE;
		/*
		 * the next row will be one higher than the previous. The only time this
		 * will not be the case is when row == SIZE, at which point, it will go
		 * onto position 1 of the next row
		 */
		int nextCol = (nextRow == 0) ? col + 1 : col;

		/*
		 * The only time that the column will change, is when the nextRow is 0,
		 * otherwise the column remains the same
		 */

		if (board[row][col] > 0) { // if there is a value already in the board

			GameBoard.grid[row][col].setBackground(new Color(0x007D00)); // green

			if (row == SIZE - 1 && col == SIZE - 1) {
				return true; // solved the board!
			}
			/*
			 * if we have reaced the last place of the board and already have a
			 * preset value, then we know we have solve the board.
			 */

			boolean isSolvable = solve(board, nextRow, nextCol, showSteps);
			/*
			 * This check is to see if the board is solvable from this point, if
			 * it is, we keep the current square green, if it's not solvable,
			 * then we know that we'll be backtracking, so we change the colour
			 * of the square.
			 */

			if (!isSolvable) {
				GameBoard.grid[row][col].setBackground(new Color(0x518191)); // grey
				// we know we'll be going back.
			}

			if (showSteps) {
				pause();
			} // pause again so user can see.

			return isSolvable;
			/*
			 * will be false if the board has become invalid, and true if we can
			 * still solve the board.
			 */
		}

		// if it's an empty space == 0
		for (int num = 1; num <= SIZE; num++) {

			if (isValid(board, row, col, num)) {

				// here we test every possible value between 1 - 9.

				board[row][col] = num;
				/*
				 * If we've found a valid number, then we update the board
				 * accordingly.
				 */
				GameBoard.grid[row][col].setBackground(new Color(0x007D00)); // green
				// change the colour to be green
				GameBoard.showBoard(board);
				// update the JLable array

				if (row == SIZE - 1 && col == SIZE - 1) {
					/*
					 * if we've reached the last spot with a valid number, then
					 * we've solved the board and can return true.
					 */
					GameBoard.showBoard(board);
					// showBoard needed to print final value won't be reached
					// need to add one here to update board for last time
					return true;
					/*
					 * At this point, if we return true, the overall method will
					 * return true. Indicating that the board has been solved.
					 */
				} // end of board

				// compute next position
				boolean isSolved = solve(board, nextRow, nextCol, showSteps);

				/*
				 * again we test to see if the board is solvable at this point.
				 * If it is, we return true.
				 */

				if (isSolved) {
					return true;
				}

			} // if valid
		} // outer for

		/*
		 * once we're here, it means that we will be returning false, so we need
		 * to delete the character, show red to the user, pause again and then
		 * finally return false.
		 */

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

	/*
	 * Example of solve method: say square 0,0 has a number in it already. Since
	 * it is not the last square we simply solve the next square. Then we move
	 * on to 1,0 which is empty. We try 1, it is invalid because there is a 1
	 * somewhere in the same row. We then try 2, it is invalid because there is
	 * a 2 somewhere in the same column. We try 4, it is invalid because it is
	 * somewhere in the same 3x3 grid. We try 5 and it is valid. We fill
	 * grid[1][0] with 4 and solve the next square. in the next square we try 1-
	 * 9 but all of them are invalid. Because of this, we return false. so we go
	 * back to square[1][0]. We now try 5 because 4 has return false. This is
	 * the general idea with this method. It will keep trying every possibility
	 * until it reaches the overall method either returns true or false. If it
	 * returs true, the board is solved, if it returns false, then it means the
	 * board is not solvable.
	 */

	public static void solveWithPause(int[][] board) {
		isSolving = true;
		solve(board, 0, 0, true);
		isSolving = false;
		JOptionPane.showMessageDialog(null, "Puzzle Solved!");

	}
	/*
	 * this method simply sets the value of isSolving to true, solves the board,
	 * then sets isSolving to false. This is used when a different thread is
	 * trying to access the GUI while the board is currently solving.
	 */

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

	// pauses the thread based on waitTime. Default is 125ms

	public static boolean getSolving() {
		return isSolving;
	}
	// allows access to isSolving from the other classes

} // Solver Class
