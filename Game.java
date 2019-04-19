package Sudoku;

import javax.swing.SwingUtilities;

public class Game {
	
	//Syzdavam danni za sudokuto
	//Pravq si dyska
	static void runGame()
	{
		int size = 9, sizeBox = 3, difficult = 48;
		

		Generator board = new Generator(size, sizeBox, difficult);

		//Syzdavane na prazna dyska
		int[][] solveBoard = board.getBoard();
		
		//Avtomatichno zapylvane na dyskata
		new Backtracking(size);

		//Zapylvam dyskata
		board.fillValues();
		
		
		runFrame(solveBoard);
		
	}
	
	//Syzdavam frame
	static void runFrame(int board [][])
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Frame frame = new Frame(board);
				frame.GridInterface(board);
				frame.setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		runGame();
	}

}
