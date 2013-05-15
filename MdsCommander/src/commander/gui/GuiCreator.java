package commander.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import model.CmdFileRow;
import model.FileJTable;
import model.FileTableModel;

public class GuiCreator {

	public static String leftSideInitialPath = "C:\\";
	public static String rightSideInitialPath = "C:\\";

	private FileJTable leftFileTable;
	private FileJTable rightFileTable;

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
		JPanel leftSide = new JPanel(new BorderLayout());
		JPanel rightSide = new JPanel(new BorderLayout());

		leftFileTable = new FileJTable(new FileTableModel(),
				leftSideInitialPath);
		JScrollPane leftScroll = new JScrollPane(leftFileTable);
		leftScroll.getViewport().setBackground(
				UIManager.getColor("Table.background"));
		leftSide.add(leftScroll, BorderLayout.CENTER);

		rightFileTable = new FileJTable(new FileTableModel(),
				rightSideInitialPath);

		JLabel leftLabel = createPathLabel(leftFileTable.getCurrentPath());

		leftSide.add(leftLabel, BorderLayout.NORTH);

		JScrollPane rightScroll = new JScrollPane(rightFileTable);
		rightScroll.getViewport().setBackground(
				UIManager.getColor("Table.background"));
		rightSide.add(rightScroll, BorderLayout.CENTER);

		Dimension minSize = new Dimension(0, 0);
		leftSide.setMinimumSize(minSize);
		rightSide.setMinimumSize(minSize);

		final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSide, rightSide);
		splitter.setOneTouchExpandable(true);
		// splitter.setDividerLocation(splitterPanel.getSize().width / 2);

		splitterPanel.setLayout(new BorderLayout());
		splitterPanel.add(splitter, BorderLayout.CENTER);

		rightFileTable.setSelected();

		rightFileTable.getActionMap().put("tabPressed", tabPressed);
		leftFileTable.getActionMap().put("tabPressed", tabPressed);

		rightFileTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tabPressed");

		leftFileTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tabPressed");

		return splitterPanel;
	}

	private JLabel createPathLabel(String path) {
		return new JLabel(path);
	}

	@SuppressWarnings("serial")
	private FileJTable createFileTable(String path) {

		return null;
	}

	private AbstractAction tabPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (leftFileTable.isSelected()) {
				leftFileTable.setDeselected();
				rightFileTable.setSelected();
			} else {
				leftFileTable.setSelected();
				rightFileTable.setDeselected();
			}

		}
	};

}
