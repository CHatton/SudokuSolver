
public class SudokuSolver {

	final static int SIZE = 9;
	static int[][] board = new int[SIZE][SIZE];

	public static void main(String[] args) {

		int[][] arr9 = { { 0, 6, 0, 7, 0, 9, 0, 0, 0 }, { 0, 4, 0, 5, 3, 0, 0, 2, 0 }, { 0, 0, 7, 0, 0, 4, 8, 0, 0 },

				{ 4, 0, 0, 0, 2, 7, 9, 3, 0 }, { 0, 7, 9, 0, 6, 0, 1, 8, 0 }, { 0, 3, 6, 4, 9, 0, 0, 0, 2 },

				{ 0, 0, 4, 9, 0, 0, 6, 0, 0 }, { 0, 1, 0, 0, 7, 2, 0, 5, 0 }, { 0, 0, 0, 8, 0, 1, 0, 9, 0 } };

		int[][] arr4 = { { 0, 1, 3, 0 }, { 2, 0, 0, 0 }, { 0, 0, 0, 3 }, { 0, 2, 1, 0 }

		};

		fillBoard(arr9);
		System.out.println();
		showBoard();
		System.out.println();
		solve(0, 0);
		showBoard();
	} // main

	public static void showBoard() {

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				System.out.print(board[row][col]);
				
			}
			System.out.println();
		}
	} // showBoard

	public static void fillBoard() {

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = 0;
			}
		}
	} // initBoard

	public static void fillBoard(int[][] newBoard) {

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = newBoard[row][col];
			}
		}
	} // fillBoard

	public static boolean isValid(int row, int col, int num) {
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

		// CHECK IF VALUE IN BOX

		return result;
	}// isValid

	public static boolean solve(int row, int col) {

		int nextRow = (row + 1) % SIZE;
		int nextCol = nextRow == 0 ? col + 1 : col;

		if (board[row][col] > 0) {

			if (boardIsFull()) {
				return true; // solved the board!
				
			}
			return solve(nextRow, nextCol);
		
		}

		// if it's an empty space == 0

		for (int num = 1; num <= SIZE; num++) {
			if (isValid(row, col, num)) {
			
				board[row][col] = num;
				
				// assign a valid number to the position

				if (boardIsFull()) {
					return true;
				}

				// compute next position
				boolean isSolved = solve(nextRow, nextCol);

				if (isSolved) {
					return true;
				}

			} // inner for
		} // outer for

		board[row][col] = 0;
		// reset position for when we attempt again
		return false;
	} // solve

	public static boolean boardIsFull() {
		boolean result = true;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				if (board[row][col] == 0) {
					result = false;
				}
			}
		}
		
		return result;
	}

} // SudokuSolver
