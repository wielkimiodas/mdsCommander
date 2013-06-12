package commander.controller;

import javax.swing.JFrame;

import commander.gui.GuiCreator;

public class MainCommander {

	/**
	 * @param args
	 */

	public static final JFrame frame = new JFrame("Mds Commander");

	public static void main(String[] args) {
		GuiCreator gc = new GuiCreator();
		frame.getContentPane().add(gc.createMainPanel());
		frame.setJMenuBar(GuiCreator.createMainMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
