package commander.gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class FileTableRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component editor = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		// editor.setBackground((row % 2) == 0 ? Color.white : Color.lightGray);

		// if (column == 2) {
		// table.getModel().setValueAt(
		// processFileLength((long) table.getModel().getValueAt(row,
		// column)), row, column);
		//
		// }
		setOpaque(true);

		return editor;
	}

	private String processFileLength(long length) {
		String sLen = Long.toString(length);

		for (int i = sLen.length() % 3; i < sLen.length(); i = i + 3) {
			sLen = sLen.substring(0, i) + " "
					+ sLen.substring(i, sLen.length());
		}

		return sLen;
	}
}
