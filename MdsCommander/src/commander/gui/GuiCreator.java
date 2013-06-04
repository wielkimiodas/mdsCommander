package commander.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import commander.controller.FileManager;

import model.CommanderDataListener;
import model.FileJTable;

public class GuiCreator implements CommanderDataListener {

	private JPanel southPart;
	private JPanel northPart;
	private CmdFileWindow leftSide;
	private CmdFileWindow rightSide;
	private String downPath = "abc";
	private JLabel path = new JLabel();

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public JComponent createMainPanel() {
		final JPanel mainPanel = new JPanel();

		final JPanel splitterPanel = createSplitPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		mainPanel.add(splitterPanel, BorderLayout.CENTER);
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		return mainPanel;
	}

	private static JPanel createNorthPanel() {
		final JPanel panel = new JPanel(new MyLayout(FlowLayout.LEFT));
		ImageIcon test = new ImageIcon("res/refresh.png");

		for (int i = 0; i < 20; i++) {
			JButton imgBtn = new JButton();
			imgBtn.setIcon(test);
			imgBtn.setPreferredSize(new Dimension(16, 16));
			imgBtn.setBorder(BorderFactory.createEmptyBorder());
			imgBtn.setContentAreaFilled(false);
			panel.add(imgBtn);
		}

		return panel;
	}

	private JPanel createSouthPanel() {
		List<String> shortcuts = Arrays.asList("F3 Podgl¹d", "F4 Edycja",
				"F5 Kopiuj", "F6ZmPrzen", "F7 UtwKat", "F8 Usuñ",
				"Alt+F4 Zakoñcz");
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel executionPanel = new JPanel();

		path.setText(downPath);

		JTextField textbox = new JTextField();
		textbox.setMaximumSize(new Dimension(10, 10));

		path.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textbox.setAlignmentX(Component.RIGHT_ALIGNMENT);

		executionPanel
				.setLayout(new BoxLayout(executionPanel, BoxLayout.X_AXIS));

		final GridBagLayout gridbag = new GridBagLayout();
		final GridBagConstraints cc = new GridBagConstraints();
		executionPanel.setLayout(gridbag);

		cc.weightx = 1;
		cc.weighty = 1;
		cc.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(path, cc);
		executionPanel.add(path);

		cc.weightx = 5;
		cc.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(textbox, cc);
		// textbox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		executionPanel.add(textbox);

		GridLayout gl = new GridLayout(1, 6);
		gl.setHgap(5);
		gl.setVgap(5);

		JPanel shortcutsPanel = new JPanel();
		shortcutsPanel.setLayout(gl);

		for (String shortcut : shortcuts) {
			shortcutsPanel.add(new JLabel(shortcut));
		}

		southPanel.add(executionPanel, BorderLayout.NORTH);
		southPanel.add(shortcutsPanel, BorderLayout.SOUTH);

		return southPanel;
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

	public void refresh() {
		// leftSide.refresh() ->filejtable.refresh +right;

		if (leftSide.isSelected()) {
			downPath = leftSide.getFileJTable().getCurrentPath();
		} else {
			downPath = rightSide.getFileJTable().getCurrentPath();
		}

		path.setText(downPath);

	}

	public JPanel createSplitPanel() {

		JPanel splitterPanel = new JPanel();

		leftSide = new CmdFileWindow(this);
		rightSide = new CmdFileWindow(this);
		leftSide.setSelected();
		final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSide, rightSide);
		splitter.setOneTouchExpandable(true);
		splitter.setResizeWeight(0.5);

		splitterPanel.setLayout(new BorderLayout());
		splitterPanel.add(splitter, BorderLayout.CENTER);

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

	@Override
	public void CmdDataChanged() {
		refresh();

	}

}
