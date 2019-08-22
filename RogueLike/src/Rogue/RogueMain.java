package Rogue;

/**
 * Main class of the game which deals with the generation of the window and the inizialization of the Text Generator7
 * 
 * @author comec
 */
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Screens.Screen;
import Screens.StartScreen;
import TextManagement.TextManager;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class RogueMain extends JFrame implements KeyListener  {

	private static final long serialVersionUID = -1974790262751795811L;
	private AsciiPanel terminal;
	private Screen screen;
	private static JTextArea textArea1; //textarea for  sub-screens
	private static JTextArea textArea2; //textarea for playscreen
	private JScrollPane scroll1;
	private JScrollPane scroll2;

	private final static Color color = new Color(102, 102, 0);
	private final static Border border = BorderFactory.createLineBorder(Color.BLACK);
	
	/**
	 * Construtor which iniziates the window of the game and it's components
	 */
	public RogueMain(){
		super();
		setResizable(false);
		screen = new StartScreen();
		setFocusable(true); //TODO mirar que hace esto
	    setLayout(new BorderLayout());
	    terminal = new AsciiPanel();
	    
	    //TEXTAREA 1******************************************
		textArea1 = init_textArea();
		scroll1 = new JScrollPane(textArea1);
		int widht1 = 54* terminal.getCharWidth();
		int height1 = 23 * terminal.getCharHeight();
		scroll1.setPreferredSize(new Dimension(widht1, height1));
		scroll1.setAutoscrolls(true);
		add(scroll1, BorderLayout.EAST);
		
		//TEXTAREA 2 ***************************************
		//28 lineas
		textArea2 = init_textArea2();
		scroll2 = new JScrollPane(textArea2);
		int width = 135 * terminal.getCharWidth();
		int height = 22 * terminal.getCharHeight();
		scroll2.setPreferredSize(new Dimension(width, height));
		scroll2.setAutoscrolls(true);
		//add(textArea2, BorderLayout.SOUTH);
		add(scroll2, BorderLayout.SOUTH );
		
		//Terminal****************************************
		
		add(terminal, BorderLayout.CENTER);
		int w = 135 * terminal.getCharWidth();
		int h = 48 * terminal.getCharHeight();
		Dimension panelDimension = new Dimension(w,h);
		setSize(panelDimension);
    	setMinimumSize(panelDimension);
    	setPreferredSize(panelDimension);
		pack(); 
		addKeyListener(this);
		repaint();
	}
	
	/**
	 * Iniziates the JTextArea that will be in the superior right part of the window
	 * @return the JTextArea created
	 */
	public static JTextArea init_textArea() {
		final JTextArea textA = new JTextArea();
		textA.setFont(new Font("Mincho", Font.PLAIN, 16));
		textA.setBackground(color);
		textA.setForeground(Color.white);
    	textA.setVisible(true);
    	textA.setLineWrap(true);
    	textA.setWrapStyleWord(true);
    	textA.setEditable(true);
    	textA.setFocusable(false);
		textA.setHighlighter(null);
		textA.setLayout(new BorderLayout());
		textA.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		return textA;
	}

	/**
	 * Iniziates the JTextArea that will be in the bottom part of the window
	 * @return the JTextArea created
	 */
	public static JTextArea init_textArea2() { 
		final JTextArea textA = new JTextArea();
		textA.setFont(new Font("Maru Gothic", Font.PLAIN, 16));
		textA.setBackground(color);
		textA.setForeground(Color.white);
		textA.setVisible(true);
		textA.setLineWrap(true);
    	textA.setWrapStyleWord(true);
		textA.setEditable(true);
		textA.setFocusable(false);
		textA.setHighlighter(null);
		textA.setLayout(new BorderLayout());
		textA.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		return textA;
	}
	
	public void keyPressed(KeyEvent e){
		screen = screen.respondToUserInput(e);
		repaint();
	}
	
	public void keyReleased(KeyEvent e){}
	
	public void keyTyped(KeyEvent e){}
	
	/**
	 * Main function that starts the game and inizialites the Text generator
	 * @param args not used
	 */
	public static void main(String[] args){
		RogueMain rogue = new RogueMain(); 
		TextManager.getSingletonInstance(textArea1, textArea2);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		factory.getWordDataGetter();
		factory.getRealizator();
		rogue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rogue.setVisible(true);
	}
	
	
	public void repaint(){
		terminal.clear();
		screen.displayOutput(terminal, textArea1, textArea2);
		super.repaint();
	}
	
}
