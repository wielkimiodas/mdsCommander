package model;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import model.Comparators.FileComparators;

import commander.gui.CmdFileWindow;
import commander.gui.FileTableRenderer;
import commander.gui.GuiCreator;

@SuppressWarnings("serial")
public class FileJTable extends JTable {

	private String currentPath = "test";
	private Comparators.FileComparators currentSort = FileComparators.NAME_ASC;

	private FileTableModel fileTableModel = new FileTableModel();
	private CmdFileWindow cmdFileWindow;
	private Boolean selected = false;

	public FileJTable(CmdFileWindow cmdFileWindow,
			final FileTableModel fileTableModel, String path) {
		super(fileTableModel);
		this.currentPath = path;
		this.fileTableModel = fileTableModel;
		this.cmdFileWindow = cmdFileWindow;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDefaultRenderer(Object.class, new FileTableRenderer());
		getTableHeader().setReorderingAllowed(false);

		getActionMap().put("enterPressed", enterPressed);
		getActionMap().put("spacePressed", spacePressed);
		getActionMap().put("leftArrowPressed", leftArrowPressed);
		getActionMap().put("rightArrowPressed", rightArrowPressed);
		getActionMap().put("f7Pressed", f7Pressed);

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "spacePressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
						"leftArrowPressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
				"rightArrowPressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "f7Pressed");

		getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int col = columnAtPoint(e.getPoint());
				Comparators.FileComparators newSort = null;
				switch (col) {
				case 0:
					if (currentSort == FileComparators.NAME_ASC) {
						newSort = FileComparators.NAME_DESC;
					} else {
						newSort = FileComparators.NAME_ASC;
					}
					break;
				case 1:
					if (currentSort == FileComparators.EXT_ASC) {
						newSort = FileComparators.EXT_DESC;
					} else {
						newSort = FileComparators.EXT_ASC;
					}
					break;

				case 2:
					if (currentSort == FileComparators.SIZE_ASC) {
						newSort = FileComparators.SIZE_DESC;
					} else {
						newSort = FileComparators.SIZE_ASC;
					}
					break;

				case 3:
					if (currentSort == FileComparators.DATE_ASC) {
						newSort = FileComparators.DATE_DESC;
					} else {
						newSort = FileComparators.DATE_ASC;
					}
					break;

				}

				fileTableModel.sortData(newSort);
				currentSort = newSort;
			}
		});

		setRowSelectionAllowed(true);
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
		if (selected)
			setSelected();

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

			int selectedRow = 0;
			try {
				selectedRow = getSelectedRow();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("No file selected");
				return;
			}

			CmdFileRow row = fileTableModel.getRowAt(selectedRow);
			String newPath = row.getLocation();
			if (row.getIsFolder()) {
				refresh(newPath);
				cmdFileWindow.refreshPathLabel();
			} else {
				Desktop d = Desktop.getDesktop();
				try {
					d.open(new File(row.getLocation()));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"You cannot access this file",
							"MdsCommander error message",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		}
	};

	private AbstractAction spacePressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectedRow = getSelectedRow();
			CmdFileRow row = fileTableModel.getRowAt(selectedRow);
			row.select();
			fileTableModel.fireTableRowsUpdated(selectedRow, selectedRow);

		}
	};

	private AbstractAction leftArrowPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}
	};

	private AbstractAction rightArrowPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}
	};

	private AbstractAction f7Pressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File f = new File(currentPath + "miodas");
			if (f.isFile()) {
				f.mkdir();
				fileTableModel.refreshData();
				System.out.println("nowy folder");
			} else {
				System.out.println("plik juz istnieje");
			}

		}
	};

	private AbstractAction delPressed = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}
	};

}
