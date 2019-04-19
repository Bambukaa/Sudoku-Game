package Sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int size = 9;
	private int[][] expectToFilledField;
	private int[][] sudokuField;
	private int[][] fullSudoku;
	private JTextField tField[][];
	private final JButton solutionButton;
    private final JButton clearButton;
    private final JPanel buttonPanel;
    private JPanel gridPanel;
    private final JButton finishButton;

    
    //Constructor
	public Frame(int[][] board) {
		//Syzdavam parametri na frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 600));
		
		this.sudokuField = board;
		this.tField = new JTextField[size][size];
		this.expectToFilledField = new int[size][size];
		this.fullSudoku = board;
		
		this.buttonPanel = new JPanel();
		this.gridPanel = new JPanel();
		
		this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		this.clearButton = new JButton("Clear");
        this.solutionButton = new JButton("Solution");
        this.finishButton = new JButton("Ready");
        
        this.clearButton.setBackground(new Color(0, 150, 136));
        this.finishButton.setBackground(new Color(0, 150, 136));
        this.solutionButton.setBackground(new Color(0, 150, 136));
        
        this.buttonPanel.add(clearButton);
        this.buttonPanel.add(solutionButton);
        this.buttonPanel.add(finishButton);
        
        this.setLayout(new BorderLayout());
        this.add(gridPanel);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        
        //Deistvie pri natiskane na butona clear
        clearButton.addActionListener((ActionEvent e) -> {
            clear();
        });

        //Deistvie pri natiskane na solution butona
        solutionButton.addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent arg0) {
        		JFrame solutionFrame = new JFrame("Solution");
        		JPanel gridPanel = new JPanel();
        		JPanel buttonPanel = new JPanel();
        		JButton closeButton = new JButton("OK");
        		
        		closeButton.setBackground(new Color(0, 150,136));
        		
        		closeButton.addActionListener((ActionEvent e) -> {
        			solutionFrame.dispose();
        		});
        		// ------------------------------------
        		// ------------------------------------
        		// ------------------------------------
        		if(Backtracking.SudokuSolver(fullSudoku))
        		{int row = 0, col = 0;
        		gridPanel.setLayout(new GridLayout(3, 3));
        		JPanel box[] = new JPanel[size];
        		JTextField grid[][] = new JTextField[size][size];
        		for (int i = 0; i < size; i++) {
        			box[i] = new JPanel();
        			box[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        			box[i].setLayout(new GridLayout(3, 3));
        			for (int j = 0; j < size; j++) {

        				grid[i][j] = new JTextField();
        				grid[i][j].setHorizontalAlignment(JTextField.CENTER);
        				grid[i][j].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        				grid[i][j].setText(Integer.toString(fullSudoku[row][col]));
        				grid[i][j].setEditable(false);
        				grid[i][j].setBackground(Color.white);
        				
        				if (i == 0 || i == 3 || i == 6) {
        					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
        						col++;
        					else if (j == 2 || j == 5)
        						col = 0;
        					else if (j == 8)
        						col = 3;

        				} else if (i == 1 || i == 4 || i == 7) {
        					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
        						col++;
        					else if (j == 2 || j == 5)
        						col = 3;
        					else if (j == 8)
        						col = 6;

        				} else if (i == 2 || i == 5 || i == 8) {
        					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
        						col++;
        					else if (j == 2 || j == 5)
        						col = 6;
        					else if (j == 8)
        						col = 0;

        				}

        				if ((j == 2 || j == 5) && (i == 0 || i == 3 || i == 6))
        					row++;
        				else if ((j == 2 || j == 5) && (i == 1 || i == 4 || i == 7))
        					row++;
        				else if ((j == 2 || j == 5) && (i == 2 || i == 5 || i == 8))
        					row++;

        				if (j == 8 && (i == 2 || i == 5))
        					row++;
        				else if (j == 8 && (i == 0 || i == 1))
        					row = 0;
        				else if (j == 8 && (i == 3 || i == 4))
        					row = 3;
        				else if (j == 8 && (i == 6 || i == 7))
        					row = 6;

        				box[i].add(grid[i][j]);
        			}
        			gridPanel.add(box[i]);
        		}}
        		// ------------------------------------
        		// ------------------------------------
        		// ------------------------------------
        		buttonPanel.add(closeButton);      	
        		solutionFrame.setLayout(new BorderLayout());
        		solutionFrame.add(gridPanel);
        		solutionFrame.add(buttonPanel, BorderLayout.PAGE_END);
        		
        		gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        		
        		
        		solutionFrame.setSize(600,500);
        		solutionFrame.setVisible(true);
        		framePosition(solutionFrame);
        	}
        });
        
        //Deistvie pri natiskane na finish button
        finishButton.addMouseListener(new MouseAdapter()  {
			public void mouseReleased(MouseEvent arg0) {
            	JFrame finishFrame = new JFrame("End");
            	JButton finishButton = new JButton("OK");
            	finishButton.setBackground(new Color(0, 150, 136));
            	
            	finishButton.addMouseListener(new MouseAdapter()
            			{
            				public void mouseReleased(MouseEvent arg0)
            				{
            					finishFrame.dispose();
            					if(checkFields()) dispose();
            				}
            			});
            		
            	JLabel text = new JLabel();
            	if(checkFields() == true)text.setText("You won!");
            	else text.setText("You have mistake!");
				text.setHorizontalAlignment(JTextField.CENTER);
            	
				finishFrame.setSize(400, 200);
				finishFrame.setMinimumSize(new Dimension(400,200));
				finishFrame.setLayout(new BorderLayout());
            	finishFrame.add(finishButton, BorderLayout.PAGE_END);
            	finishFrame.add(text, BorderLayout.CENTER);
            	finishFrame.setVisible(true);
            	framePosition(finishFrame);
            }
        });
		
		framePosition(this);        
	}
	
	//Frame go syzdava v centyra na ekrana
	void framePosition(JFrame frame)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w)/2;
		int y = (dim.height - h)/2;
		frame.setLocation(x, y);
	}
	
	//Chisti vsichki vyvedeni cifri ot potrebitelq
	void clear()
	{
		
		int row = 0, col = 0;
		for(int i = 0; i < size; i ++)
		{
			for(int j = 0; j < size; j++)
			{
				if(sudokuField[row][col] == 0 )
					tField[i][j].setText("");
				if (i == 0 || i == 3 || i == 6) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 0;
					else if (j == 8)
						col = 3;

				} else if (i == 1 || i == 4 || i == 7) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 3;
					else if (j == 8)
						col = 6;

				} else if (i == 2 || i == 5 || i == 8) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 6;
					else if (j == 8)
						col = 0;

				}

				if ((j == 2 || j == 5) && (i == 0 || i == 3 || i == 6))
					row++;
				else if ((j == 2 || j == 5) && (i == 1 || i == 4 || i == 7))
					row++;
				else if ((j == 2 || j == 5) && (i == 2 || i == 5 || i == 8))
					row++;

				if (j == 8 && (i == 2 || i == 5))
					row++;
				else if (j == 8 && (i == 0 || i == 1))
					row = 0;
				else if (j == 8 && (i == 3 || i == 4))
					row = 3;
				else if (j == 8 && (i == 6 || i == 7))
					row = 6;
			}
		}
	}

	//Proverqva dali vyvedenite cifri sa kakto trqbva da se reshi cqloto sudoku
	boolean checkFields()
	{
		int row = 0, col = 0;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(tField[i][j].getText().equals("")) expectToFilledField[row][col] = 0;
				else expectToFilledField[row][col] = Integer.parseInt(tField[i][j].getText());
				if (i == 0 || i == 3 || i == 6) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 0;
					else if (j == 8)
						col = 3;

				} else if (i == 1 || i == 4 || i == 7) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 3;
					else if (j == 8)
						col = 6;

				} else if (i == 2 || i == 5 || i == 8) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 6;
					else if (j == 8)
						col = 0;

				}

				if ((j == 2 || j == 5) && (i == 0 || i == 3 || i == 6))
					row++;
				else if ((j == 2 || j == 5) && (i == 1 || i == 4 || i == 7))
					row++;
				else if ((j == 2 || j == 5) && (i == 2 || i == 5 || i == 8))
					row++;

				if (j == 8 && (i == 2 || i == 5))
					row++;
				else if (j == 8 && (i == 0 || i == 1))
					row = 0;
				else if (j == 8 && (i == 3 || i == 4))
					row = 3;
				else if (j == 8 && (i == 6 || i == 7))
					row = 6;
			}
		}
		
		if(Backtracking.SudokuSolver(sudokuField))
			if(Arrays.deepEquals(expectToFilledField,sudokuField)) {
				return true;
			}
		return false;
	}

	
	//Syzdava interface na sudokuto
	void GridInterface(int[][] sudokuF) {
		
		
		int row = 0, col = 0;
		gridPanel.setLayout(new GridLayout(3, 3));
		JPanel box[] = new JPanel[size];
		for (int i = 0; i < size; i++) {
			box[i] = new JPanel();
			box[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			box[i].setLayout(new GridLayout(3, 3));
			for (int j = 0; j < size; j++) {

				tField[i][j] = new JTextField();
				tField[i][j].setHorizontalAlignment(JTextField.CENTER);
				tField[i][j].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
				if (sudokuF[row][col] == 0) {
					tField[i][j].setText("");
					((AbstractDocument)tField[i][j].getDocument()).setDocumentFilter(new JTextFieldNum(1));
				} else {
					tField[i][j].setText(Integer.toString(sudokuF[row][col]));
					tField[i][j].setEditable(false);
					tField[i][j].setBackground(Color.white);
				}
				if (i == 0 || i == 3 || i == 6) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 0;
					else if (j == 8)
						col = 3;

				} else if (i == 1 || i == 4 || i == 7) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 3;
					else if (j == 8)
						col = 6;

				} else if (i == 2 || i == 5 || i == 8) {
					if (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)
						col++;
					else if (j == 2 || j == 5)
						col = 6;
					else if (j == 8)
						col = 0;

				}

				if ((j == 2 || j == 5) && (i == 0 || i == 3 || i == 6))
					row++;
				else if ((j == 2 || j == 5) && (i == 1 || i == 4 || i == 7))
					row++;
				else if ((j == 2 || j == 5) && (i == 2 || i == 5 || i == 8))
					row++;

				if (j == 8 && (i == 2 || i == 5))
					row++;
				else if (j == 8 && (i == 0 || i == 1))
					row = 0;
				else if (j == 8 && (i == 3 || i == 4))
					row = 3;
				else if (j == 8 && (i == 6 || i == 7))
					row = 6;

				box[i].add(tField[i][j]);
			}
			gridPanel.add(box[i]);
		}
		
	}
	
	//Neshto sym si testval
	void printSudoku(int[][] sudoku)
	{
		for(int i = 0; i < size; i++)
		{
			for(int j =0; j < size; j++)
			{
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}

	

