package Sudoku;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
//Sysdavam class v koito shte ogranicha JTextField samo na cifri i na edin simvol
public class JTextFieldNum extends DocumentFilter {
	private Pattern regexCheck = Pattern.compile("[0-9]+");
	private int limit;

	//Constructor
    public JTextFieldNum(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be <= 0");
        }
        this.limit = limit;
    }
	 
    //Vyvejda danni v JTextField(vzima)
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null) {
			return;
		}

		if (regexCheck.matcher(str).matches()) {
			super.insertString(fb, offs, str, a);
		}

	}
	
	//Promenq JTextField ako se vyvede po golqm razmer ili drug simvol
	public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
			throws BadLocationException {
		if (str == null) {
			return;
		}
		
		int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + str.length()) - limit - length;
        if (overLimit > 0) {
            str = str.substring(0, str.length() - overLimit);
        }

		if (regexCheck.matcher(str).matches() && str.length() > 0) {
			fb.replace(offset, length, str, attrs);
		}

	}

}
