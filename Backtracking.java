package Sudoku;

public class Backtracking {
	//Class v koito se zapylvat vsichki cifri koito ne sa zapylneni pyrvonachalno
	private static int sudokuSize;

	//Constructor
	public Backtracking(int sudokuSize) {
		Backtracking.sudokuSize = sudokuSize;
	}
	
	
	//Recursiven method s koite se zapylvat ostanalite cifri
	static public boolean SudokuSolver(int[][] board)
	{
		int row = 0, col = 0;
		boolean isEmpty = true;
		
		for(int i = 0; i < sudokuSize; i++)
		{
			for(int j = 0; j < sudokuSize; j++)
			{
				if(board[i][j] == 0)
				{
					row = i;
					col = j;
					isEmpty = false;
					break;
				}
			}
			if(!isEmpty)
				break;
		}
		
		if(isEmpty) return true;
		
		for(int num = 1; num <= sudokuSize; num++)
		{
			if(Generator.CheckIsSafe(row,col,num,board))
			{
				board[row][col] = num;
				if(SudokuSolver(board)) return true;
				else board[row][col] = 0;
			}
		}
		return false;
	}
	

}
