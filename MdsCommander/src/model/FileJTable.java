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
		getActionMap().put("spacePressed", spacePressed);
		getActionMap().put("leftArrowPressed", leftArrowPressed);
		getActionMap().put("rightArrowPressed", rightArrowPressed);

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");

		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "spacePressed");
		
		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftArrowPressed");
		
		getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightArrowPressed");

		
		getTableHeader().addMouseListener(new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int col = columnAtPoint(e.getPoint());
			
			
			System.out.println("kliknales "+ col);
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
			} else {
				Desktop d = Desktop.getDesktop();
				try {
					d.open(new File(row.getLocation()));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
						    "You cannot access this file","MdsCommander error message",
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

}
