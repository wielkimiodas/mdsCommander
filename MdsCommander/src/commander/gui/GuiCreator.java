package commander.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import model.FileJTable;

public class GuiCreator {

	private CmdFileWindow leftSide;
	private CmdFileWindow rightSide;

	public JComponent createMainPanel() {
		final JPanel mainPanel = new JPanel();

		final JPanel splitterPanel = createSplitPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(createTestPanel(Color.red, 150, 150), BorderLayout.SOUTH);
		mainPanel.add(splitterPanel, BorderLayout.CENTER);
		mainPanel.add(createTestPanel(Color.blue, 50, 150), BorderLayout.NORTH);
		return mainPanel;
	}

	private static JPanel createTestPanel(Color color, int width, int height) {
		final JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setSize(width, height);
		return panel;
	}

	public static JMenuBar createMainMenuBar() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem closeItem = new JMenuItem("Close");
		closeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		fileMenu.add(closeItem);

		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(new JMenu("Edit"));
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(new JMenu("Help"));
		return menuBar;
	}

	public JPanel createSplitPanel() {

		JPanel splitterPanel = new JPanel();
		leftSide = new CmdFileWindow();
		rightSide = new CmdFileWindow();

		final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSide, rightSide);
		splitter.setOneTouchExpandable(true);
		splitter.setResizeWeight(0.5);

		splitterPanel.setLayout(new BorderLayout());
		splitterPanel.add(splitter, BorderLayout.CENTER);

		leftSide.setSelected();

		FileJTable leftJTable = leftSide.getFileJTable();
		leftJTable.getActionMap().put("tabPressed", tabPressed);
		leftJTable
				.getInputMap(CmdFileWindow.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tabPressed");
		FileJTable rightJTable = rightSide.getFileJTable();
		rightJTable.getActionMap().put("tabPressed", tabPressed);
		rightJTable.getInputMap(
				CmdFileWindow.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tabPressed");

		return splitterPanel;
	}

	@SuppressWarnings("serial")
	private AbstractAction tabPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (rightSide.isSelected()) {
				rightSide.setDeselected();
				leftSide.setSelected();
			} else {
				rightSide.setSelected();
				leftSide.setDeselected();
			}
		}
	};

}
