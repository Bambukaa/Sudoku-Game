package Sudoku;

public class Generator {
	//Syzdavam class v koito se generira proizvolna dyska  
	private static int sudokuSize;
	private static int boxSize;
	private int removeElements;
	private static int[] board[];
	
	//Constructor
	public Generator(int sudokuSize, int boxSize, int removeElements) {
		Generator.sudokuSize = sudokuSize;
		Generator.boxSize = boxSize;
		this.removeElements = removeElements;

		board = new int[sudokuSize][sudokuSize];
	}

	//Mutator
	public int[][] getBoard() {
		return Generator.board;
	}
	

	//Glaven method
	public void fillValues() {
		FillDiagonal(board);

		FillRemaing(0, boxSize, board);

		RemoveDigits(board);
	}

	//Popylva glavniq diagonal t.e. 3x3 kutiite
	void FillDiagonal(int[][] Board) {
		for (int i = 0; i < sudokuSize; i += boxSize)
			FillBox(i, i, Board);
	}

	//Zapylva 3x3 kutii s proizvolni cifri
	void FillBox(int row, int col, int[][] Board) {
		int digit;
		for (int i = 0; i < boxSize; i++) {
			for (int j = 0; j < boxSize; j++) {
				do {
					digit = RandomGenerator(sudokuSize);
				} while (UsedInBox(row, col, digit,Board));
				Board[row + i][col + j] = digit;
			}
		}
	}

	//Generira proizvolna cifra ot 1 do 9
	int RandomGenerator(int size) {
		return (int) (Math.random() * size + 1);
	}
	
	//Proverqva dali e v kutiq
	static boolean UsedInBox(int row, int col, int digit, int[][] Board) {
		for (int i = 0; i < boxSize; i++) {
			for (int j = 0; j < boxSize; j++) {
				if (Board[row + i][col + j] == digit)
					return true;
			}
		}
		return false;
	}

	//Proverqva dali e v red
	static boolean UsedInRow(int row, int digit, int[][] Board) {
		for (int i = 0; i < sudokuSize; i++) {
			if (Board[row][i] == digit)
				return true;
		}
		return false;
	}

	//Proverqva dali e v kolona
	static boolean UsedInCol(int col, int digit, int[][] board) {
		for (int i = 0; i < sudokuSize; i++) {
			if (board[i][col] == digit)
				return true;
		}
		return false;
	}

	//Proverqva dali e bezopasno tochno opredeleno pole
	public static boolean CheckIsSafe(int row, int col, int digit, int[][] Board) {
		return (!UsedInRow(row, digit,Board) && !UsedInCol(col, digit,Board) && !UsedInBox(row - row % 3, col - col % 3, digit,Board));
	}

	//Zapylva vsichko ostanalo koeto e ostanalo
	boolean FillRemaing(int row, int col, int[][] Board) {
		if (row >= sudokuSize && col >= sudokuSize)
			return true;

		if (col >= sudokuSize && row < sudokuSize - 1) {
			row++;
			col = 0;
		}

		if (row < boxSize) {
			if (col < boxSize) {
				col = boxSize;
			}
		} else if (row < sudokuSize - boxSize) {
			if (col == (int) (row / boxSize) * boxSize) {
				col += boxSize;
			}
		} else {
			if (col == sudokuSize - boxSize) {
				row++;
				col = 0;
				if (row >= sudokuSize)
					return true;
			}
		}

		for (int num = 1; num <= sudokuSize; num++) {
			if (CheckIsSafe(row, col, num,Board)) {
				Board[row][col] = num;
				if (FillRemaing(row, col + 1,Board)) {
					return true;
				}
				Board[row][col] = 0;
			}
		}
		return false;
	}

	//Premaha proizvolni cifri ot dyskata
	void RemoveDigits(int[][] Board) {
		int counter = removeElements;
		while (counter != 0) {
			int cellNumber = RandomGenerator(sudokuSize * sudokuSize) - 1; // Ако е 81 излиза извън размерите на масива
			int row = cellNumber / sudokuSize;
			int col = cellNumber % sudokuSize;
			if (col != 0 && col == sudokuSize)
				col = col - 1;
			if (Board[row][col] != 0) {
				counter--;
				Board[row][col] = 0;
			} else {
				Board[row][col] = Board[row][col];
			}
		}
	}
//Tuk si testvah :D
	public void printSudoku() {
		for (int i = 0; i < sudokuSize; i++) {
			for (int j = 0; j < sudokuSize; j++) {
				if (board[i][j] == 0)
					System.out.print("- ");
				else if (board[i][j] != 0)
					System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
}
