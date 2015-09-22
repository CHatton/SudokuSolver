import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InputGrid extends JFrame {

	public InputGrid() {
		super("Input Custom Sudoku");
		JButton submit = new JButton();
		TextArea[][] in = new TextArea[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				in[row][col] = new TextArea();
			}
		}

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes with X
		setVisible(true); // makes it visible
		setLocationRelativeTo(null); // centered
		setSize(500, 500); // creates a 900x800 window
		setResizable(false);// user can't change the window size

		setLayout(new GridLayout(10, 9));
		int[][] board = new int[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				add(in[row][col]);
				in[row][col].setText("0");
			}
		}

		add(submit);
		submit.setText("OK");
		submit.setToolTipText("Submit your custom puzzle");

		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (GameBoard.isSolving) {
					JOptionPane.showMessageDialog(null, "Please wait for puzzle to solve!");
				} else {

					int[][] board = new int[9][9];

					for (int row = 0; row < 9; row++) {
						for (int col = 0; col < 9; col++) {

							try { 
								board[row][col] = Integer.parseInt(in[row][col].getText());
							} catch (NumberFormatException n) { // if user puts in an invalid character
								System.out.println("Invalid Value Assigned to grid.");
							}

						}
					}

					GameBoard.makeGray();
					GameBoard.showBoard(board);

				}

			}

		});

	} // InputGrid

}
