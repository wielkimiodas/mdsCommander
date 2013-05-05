package commander.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import model.FileTableModel;

public class GuiCreator {

	public static JComponent createMainPanel() {
		final JPanel mainPanel = new JPanel();

		final JPanel splitterPanel = createSplitPanel();

		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(createPanel(Color.red, 150, 150), BorderLayout.SOUTH);

		mainPanel.add(splitterPanel, BorderLayout.CENTER);

		mainPanel.add(createPanel(Color.blue, 50, 150), BorderLayout.NORTH);

		return mainPanel;
	}

	private static JPanel createPanel(Color color, int width, int height) {
		final JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setSize(width, height);

		// final JButton button = new JButton(text);
		// button.setPreferredSize(new Dimension(width, height));
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

	public static JPanel createSplitPanel() {

		JPanel splitterPanel = new JPanel();
		JPanel leftSide = new JPanel(new BorderLayout());
		JPanel rightSide = new JPanel(new BorderLayout());

		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		JButton bt1 = new JButton("taki");
		JButton bt3 = new JButton("baton");

		leftSide.add(bt1, BorderLayout.NORTH);
		leftSide.add(bt3, BorderLayout.SOUTH);

		JTable rightFileTable = createFileTable("C:\\");
		rightSide.add(rightFileTable, BorderLayout.CENTER);

		Dimension minSize = new Dimension(0, 0);
		leftSide.setMinimumSize(minSize);
		rightSide.setMinimumSize(minSize);

		final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(leftSide), new JScrollPane(rightSide));
		splitter.setOneTouchExpandable(true);

		splitterPanel.setLayout(new BorderLayout());
		splitterPanel.add(splitter, BorderLayout.CENTER);
		return splitterPanel;
	}

	public static JTable createFileTable(String path) {

		FileTableModel fileTableModel = new FileTableModel();
		fileTableModel.setData(path);
		JTable fileTable = new JTable(fileTableModel);

		fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileTable.setDefaultRenderer(Object.class, new FileTableRenderer());

		return fileTable;
	}
}
