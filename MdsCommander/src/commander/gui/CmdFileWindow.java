package commander.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import model.FileJTable;
import model.FileTableModel;

@SuppressWarnings("serial")
public class CmdFileWindow extends JPanel {
	private static String initialPath = "C:\\";

	private JScrollPane scroller;
	private FileJTable fileJTable;

	public FileJTable getFileJTable() {
		return fileJTable;
	}

	private JLabel pathLabel = new JLabel(initialPath);
	private JLabel summarizingDownLabel = new JLabel("tescior");

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

		Dimension minSize = new Dimension(0, 0);
		this.setMinimumSize(minSize);

		this.add(scroller, BorderLayout.CENTER);
		this.add(pathLabel, BorderLayout.NORTH);
		this.add(summarizingDownLabel, BorderLayout.SOUTH);
	}

	public void refreshPathLabel() {
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
}
