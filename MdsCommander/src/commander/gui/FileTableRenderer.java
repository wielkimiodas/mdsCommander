package commander.gui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;

import model.CmdFileRow;

@SuppressWarnings("serial")
public class FileTableRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component editor = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		JLabel editorLabeled = (JLabel) editor;

		if (column == 0) {
			FileSystemView view = FileSystemView.getFileSystemView();
			File f = new File(value.toString());
			// CmdFileRow cmdRow = new CmdFileRow(value.toString());
			// File f = new File(cmdRow.getLocation());
			Icon icon = view.getSystemIcon(f);
			editorLabeled.setIcon(icon);
			editorLabeled.setText(view.getSystemDisplayName(f));

		} else {
			editorLabeled.setIcon(null);
		}

		setOpaque(true);

		return editorLabeled;
	}
}
