package model;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class FileTableModel extends AbstractTableModel {

	String columnNames[] = { "Name", "Ext", "Size", "Date" };

	// Object[][] data;

	List<Object[]> data = new ArrayList<Object[]>();

	// = { { "file1", "txt", new Integer(200), new Date() },
	// { "abrakadabra", "jpg", new Integer(10000), new Date() },
	// { "rumcajs", "exe", new Integer(5324500), new Date() },
	// { "rumcajs", "exe", new Integer(5324500), new Date() } };

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
		return data.get(row)[col];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public void setData(String path) {

		File location = new File(path);

		File[] fileList = location.listFiles();

		for (int i = 0; i < fileList.length; i++) {
			Object[] fileListRow = new Object[4];
			File currFile = fileList[i];

			fileListRow[0] = getFileName(currFile);

			fileListRow[1] = getExtension(currFile);

			if (currFile.isFile()) {
				fileListRow[2] = fileList[i].length();
			} else {
				fileListRow[2] = "<DIR>";
			}

			fileListRow[3] = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.SHORT).format(
					new Date(fileList[i].lastModified()));

			this.data.add(i, fileListRow);
		}

		fireTableDataChanged();
	}

	private String getFileName(File file) {
		String name = file.getName();

		if (file.isFile()) {
			int i = name.lastIndexOf('.');
			if (i > 0)
				name = name.substring(0, i);
		}

		return name;
	}

	private String getExtension(File file) {
		String ext = "";

		if (file.isFile()) {
			String name = file.getName();
			int i = name.lastIndexOf('.');
			if (i > 0)
				ext = name.substring(i + 1);
		}

		return ext;
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.get(rowIndex)[columnIndex] = aValue;
	}

}
