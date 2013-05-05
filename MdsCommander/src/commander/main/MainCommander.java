package commander.main;

import javax.swing.JFrame;

import commander.gui.GuiCreator;

public class MainCommander {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Mds Commander");
		frame.getContentPane().add(GuiCreator.createMainPanel());
		frame.setJMenuBar(GuiCreator.createMainMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
