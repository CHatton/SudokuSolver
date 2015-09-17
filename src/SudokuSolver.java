
// Cian Hatton - G00265311
// CHatton on GitHub

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
import javax.swing.JOptionPane;

public class SudokuSolver extends JFrame {

	final static int SIZE = 9;
	// static int[][] sudoku = new int[SIZE][SIZE];
	static JLabel[][] grid = new JLabel[SIZE][SIZE];

	// BUTTONS
	static JButton solveButton = new JButton();
	static JButton showStepsButton = new JButton();
	static JButton randomPuzzleButton = new JButton();
	static JButton enterPuzzleButton = new JButton();
	static JButton clearButton = new JButton();
	// BUTTONS

	public static void main(String[] args) {

		start();
		showBoard(fillBoard(Premade.grid2));

		// System.out.println(solve(0, 0, false));
		// CompletableFuture.runAsync(() -> solve(arr9,0, 0, true));
		// solve(0, 0, true);
		// showBoard();

	} // main

	SudokuSolver() { // main GUI

		super("Sudoku Solver (Recursion & Backtracking Method) - Cian Hatton"); // title
																				// text
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes with X
		setVisible(true); // makes it visible
		setLocationRelativeTo(null); // centered
		setSize(800, 800); // creates a 900x800 window
		setResizable(false);// user can't change the window size
		setBackground(Color.BLUE);

		setLayout(new GridLayout(SIZE + 1, SIZE, 5, 5));

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

		add(solveButton);
		solveButton.setText("SOLVE");
		solveButton.setToolTipText("Solves the puzzle instantly"); // on hover

		add(new JLabel()); // fill grid slot

		add(showStepsButton);
		showStepsButton.setText("SHOW");
		showStepsButton.setToolTipText("Goes through the process of solving the puzzle"); // on
																							// hover

		add(new JLabel()); // fill grid slot

		add(randomPuzzleButton);
		randomPuzzleButton.setText("PREMADE");
		randomPuzzleButton.setToolTipText("Provides one of 5 unique premade puzzles");

		add(new JLabel()); // fill grid slot

		add(enterPuzzleButton);
		enterPuzzleButton.setText("CUSTOM");
		enterPuzzleButton.setToolTipText("Bring up a custom sudoku window");

		add(new JLabel()); // fill grid slot

		add(clearButton);
		clearButton.setText("CLEAR");
		clearButton.setToolTipText("Clears any existing puzzle");

		// BUTTON FUNCTIONS
		solveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int[][] b = getBoard(); // get current board state

				if (solve(b, 0, 0, false)) { // if solvable
					showBoard(b); // show result
				} else { // impossible to solve
					JOptionPane.showMessageDialog(null, "That puzzle is not solvable :(");
					showBoard(b); // show result
				}
			}

		});

		showStepsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int[][] b = getBoard(); // get board state
				int[][] c = getBoard(); // get second current board state

				if (solve(c, 0, 0, false)) { // if solvable

					for (int i = 0; i < SIZE; i++) {
						for (int j = 0; j < SIZE; j++) {
							grid[i][j].setBackground(Color.GRAY);
							// reset colour so colour from previous
							// test bleeds through
						}
					}

					CompletableFuture.runAsync(() -> solve(b, 0, 0, true));
				} else { // not solvable
					JOptionPane.showMessageDialog(null, "That puzzle is not solvable :(");
					showBoard(b); // show result
				}

				showBoard(b);
				// solve(b,0,0,true);
				// run solve in a separate thread to prevent locking up
				// Java 8 feature
				// showBoard();
			}

		});

		randomPuzzleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		enterPuzzleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		clearButton.addActionListener(new ActionListener() { // working as intended, clears the board

			public void actionPerformed(ActionEvent e) {

				int[][] b = fillBoard(); // make an empty board
				showBoard(b); // show empty board

			}

		});

		// BUTTON FUNCTIONS

	} // SudokuGame() Constructor

	public static int[][] getBoard() {

		int[][] board = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (grid[row][col].getText().trim().equals("")) {
					board[row][col] = 0;
				} else {
					board[row][col] = Integer.parseInt(grid[row][col].getText().trim());
				}

			}
		}

		return board;
	}

	public static void start() {

		int[][] board = new int[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = new JLabel(" ");
			}
		}
		SudokuSolver game = new SudokuSolver();
		// newGame();
		showBoard(board);
	}

	public static void showBoard(int[][] board) {

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				if (board[row][col] == 0) {
					grid[row][col].setText("  ");
				} else {
					grid[row][col].setText(" " + board[row][col]);
				}
			}
		}

		// if(board[SIZE-1][SIZE-1] != 0){
		// grid[SIZE - 1][SIZE - 1].setText(" " + board[SIZE - 1][SIZE - 1]);
		// }
	} // showBoard

	public static int[][] fillBoard() {

		int[][] board = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				grid[row][col].setBackground(Color.GRAY);
				board[row][col] = 0;
			}
		}

		return board;
	} // initBoard

	public static int[][] fillBoard(int[][] newBoard) {

		int[][] board = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = newBoard[row][col];
			}
		}

		return board;
	} // fillBoard

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
		
		
		else if (row < 3 && (col < 6 && col > 2)){ // top mid segment 

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
		
		else if ((row > 2 && row < 6)&&( col > 2 && col < 6)) { // mid *

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
			}
			// MID ROW

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
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

		showBoard(board);
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

			return solve(board, nextRow, nextCol, showSteps);

		}
		// if it's an empty space == 0

		for (int num = 1; num <= SIZE; num++) {
			if (isValid(board, row, col, num)) {

				board[row][col] = num;
				grid[row][col].setBackground(new Color(0x007D00)); // green

				// assign a valid number to the position

				if (row == SIZE - 1 && col == SIZE - 1) {
					showBoard(board);
					// showBoard needed to print final value won't be reached
					// need to add one here to update board for last time
					return true;
				}

				// compute next position
				boolean isSolved = solve(board, nextRow, nextCol, showSteps);

				if (isSolved) {
					return true;
				}

			} // inner for
		} // outer for

		board[row][col] = 0;
		grid[row][col].setBackground(new Color(0xB20000)); // red
		// reset position for when we attempt again
		return false;
	} // solve

} // SudokuSolver
