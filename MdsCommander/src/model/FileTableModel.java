package model;

import java.io.File;
import java.util.ArrayList;
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
			this.data.add(i, fileList);
		}

		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

}
