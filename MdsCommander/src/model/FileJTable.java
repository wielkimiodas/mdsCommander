package model;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import commander.gui.FileTableRenderer;

@SuppressWarnings("serial")
public class FileJTable extends JTable {

	private String currentPath;

	private FileTableModel fileTableModel = new FileTableModel();

	private Boolean selected = false;

	public FileJTable(FileTableModel fileTableModel, String path) {
		super(fileTableModel);
		this.currentPath = path;
		this.fileTableModel = fileTableModel;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDefaultRenderer(Object.class, new FileTableRenderer());
		getTableHeader().setReorderingAllowed(false);

		getActionMap().put("enterPressed", enterPressed);
		// getActionMap().put("tabPressed", tabPressed);

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
		// getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
		// KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tabPressed");

		refresh(path);

	}

	public void setSelected() {
		requestFocus();
		changeSelection(0, 0, false, false);
		selected = true;
	}

	public void setDeselected() {
		clearSelection();
		selected = false;
	}

	public FileTableModel getFileTableModel() {
		return fileTableModel;
	}

	public void refresh(String path) {
		currentPath = path;
		fileTableModel.setData(currentPath);
		fileTableModel.fireTableDataChanged();
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public Boolean isSelected() {
		return selected;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	private AbstractAction enterPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectedRow = getSelectedRow();
			CmdFileRow row = fileTableModel.getRowAt(selectedRow);
			String newPath = row.getLocation();
			if (row.getIsFolder()) {
				refresh(newPath);
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
	};

	// private AbstractAction tabPressed = new AbstractAction() {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// if (selected) {
	// setDeselected();
	// } else {
	// setSelected();
	// }
	//
	// }
	// };

}
