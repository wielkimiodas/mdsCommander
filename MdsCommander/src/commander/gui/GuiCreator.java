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

		JTable leftFileTable = createFileTable("D:\\");
		JScrollPane leftScroll = new JScrollPane(leftFileTable);
		leftScroll.getViewport().setBackground(
				UIManager.getColor("Table.background"));
		leftSide.add(leftScroll, BorderLayout.CENTER);

		JTable rightFileTable = createFileTable("C:\\");

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
		return splitterPanel;
	}

	@SuppressWarnings("serial")
	public static JTable createFileTable(String path) {

		final FileTableModel fileTableModel = new FileTableModel();
		fileTableModel.setData(path);

		final JTable fileTable = new JTable(fileTableModel);

		fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileTable.setDefaultRenderer(Object.class, new FileTableRenderer());

		fileTable.getTableHeader().setReorderingAllowed(false);

		// fileTable.addKeyListener(new KeyListener() {
		//
		// @Override
		// public void keyTyped(KeyEvent e) {
		// if (e.getKeyChar() == '\n') {
		// int selectedRow = fileTable.getSelectedRow();
		// CmdFileRow row = fileTableModel.getRowAt(selectedRow);
		// String newPath = row.getLocation();
		// fileTableModel.setData(newPath);
		// fileTableModel.fireTableDataChanged();
		// e.consume();
		// }
		//
		// if (e.getKeyChar() == '\t') {
		// int selectedRow = fileTable.getSelectedRow();
		// System.out.println(selectedRow
		// + fileTableModel.getRowAt(selectedRow).getName());
		// e.consume();
		// }
		//
		// }
		//
		// @Override
		// public void keyReleased(KeyEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void keyPressed(KeyEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// fileTable
		// .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		// im.remove(KeyStroke.getKeyStroke(Character.valueOf('\t')));

		fileTable.getActionMap().put("mds", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int selectedRow = fileTable.getSelectedRow();
				CmdFileRow row = fileTableModel.getRowAt(selectedRow);
				String newPath = row.getLocation();
				if (row.getIsFolder()) {
					fileTableModel.setData(newPath);
					fileTableModel.fireTableDataChanged();
				} else {
					Desktop d = Desktop.getDesktop();
					try {
						d.open(new File(row.getLocation()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		fileTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "mds");

		// im.put(KeyStroke.getKeyStroke(Character.valueOf('\t')), "mds");

		return fileTable;
	}
}
