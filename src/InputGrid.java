
/*
 * Sudoku Solver
 * Cian Hatton - G00265311
 * CHatton on GitHub
*/

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InputGrid extends JFrame {

	final static int SIZE = 9;
	static int[][] customBoard = new int[SIZE][SIZE];

	public InputGrid() {
		super("Input Custom Sudoku");
		JButton submit = new JButton(); // create Submit button
		JButton add = new JButton(); // create Add button
		TextArea[][] in = new TextArea[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				in[row][col] = new TextArea();
			}
		} // creates a 9x9 grid of TextAreas

		setVisible(true); // makes it visible
		setLocationRelativeTo(null); // centered
		setSize(550, 550); // creates a 550x550 window
		setResizable(false);// user can't change the window size
		setLayout(new GridLayout(10, 9));

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				add(in[row][col]);
				// add each TextArea to the grid

				int num = 0;

				try {
					num = Integer.parseInt(GameBoard.grid[row][col].getText().trim());
				} catch (NumberFormatException e) {
					// okay to be left 0
				} // possible user will not enter 1-9

				customBoard[row][col] = num;
				// set the value to be the number entered in the input grid
				in[row][col].setText("" + customBoard[row][col]);
				// text is forced as a String
			}
		}

		add(submit);
		add(new JLabel());
		add(add);
		// adds buttons to the grid
		submit.setText("OK"); // submit text
		submit.setToolTipText("Submit your custom puzzle");
		// hover text

		add.setText("ADD"); // add text
		add.setToolTipText("Save custom puzzle (Be sure to hit OK on your new puzzle first!)");
		// hover text

		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				////////////////////////////////////
				for (int row = 0; row < SIZE; row++) {
					for (int col = 0; col < SIZE; col++) {
						try {
							customBoard[row][col] = Integer.parseInt(in[row][col].getText());
							if (customBoard[row][col] > SIZE || customBoard[row][col] < 0) {
								customBoard[row][col] = 0;
							} // sets it to 0 if they put in a bad number

						} catch (NumberFormatException n) {
							// if user puts in an invalid character
							customBoard[row][col] = 0;
							n.printStackTrace();
						}
					}
				}

				GameBoard.makeGray();
				GameBoard.showBoard(customBoard);
				////////////////////////////////////
			}
		});
		/*
		 * when the user hits the submit button, it takes in the number they
		 * chose, if the number is < or > 0, then it is set at 0. If they put it
		 * something that is the wrong format e.g. 'f' or '-' it will also set
		 * it to 0
		 */

		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				////////////////////////////////////
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("premadePuzzles.dat", true)));
					// for appending text to the file.
					out.write("\n");
					for (int row = 0; row < SIZE; row++) {
						for (int col = 0; col < SIZE; col++) {
							out.write(customBoard[row][col] + " ");
							// writes the user's choice to the file
						}
					}
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("New puzzle added!");
				////////////////////////////////////
			}

		}); // Add Button
		/*
		 * This button adds a new line of 81 characters to the
		 * premadePuzzles.dat file that the user has entered to be chose by the
		 * randomFill method at a later point
		 */
	} // InputGrid

} // Class
