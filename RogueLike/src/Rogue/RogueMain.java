package Rogue;

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
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


public class RogueMain extends JFrame implements KeyListener  {

	//TODO el texto a mostrar debe elegirse en base al languages.properties
	private AsciiPanel terminal;
	private Screen screen;
	private static JTextArea textArea1; //textarea for  sub-screens
	private static JTextArea textArea2; //textarea for playscreen
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private String language;

    private final static String newline = "\n";

	//TODO Buscar mejores colores
	private final static Color color = new Color(102, 102, 0);
	private final static Border border = BorderFactory.createLineBorder(Color.BLACK);
	
	//TODO ver donde encajo y como los datos de estado del personaje
	public RogueMain(){
		super();
		setResizable(false);
		screen = new StartScreen();
		setFocusable(true); //TODO mirar que hace esto
	    setLayout(new BorderLayout());
	    terminal = new AsciiPanel();
	    
	    //TEXTAREA 1******************************************
		textArea1 = init_textArea(terminal.getCharWidth(), terminal.getCharHeight());
		scroll1 = new JScrollPane(textArea1);
		int widht1 = 54* terminal.getCharWidth();
		int height1 = 23 * terminal.getCharHeight();
		scroll1.setPreferredSize(new Dimension(widht1, height1));
		scroll1.setAutoscrolls(true);
		add(scroll1, BorderLayout.EAST);
		
		
		
		
		//TEXTAREA 2 ***************************************
		//28 lineas
		textArea2 = init_textArea2(terminal.getCharWidth(), terminal.getCharHeight());
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
	
	
	public static JTextArea init_textArea(int w, int i) {
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

	public static JTextArea init_textArea2(int w, int i) { //TODO la barra vertical no aparece cuando deberia, el contenido del texarea esta limitado verticalmente lo cual no dbeeria ser
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
		screen.displayOutput(terminal, this.textArea1, this.textArea2);
		super.repaint();
	}
	
}
