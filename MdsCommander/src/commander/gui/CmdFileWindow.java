package commander.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import model.FileJTable;
import model.FileTableModel;

@SuppressWarnings("serial")
public class CmdFileWindow extends JPanel {
	private static String initialPath = File.listRoots()[0].getAbsolutePath();

	private JScrollPane scroller;
	private FileJTable fileJTable;
	private JComboBox<File> roots;

	public FileJTable getFileJTable() {
		return fileJTable;
	}

	private JLabel pathLabel = new JLabel(initialPath);
	private JLabel summarizingDownLabel = new JLabel("tescior");
	private JPanel northPanel = new JPanel(new BorderLayout());
	private JLabel driveInfo = new JLabel("drive info here");

	public String getPathLabel() {
		return pathLabel.getText();
	}

	public CmdFileWindow() {
		this.setLayout(new BorderLayout());
		fileJTable = new FileJTable(this, new FileTableModel(), initialPath);
		summarizingDownLabel.setText(fileJTable.getSummarizingDownLabel());
		scroller = new JScrollPane(fileJTable);
		scroller.getViewport().setBackground(
				UIManager.getColor("Table.background"));

		createCombobox();

		Dimension minSize = new Dimension(0, 0);
		this.setMinimumSize(minSize);
		JLabel m = new JLabel("mjodas");
		northPanel.add(pathLabel, BorderLayout.SOUTH);
		northPanel.add(roots, BorderLayout.WEST);
		northPanel.add(driveInfo, BorderLayout.CENTER);

		this.add(scroller, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(summarizingDownLabel, BorderLayout.SOUTH);
	}

	public void refreshLabelAndSummary() {
		pathLabel.setText(fileJTable.getCurrentPath());
		summarizingDownLabel.setText(fileJTable.getSummarizingDownLabel());
	}

	public void setSelected() {
		fileJTable.setSelected();
	}

	public Boolean isSelected() {
		return fileJTable.isSelected();
	}

	public void setDeselected() {
		fileJTable.setDeselected();
	}

	private void createCombobox() {
		roots = new JComboBox<File>(File.listRoots());
		roots.addActionListener(cbListener);
	}

	ActionListener cbListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String drive = ((File) cb.getSelectedItem()).getAbsolutePath();
			fileJTable.refresh(drive);
			refreshLabelAndSummary();
		}
	};

}
