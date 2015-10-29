
// Cian Hatton - G00265311
// CHatton on GitHub

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameBoard extends JFrame {

	final static int SIZE = 9;

	static int waitTime = 125; // determines length of SHOW button in ms

	static JLabel[][] grid = new JLabel[SIZE][SIZE];
	// JLabel text will be changed to update board state

	// BUTTONS
	static JButton solveButton = new JButton();
	static JButton showStepsButton = new JButton();
	static JButton randomPuzzleButton = new JButton();
	static JButton enterPuzzleButton = new JButton();
	static JButton clearButton = new JButton();
	// BUTTONS
	// adds buttons to the game board
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner premade = new Scanner(new FileReader("premadePuzzles.dat"));

		start();
		showBoard(randomFill(premade));
		// shows a random puzzle from the .dat file
		
		premade.close();

	} // main

	public GameBoard() { // main GUI

		super("Sudoku Solver (Recursion & Backtracking Method) - Cian Hatton"); // title
																				// text
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes with X
		setVisible(true); // makes it visible
		setLocationRelativeTo(null); // centered
		setSize(800, 800); // creates an 800x800 window
		setResizable(false);// user can't change the window size
		// setBackground(Color.BLUE);

		setLayout(new GridLayout(SIZE + 1, SIZE, 5, 5));
		// SIZE + 1 to allow for buttons on bottom row
		
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
		showStepsButton.setToolTipText(
				"Goes through the process of solving the puzzle," + " depending on the difficulty of the puzzle, "
						+ "this process make take a longer time!" + " for results, press the 'SOLVE' button instead."); // on
		// hover

		add(new JLabel()); // fill grid slot

		add(randomPuzzleButton);
		randomPuzzleButton.setText("RND");
		randomPuzzleButton.setToolTipText("Provides one of 6 unique premade puzzles");

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

				if (Solver.validBoardState(b) == false) {
					JOptionPane.showMessageDialog(null, "Initial Board State Invalid");
				} else if (isEmptyBoard(b)) {
					JOptionPane.showMessageDialog(null, "Board is empty!");

				} else if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {

					if (Solver.solve(b, 0, 0, false)) { // if solvable
						showBoard(b); // show result
					} else { // impossible to solve
						JOptionPane.showMessageDialog(null, "That puzzle is not solvable :(");
						showBoard(b); // show result
					}
				} // !isSolving
			}
		});

		showStepsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int[][] b = getBoard(); // get board state
				int[][] c = getBoard(); // get second current board state

				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else if (Solver.validBoardState(b) == false) {
					JOptionPane.showMessageDialog(null, "Initial Board State Invalid");
				} else if (isEmptyBoard(b)) {
					System.out.println("Empty Board");
				} else if (boardFull(b)) {
					System.out.println("Full board");

				} else if (Solver.solve(c, 0, 0, false)) { // if solvable
					makeGray();
					CompletableFuture.runAsync(() -> Solver.solveWithPause(b));
				} else { // not solvable
					JOptionPane.showMessageDialog(null, "That puzzle is not solvable :(");
					showBoard(b); // show result
				}

				showBoard(b);
			}
		});

		randomPuzzleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {

					Scanner premade;

					try {
						premade = new Scanner(new FileReader("premadePuzzles.dat"));
						int numLines = 0;

						while (premade.hasNext()) {
							premade.nextLine();
							numLines++;
						}
						// count number of puzzles in the file
						premade.close();

						premade = new Scanner(new FileReader("premadePuzzles.dat"));

						Random rnd = new Random();
						int num = rnd.nextInt(numLines);
						
						for (int i = 0; i < num; i++) {
							premade.nextLine();
						} // skip lines to get the "random" puzzle

						showBoard(randomFill(premade));
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

					makeGray();
				}
			}
		});

		enterPuzzleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Solver.getSolving()){
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				}else{
					new InputGrid(); // brings up second board
				}
			}
		});

		clearButton.addActionListener(new ActionListener() {
			// Clears the board
			public void actionPerformed(ActionEvent e) {
				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {
					int[][] b = fillBoard(); // make an empty board
					showBoard(b); // show empty board
				}
			}
		});
		// BUTTON FUNCTIONS

	} // Constructor

	public static void start() {

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = new JLabel(" ");
				// fill grid with all empty JLabels
			}
		}
		GameBoard game = new GameBoard();
		System.out.println("Game Started!");

	}

	public static int[][] getBoard() {
		// used to get the current board state
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
	} // getBoard

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
	} // fillBoard

	public static int[][] fillBoard(int[][] newBoard) {

		int[][] board = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = newBoard[row][col];
			}
		}

		return board;
	} // fillBoard

	public static void makeGray() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				grid[row][col].setBackground(Color.GRAY);
			}
		}
	}

	public static int[][] randomFill(Scanner file) {
		// returns a board from the .dat file
		
		int board[][] = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = file.nextInt();
			}
		}
		file.close();
		return board;
	}

	public static boolean isEmptyBoard(int[][] board) {
		// determines if the board is empty or not
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] != 0) {
					return false;
				}
			}
		}
		return true;
	} // isEmptyBoard

	public static boolean boardFull(int[][] board) {
		// determines if the board is full
		for (int row = 0; row < SIZE; row++) {

			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == 0) {
					return false;
				}
			}
		}
		return true;
	} // boardFull
} // SudokuSolver
