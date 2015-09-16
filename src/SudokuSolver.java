import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SudokuSolver extends JFrame {

	final static int SIZE = 9;
	static int[][] board = new int[SIZE][SIZE];
	static JLabel[][] grid = new JLabel[SIZE][SIZE];
	static JButton solveButton = new JButton();

	public static void main(String[] args) {

		int[][] arr9 = { { 0, 6, 0, 7, 0, 9, 0, 0, 0 }, { 0, 4, 0, 5, 3, 0, 0, 2, 0 }, { 0, 0, 7, 0, 0, 4, 8, 0, 0 },

				{ 4, 0, 0, 0, 2, 7, 9, 3, 0 }, { 0, 7, 9, 0, 6, 0, 1, 8, 0 }, { 0, 3, 6, 4, 9, 0, 0, 0, 2 },

				{ 0, 0, 4, 9, 0, 0, 6, 0, 0 }, { 0, 1, 0, 0, 7, 2, 0, 5, 0 }, { 0, 0, 0, 8, 0, 1, 0, 9, 0 } };
		int[][] arr8 = { { 0, 0, 0, 7, 0, 9, 0, 0, 0 }, { 0, 4, 0, 5, 3, 0, 0, 2, 0 }, { 0, 0, 7, 0, 0, 4, 8, 0, 0 },

				{ 4, 0, 0, 0, 0, 7, 9, 3, 0 }, { 0, 7, 9, 0, 0, 0, 1, 8, 0 }, { 0, 3, 6, 4, 9, 0, 0, 0, 2 },

				{ 0, 0, 0, 0, 0, 0, 6, 0, 0 }, { 0, 1, 0, 0, 0, 2, 0, 5, 0 }, { 0, 0, 0, 8, 0, 1, 0, 9, 0 } };

		int[][] arr4 = { { 0, 1, 3, 0 }, { 2, 0, 0, 0 }, { 0, 0, 0, 3 }, { 0, 2, 1, 0 }

		};

		start();
		fillBoard(arr9);

		showBoard();
		System.out.println(solve(0,0,false));
		//CompletableFuture.runAsync(() -> solve(0, 0, true));
		//solve(0, 0, true);
		showBoard();

	} // main

	SudokuSolver() { // main GUI

		super("Sudoku Solver (Recursion & Backtracking approach) - Cian Hatton"); // title
																					// text
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes with X
		setVisible(true); // makes it visible
		setLocationRelativeTo(null); // centered
		setSize(800, 800); // creates a 900x800 window
		setResizable(false);// user can't change the window size
		setBackground(Color.BLUE);

		// GridLayout grid1 = (new GridLayout(SIZE, SIZE, 5, 5));
		// GridLayout grid2 = (new GridLayout(9, 2));

		setLayout(new GridLayout(SIZE, SIZE, 5, 5));

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				add(grid[row][col]);
				grid[row][col].setBackground(Color.GRAY);
				grid[row][col].setFont(new Font("Serif", Font.PLAIN, 90));
				grid[row][col].setOpaque(true); // otherwise can't see the
				// background colour change
				grid[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}

		// add(solveButton);

		solveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				CompletableFuture.runAsync(() -> solve(0, 0, true));
				// run solve in a separate thread to prevent locking up
				// Java 8 feature
				showBoard();
			}

		});

		// add(solveButton);

	} // SudokuGame() Constructor

	public static void start() {

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = new JLabel(" ");
			}
		}
		SudokuSolver game = new SudokuSolver();
		// newGame();
		showBoard();
	}

	public static void showBoard() {

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				if (board[row][col] == 0) {
					grid[row][col].setText("  ");
				} else {
					grid[row][col].setText(" " + board[row][col]);
				}
			}
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

	public static boolean solve(int row, int col, boolean showSteps) {

		if (showSteps) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			showBoard();
		}
		// System.out.println("ROW: " + row + " COL: " + col);
		int nextRow = (row + 1) % SIZE;
		int nextCol = (nextRow == 0) ? col + 1 : col;
		/*
		 * if(nextRow == 0){ nextCol = col + 1; }else{ nextCol = col; }
		 */
		if (board[row][col] > 0) {

			if (row == SIZE - 1 && col == SIZE - 1) {
				return true; // solved the board!

			}

			return solve(nextRow, nextCol,showSteps);

		}
		// if it's an empty space == 0

		for (int num = 1; num <= SIZE; num++) {
			if (isValid(row, col, num)) {

				board[row][col] = num;
				grid[row][col].setBackground(new Color(0x007D00));

				// assign a valid number to the position

				if (row == SIZE - 1 && col == SIZE - 1) {
					return true;
				}

				// compute next position
				boolean isSolved = solve(nextRow, nextCol,showSteps);

				if (isSolved) {
					return true;
				}

			} // inner for
		} // outer for

		board[row][col] = 0;
		grid[row][col].setBackground(new Color(0xB20000));
		// reset position for when we attempt again
		return false;
	} // solve

} // SudokuSolver
