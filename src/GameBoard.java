
/*
 * Sudoku Solver
 * Cian Hatton - G00265311
 * CHatton on GitHub
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameBoard extends JFrame {

	final static int SIZE = 9; // size of the board is 9x9

	static int waitTime = 125; // determines length of SHOW button in ms

	static JLabel[][] grid = new JLabel[SIZE][SIZE];
	// JLabel text will be changed to update board state

	static JButton solveButton = new JButton();
	static JButton showStepsButton = new JButton();
	static JButton randomPuzzleButton = new JButton();
	static JButton enterPuzzleButton = new JButton();
	static JButton clearButton = new JButton();
	// create all of the buttons that will be added to the grid

	// adds buttons to the game board

	public static void main(String[] args) throws FileNotFoundException {

		try {
			Scanner premade = new Scanner(new FileReader("premadePuzzles.dat"));
			start();
			showBoard(randomFill(premade));
			/*
			 * if the file premadePuzzles.dat exists in the same folder start
			 * the program, otherwise give the error message saying that the
			 * user needs the file for the program to work
			 */

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Please make sure the file 'premadePuzzles.dat' is in the same folder as the Sudoku Solver!    ");
			System.exit(0);
			/*
			 * provide the user with the error message that the file is not in
			 * the same folder, and then shut down the program.
			 */
		}
	} // main

	public GameBoard() { // main GUI

		super("Sudoku Solver (Recursion & Backtracking Method) - Cian Hatton"); // title
																				// text
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes with X
		setVisible(true); // makes it visible
		setLocationRelativeTo(null);
		// top left corner is positioned in the center
		setSize(850, 850); // creates an 850x850 window
		setResizable(false);// user can't change the window size

		setLayout(new GridLayout(SIZE + 1, SIZE, 3, 3));
		/*
		 * SIZE + 1 to allow for buttons on bottom row standard GridLayout to
		 * allow for the Sudoku grid and the row of buttons along the bottom
		 */

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				add(grid[row][col]);
				grid[row][col].setBackground(new Color(0x518191)); // grey
																	// colour
				grid[row][col].setFont(new Font("Forte", Font.PLAIN, 90));
				// set font and size
				grid[row][col].setOpaque(true);
				// otherwise can't see the background colour change

				grid[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
				// creates black border around each item in the grid
			}
		}
		// creates 81 JLabels to fill the grid

		add(solveButton); // add the solve button to the GridLayout
		solveButton.setText("SOLVE"); // text on button
		solveButton.setToolTipText("Solves the puzzle instantly"); // on hover

		add(new JLabel()); // fill empty grid slot

		add(showStepsButton);// add the show button to the GridLayout
		showStepsButton.setText("SHOW");
		showStepsButton.setToolTipText(
				"Goes through the process of solving the puzzle," + " depending on the difficulty of the puzzle, "
						+ "this process make take a longer time!" + " for results, press the 'SOLVE' button instead.");
		// text that appears on hover

		add(new JLabel()); // fill empty grid slot

		add(randomPuzzleButton);// add the random button to the GridLayout
		randomPuzzleButton.setText("RND"); // button text
		randomPuzzleButton.setToolTipText("Provides one of 6 unique premade puzzles");
		// text on hover

		add(new JLabel()); // fill grid slot

		add(enterPuzzleButton);// add the custom button to the GridLayout
		enterPuzzleButton.setText("CUSTOM");// button text
		enterPuzzleButton.setToolTipText("Bring up a custom sudoku window");
		// text on hover

		add(new JLabel()); // fill grid slot

		add(clearButton);// add the clear button to the GridLayout
		clearButton.setText("CLEAR");// button text
		clearButton.setToolTipText("Clears any existing puzzle");
		// text on hover

		/*
		 * the purpose of the empty JLabels is to neatly fill out the grid
		 * layout, otherwise the buttons would all be in a row and wouldn't look
		 * as good. Each button is added to the grid and given button text and
		 * text that will appear on hover.
		 */

		// BUTTON FUNCTIONS

		/*
		 * each button gets its own ActionListener, these are to enable each
		 * button to have its own function. Each button will run various
		 * different methods based on its purpose.
		 */

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
		/*
		 * if the board state is not valid, the user will get the appropriate
		 * message, similarly if the board is empty or currently solving a
		 * puzzle the user will get a message. if none of these things are the
		 * case, then the program will simply call the solve method to solve the
		 * board and give an appropriate message if the board is not solvable
		 */

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
					/*
					 * Java 8 features, runs solveWithPause() in a separate
					 * Thread
					 */

				} else { // not solvable
					JOptionPane.showMessageDialog(null, "That puzzle is not solvable :(");
					showBoard(b); // show result
				}

				showBoard(b);
			}
		});
		/*
		 * The purpose of creating a second board is to test to see if the board
		 * is solvable before attempting to solve it. If an attempt to solve the
		 * board is made, the board will be changed. So one dummy board is made
		 * to see if the current board state is solvable. If it is then the
		 * board is set back to grey (other wise it would be all green) and then
		 * the solve function is called using runAsync(), this is a Java 8
		 * method that runs the method in a separate thread The reason multiple
		 * threads are required is because one thread needs to maintain the
		 * JFrame while the other does the Solve method. The board is not able
		 * to be updated in the GUI without a dedicated thread.
		 * 
		 * Similarly as above, the appropriate messages are displayed if there
		 * is a problem with the board
		 */

		randomPuzzleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {
					try {
						showBoard(randomFill(new Scanner(new FileReader("premadePuzzles.dat"))));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					makeGray();
				}
			}
		});
		/*
		 * This buttons reads in from the premadePuzzles.dat file and picks a
		 * "random" board state from it
		 */

		enterPuzzleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {
					new InputGrid(); // brings up second board
				}
			}
		});
		/*
		 * This button creates a new instance of InputGrid. It brings up a new
		 * window for the user to interact with and enter and save custom
		 * puzzles.
		 */

		clearButton.addActionListener(new ActionListener() {
			// Clears the board
			public void actionPerformed(ActionEvent e) {
				if (Solver.getSolving()) {
					JOptionPane.showMessageDialog(null, "STILL SOLVING!");
				} else {
					int[][] b = fillBoard(new int[SIZE][SIZE]); // make an empty
																// board
					showBoard(b); // show empty board
				}
			}
		});
		/*
		 * This button empties the board as long as it's not currently solving a
		 * puzzle.
		 */
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
	/*
	 * The start() method is called in the main method, it fills up the main
	 * grid with empty JLabels, It then creates a new instance of GameBoard.
	 */

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
	/*
	 * The getBoard() method is used in several other methods, its purpose is to
	 * create a 2D array representing the current state of the board by parsing
	 * an int from the each element in the 2D array of JLabels that the user can
	 * see.
	 */

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
	/*
	 * The showBoard() method takes a 2D int array and updates the 2D JLabel to
	 * display the contents of the board array.
	 */

	public static int[][] fillBoard(int[][] newBoard) {

		int[][] board = new int[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = newBoard[row][col];
			} // update board array with the contents of array provided
		}

		return board;
	} // fillBoard
	/*
	 * The fillBoard() method takes in a 2D int array and updates the main 2D
	 * int array with the contents of the array provided
	 */

	public static void makeGray() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				grid[row][col].setBackground(new Color(0x518191)); // Gray
			}
		}
	}
	/*
	 * makeGray() simply changes the background colour of every JLabel
	 */

	public static int[][] randomFill(Scanner file) {

		ArrayList<String> list = new ArrayList<String>();
		/*
		 * An ArrayList is used as there could be any number of lines in the
		 * file that's being read from, if an Array was used, then it would be
		 * required to pass through the file once to get the size, and then
		 * again to pick a line.
		 * 
		 */

		int board[][] = new int[SIZE][SIZE];

		while (file.hasNext()) {
			list.add(file.nextLine());
		} // add all possible puzzles to the list

		String[] boardOptions = new String[list.size()];
		// create array to hold the possible boards

		list.toArray(boardOptions); // convert from list to array

		Random rnd = new Random(); // for random numbers
		int choice = rnd.nextInt(list.size()); // pick a random position

		String[] chosenStrBoard = boardOptions[choice].split(" ");
		/*
		 * Split the string of 81 characters into an array of 81 characters by
		 * splitting them on the " " character.
		 */

		int[] chosenBoard = new int[SIZE * SIZE]; // create array to hold ints

		for (int i = 0; i < chosenBoard.length; i++) {
			chosenBoard[i] = Integer.parseInt(chosenStrBoard[i]);
		} // populate int array by parsing the String array

		int counter = 0;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = chosenBoard[counter++];
				// fill board with the randomly chosen board
			}
		}

		file.close();

		return board;
	}
	/*
	 * The random fill method takes in a file. It adds every line of the file to
	 * an ArrayList. It splits the ArrayList into a size 81 String array based
	 * on a random number chose, then an int array of size 81 is created and
	 * filled by parsing the String array. This is used to fill up the 2D array
	 * and returns it
	 */

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
	/*
	 * isEmptyBoard checks to see if every element in the 2D array is 0, if it
	 * is, then the board is empty.
	 */

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
	/*
	 * The reverse of isEmptyBoard, it checks to see if every element is between
	 * 1-9
	 */
} // SudokuSolver
