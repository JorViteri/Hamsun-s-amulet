package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

	private final static String newline = "\n";

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea1, JTextArea textArea2) {
		writeText();
		terminal.clear();
		terminal.writeCenter("roguelike ayuda", 1);
		terminal.write("Desciende por las mazmorras de Uqbar, encuentra el amuleto perdido de Hamsun,",1, 3);
		terminal.write("y regresa a la superficie para ganar. Emplea lo que encuentres para evitar", 1, 4);
		terminal.write("la muerte.",1,5);
		int y = 6;
		terminal.write("[g] or [,] to pick up", 2, y++);
		terminal.write("[d] to drop", 2, y++);
		terminal.write("[e] to eat", 2, y++);
		terminal.write("[w] to wear or wield", 2, y++);
		terminal.write("[?] for help", 2, y++);
		terminal.write("[x] to examine your items", 2, y++);
		terminal.write("[;] to look around", 2, y++);
		terminal.write("[f] to fire a projectile", 2, y++);
		terminal.write("[t] to thow an object", 2, y++);
		terminal.write("[q] to quaff a potion", 2, y++);
		terminal.write("[r] to read something", 2, y++);

		terminal.writeCenter("-- press any key to continue --", 22);
	}

	private void writeText() {
		TextManager textManager = TextManager.getTextManager();
		textManager.clearTextArea(1);
		textManager.writeText("roguelike ayuda", 1);
		textManager.writeText("Desciende por las mazmorras de Uqbar, encuentra el amuleto perdido de Hamsun, y regresa a la superficie para ganar. Emplea lo que encuentres para evitar la muerte.", 1);
		textManager.writeText("[g] or [,] to pick up", 1);
		textManager.writeText("[d] to drop", 1);
		textManager.writeText("[e] to eat", 1);
		textManager.writeText("[w] to wear or wield", 1);
		textManager.writeText("[?] for help", 1);
		textManager.writeText("[x] to examine your items", 1);
		textManager.writeText("[;] to look around", 1);
		textManager.writeText("[f] to fire a projectile", 1);
		textManager.writeText("[t] to thow an object", 1);
		textManager.writeText("[q] to quaff a potion", 1);
		textManager.writeText("[r] to read something", 1);
		textManager.writeText("-- press any key to continue --", 1);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}

}
