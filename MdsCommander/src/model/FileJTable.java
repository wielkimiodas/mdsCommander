package model;

import java.awt.Desktop;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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

	private List<CommanderDataListener> listeners = new ArrayList<CommanderDataListener>();

	public Comparators.FileComparators getCurrentSort() {
		return currentSort;
	}

	private String summarizingDownLabel = "nie dzia³a";

	public String getSummarizingDownLabel() {
		return summarizingDownLabel;
	}

	private FileTableModel fileTableModel;// = new FileTableModel();
	private CmdFileWindow cmdFileWindow;
	private Boolean selected = false;

	public FileJTable(CmdFileWindow cmdFileWindow,
			final FileTableModel fileTableModel, String path) {
		super(fileTableModel);
		this.currentPath = path;
		this.fileTableModel = fileTableModel;
		this.cmdFileWindow = cmdFileWindow;
		this.summarizingDownLabel = createDownLabelText();
		listeners.add(cmdFileWindow.getMyParent());

		setShowGrid(false);
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
		getActionMap().put("f6Pressed", moveFileAction);

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

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "f6Pressed");

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
		currentPath = path;

		fileTableModel.setData(currentPath);
		summarizingDownLabel = createDownLabelText();
		fileTableModel.fireTableDataChanged();
		if (selected)
			setSelection(true);

	}

	private String createDownLabelText() {
		String result = "";

		long fs = fileTableModel.getFilesSize();
		long sfs = fileTableModel.getSelectedFilesSize();
		int fic = fileTableModel.getFilesCount();
		int sfic = fileTableModel.getSelectedFilesCount();
		int foc = fileTableModel.getFoldersCount();
		int sfoc = fileTableModel.getSelectedFoldersCount();

		result = sfs + " k / " + fs + " k w " + sfic + " / " + fic
				+ " plik(ach/ów). " + sfoc + " / " + foc + " kat.(ów)";

		return result;
	}

	public void setSelection(Boolean selection, int row) {
		if (selection) {
			requestFocus();
			if (row >= this.getRowCount())
				row = this.getRowCount() - 1;
			changeSelection(row, 0, false, false);
			selected = true;
		} else {
			clearSelection();
			selected = false;
		}
	}

	public void setSelection(Boolean selection) {
		setSelection(selection, 0);
	}

	public FileTableModel getFileTableModel() {
		return fileTableModel;
	}

	public void refresh(String path) {
		currentPath = path;

		fileTableModel.setData(currentPath);
		summarizingDownLabel = createDownLabelText();
		fileTableModel.fireTableDataChanged();
		if (selected)
			setSelection(true);
		fireCommanderDataChanged();
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

	public void fireCommanderDataChanged() {
		for (CommanderDataListener listener : listeners)
			listener.CmdDataChanged();
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
				if (row.isFolder()) {
					if (!row.getBaseFile().canRead())
						return;
					refresh(newPath);
					cmdFileWindow.refreshLabelAndSummary();
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
			// fireCommanderDataChanged();

		}
	};

	private AbstractAction makeNewFolderAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String s = (String) JOptionPane.showInputDialog(null,
					"Name for the new directory \n", "New folder",
					JOptionPane.PLAIN_MESSAGE, null, null, null);

			if (s != "" && s != null) {
				FileManager.makeNewDirectory(currentPath, s);
				fileTableModel.refreshData();
				cmdFileWindow.getOtherFileWindow().getFileJTable().fileTableModel
						.refreshData();
				setSelection(true);
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

			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure to delete selected file(s)?",
					"Mds Commander", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				int row = getSelectedRow();
				List<File> fileList = getReallySelectedFiles();

				if (fileList.size() > 0) {
					FileManager fm = new FileManager();
					fm.removeFiles(fileList);
					fileTableModel.refreshData();

					fileTableModel.refreshData();
					cmdFileWindow.getOtherFileWindow().getFileJTable().fileTableModel
							.refreshData();
					setSelection(true, row);
				}
			}
		}
	};

	private AbstractAction copyFileAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int row = getSelectedRow();
			List<File> fileList = getReallySelectedFiles();
			// System.out.println("kopiujê" +
			// fileList.get(0).getAbsolutePath());
			String destPath = cmdFileWindow.getOtherFileWindow()
					.getFileJTable().currentPath;
			FileManager fm = new FileManager();
			fm.copyFiles(destPath, fileList);

			fileTableModel.refreshData();
			cmdFileWindow.getOtherFileWindow().getFileJTable().fileTableModel
					.refreshData();
			System.out.println("refr succs");
			setSelection(true, row);
		}
	};

	private AbstractAction moveFileAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int row = getSelectedRow();
			List<File> fileList = getReallySelectedFiles();
			String destPath = cmdFileWindow.getOtherFileWindow()
					.getFileJTable().currentPath;
			FileManager fm = new FileManager();
			fm.moveFiles(destPath, fileList);

			try {
				Thread.currentThread().sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("0,5 sec");

			fileTableModel.refreshData();
			cmdFileWindow.getOtherFileWindow().getFileJTable().fileTableModel
					.refreshData();
			setSelection(true, row);

		}
	};

}
