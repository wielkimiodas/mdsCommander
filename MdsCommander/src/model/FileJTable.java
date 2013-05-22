package model;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import model.Comparators.FileComparators;

import commander.controller.FileManager;
import commander.gui.CmdFileWindow;
import commander.gui.FileTableRenderer;
import commander.gui.GuiCreator;

@SuppressWarnings("serial")
public class FileJTable extends JTable {

	private String currentPath = "test";
	private Comparators.FileComparators currentSort = FileComparators.NAME_ASC;

	public Comparators.FileComparators getCurrentSort() {
		return currentSort;
	}

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

		getActionMap().put("enterPressed", executeFileAction);
		getActionMap().put("spacePressed", selectFileAction);
		getActionMap().put("leftArrowPressed", leftArrowPressed);
		getActionMap().put("rightArrowPressed", rightArrowPressed);
		getActionMap().put("f7Pressed", makeNewFolderAction);
		getActionMap().put("delPressed", removeFileAction);
		getActionMap().put("f5Pressed", copyFileAction);

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

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delPressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "f5Pressed");

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
		// fileTableModel.refreshData();
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

	public List<File> getSelectedFiles() {
		List<File> selectedFilesList = new ArrayList<File>();

		for (CmdFileRow fileRow : fileTableModel.getData()) {
			if (fileRow.isSelected()) {
				selectedFilesList.add(fileRow.getBaseFile());
			}
		}
		return selectedFilesList;
	}

	private CmdFileRow getJTableSelectedRow() {
		int selectedRow = 0;

		selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			System.out.println("No file selected");
			return null;
		}
		return fileTableModel.getRowAt(selectedRow);
	}

	private AbstractAction executeFileAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CmdFileRow row = getJTableSelectedRow();
			if (row != null) {
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
		}
	};

	private AbstractAction selectFileAction = new AbstractAction() {

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

	private AbstractAction makeNewFolderAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File f = new File(currentPath + "\\" + "miodas");
			if (!f.exists()) {
				f.mkdir();
				fileTableModel.refreshData();
				System.out.println("nowy folder " + f.getAbsolutePath());
			} else {
				System.out.println("plik juz istnieje");
			}
		}
	};

	public List<File> getReallySelectedFiles() {
		List<File> fileList = getSelectedFiles();
		if (fileList.size() > 0) {
			if (fileList.get(0).getName() == "[..]") {
				fileList.remove(0);
			}
		} else {
			CmdFileRow row = getJTableSelectedRow();
			fileList.add(row.getBaseFile());
		}
		return fileList;
	}

	private AbstractAction removeFileAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			List<File> fileList = getReallySelectedFiles();

			if (fileList.size() > 0) {
				FileManager.removeFiles(fileList);
				fileTableModel.refreshData();
			}

		}
	};

	private AbstractAction copyFileAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<File> fileList = getReallySelectedFiles();
			System.out.println("kopiujê" + fileList.get(0).getAbsolutePath());
			FileManager.copyFiles("C:\\miodas", fileList);
			fileTableModel.refreshData();
		}
	};

}
