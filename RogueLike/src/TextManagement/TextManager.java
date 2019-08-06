package TextManagement;

import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class TextManager {
	
	private static TextManager textManager; 
	private final static String newline = "\n";
	private JTextArea textArea1;
	private JTextArea textArea2;
	private final static int linelimit=28;
	private WordDataGetter getter;
	
	private TextManager(JTextArea textArea1, JTextArea textArea2){
		this.textArea1 = textArea1;
		this.textArea2 = textArea2;
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}
	
	public static TextManager getSingletonInstance(JTextArea textArea1, JTextArea textArea2){
		if(textManager==null){
			textManager = new TextManager(textArea1, textArea2);
		} 
		return textManager;
	}

	public static TextManager getTextManager(){
		return textManager;
	}
	
	public void writeText(String message, int id){
		switch(id){
		case 1:
			textArea1.append(message+newline);
			break;
		case 2:
			textArea2.append(message+newline);
		}
	}
	

	
	public void clearTextArea(int id){
		switch(id){
		case 1:
			textArea1.selectAll();
			textArea1.replaceSelection("");
			break;
		case 2: 
			textArea2.selectAll();
			textArea2.replaceSelection("");
		}
	}

	
	public void replaceLine(String message, int line){
		int beginning = 0, ending = 0;
		try {
			beginning =textArea1.getLineStartOffset(line);
			ending = textArea1.getLineEndOffset(line);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textArea1.replaceRange(message, beginning, ending);
	}
	
	public void removeFirstLineTextArea(int id) {
		JTextArea textArea = null;
		int end = 0;
		switch (id) {
		case 1:
			textArea = this.textArea1;
			break;
		case 2:
			textArea = this.textArea2;
		}

		try {
			end = textArea.getLineEndOffset(0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textArea.replaceRange("", 0, end);
	}
	
	public boolean textArea2ReachedLimit(){
		return textArea2.getLineCount()==linelimit;
	}
}
