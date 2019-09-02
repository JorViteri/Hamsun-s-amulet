package TextManagement;
/**
 * Class that manages the printing of messages in the JTextAreas
 */
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class TextManager {
	
	private static TextManager textManager; 
	private final static String newline = "\n";
	private JTextArea textArea1;
	private JTextArea textArea2;
	private final static int linelimit=28;
	
	/**
	 * Constructo
	 * @param textArea1 superior right JTextArea
	 * @param textArea2 bottom JTextArea
	 */
	private TextManager(JTextArea textArea1, JTextArea textArea2){
		this.textArea1 = textArea1;
		this.textArea2 = textArea2;
	}
	
	/**
	 * Singleton function
	 * @param textArea1
	 * @param textArea2
	 * @return
	 */
	public static TextManager getSingletonInstance(JTextArea textArea1, JTextArea textArea2){
		if(textManager==null){
			textManager = new TextManager(textArea1, textArea2);
		} 
		return textManager;
	}

	/**
	 * Gets the instance of the text Manager if sure it's exists 
	 * @return
	 */
	public static TextManager getTextManager(){
		return textManager;
	}
	
	/**
	 * Pass text to write 
	 * @param message string to write
	 * @param id for selecting the JTextAre to write
	 */
	public void writeText(String message, int id){
		switch(id){
		case 1:
			textArea1.append(message+newline);
			textArea1.setCaretPosition(textArea1.getDocument().getLength());
			break;
		case 2:
			textArea2.append(message+newline);
			textArea2.setCaretPosition(textArea2.getDocument().getLength());
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
