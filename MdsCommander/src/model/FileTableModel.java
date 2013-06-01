package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class FileTableModel extends AbstractTableModel {

	String columnNames[] = { "Name", "Ext", "Size", "Date" };
	private String currentPath;

	List<CmdFileRow> data = new ArrayList<CmdFileRow>();

	private long filesSize = 0;
	private long selectedFilesSize = 0;
	private int filesCount = 0;
	private int selectedFilesCount = 0;
	private int foldersCount = 0;
	private int selectedFoldersCount = 0;

	public int getFoldersCount() {
		return foldersCount;
	}

	public int getSelectedFoldersCount() {
		return selectedFoldersCount;
	}

	public long getFilesSize() {
		return filesSize / 1024;
	}

	public long getSelectedFilesSize() {
		return selectedFilesSize / 1024;
	}

	public int getFilesCount() {
		return filesCount;
	}

	public int getSelectedFilesCount() {
		return selectedFilesCount;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {

		// Object value = new Object();
		// switch (col) {
		// case 0:
		// value = data.get(row).getLocation();
		// break;
		// case 1:
		// value = data.get(row).getExtension();
		// break;
		// case 2:
		// value = data.get(row).getFileSize();
		// break;
		// case 3:
		// value = data.get(row).getLastModified();
		// break;
		// }
		return data.get(row);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public void setData(String path) {
		this.data.clear();
		foldersCount = 0;
		filesSize = 0;
		foldersCount = 0;
		filesCount = 0;

		File location = new File(path);
		File[] fileList = location.listFiles();

		if (path.length() > 3) {
			CmdFileRow first = new CmdFileRow(location, true);
			this.data.add(first);
		}
		// if (first != null) {
		// this.data.add(first);
		// }

		if (fileList == null) {

		} else {
			for (int i = 0; i < fileList.length; i++) {
				File currFile = fileList[i];
				CmdFileRow fileRow = new CmdFileRow(currFile);

				if (fileRow.getIsFolder()) {
					foldersCount++;
				} else {
					filesSize += fileRow.getBaseFile().length();
					filesCount++;
				}

				this.data.add(i, fileRow);
			}
			Collections.sort(this.data, Comparators.getNameAscComparator());
		}
		currentPath = path;
		fireTableDataChanged();
	}

	public List<CmdFileRow> getData() {
		return data;
	}

	public void sortData(Comparators.FileComparators cmp) {
		if (cmp == null)
			return;

		switch (cmp) {
		case NAME_ASC:
			Collections.sort(this.data, Comparators.getNameAscComparator());
			break;
		case NAME_DESC:
			Collections.sort(this.data, Comparators.getNameDescComparator());
			break;
		case DATE_ASC:
			Collections.sort(this.data, Comparators.getDateAscComparator());
			break;
		case DATE_DESC:
			Collections.sort(this.data, Comparators.getDateDescComparator());
			break;
		case SIZE_ASC:
			Collections.sort(this.data, Comparators.getSizeAscComparator());
			break;
		case SIZE_DESC:
			Collections.sort(this.data, Comparators.getSizeDescComparator());
			break;
		case EXT_ASC:
			Collections.sort(this.data, Comparators.getExtAscComparator());
			break;
		case EXT_DESC:
			Collections.sort(this.data, Comparators.getExtDescComparator());
			break;
		}

		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	public CmdFileRow getRowAt(int row) {
		CmdFileRow c = this.data.get(row);
		return c;
	}

	public void refreshData() {
		setData(currentPath);
	}
	// @Override
	// public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	// data.get(rowIndex)[columnIndex] = aValue;
	// }

}
