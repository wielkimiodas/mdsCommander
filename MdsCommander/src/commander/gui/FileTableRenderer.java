package commander.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.DisplayMode;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.prefs.BackingStoreException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

		CmdFileRow currentFileRow = (CmdFileRow) value;

		switch (column) {
		case 0:
			FileSystemView view = FileSystemView.getFileSystemView();
			Icon icon = view.getSystemIcon(currentFileRow.getBaseFile());
			editorLabeled.setIcon(icon);
			setValue(currentFileRow.getName());
			setHorizontalAlignment(SwingConstants.LEFT);
			break;
		case 1:
			setValue(currentFileRow.getExtension());
			editorLabeled.setIcon(null);
			setHorizontalAlignment(SwingConstants.LEFT);
			break;
		case 2:
			Object v = currentFileRow.getFileSize();

			if (v != "<DIR>")
				setHorizontalAlignment(SwingConstants.RIGHT);
			setValue(v);

			setValue(currentFileRow.getFileSize());
			editorLabeled.setIcon(null);
			break;
		case 3:

			setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.SHORT).format(currentFileRow.getLastModified()));
			editorLabeled.setIcon(null);
			setHorizontalAlignment(SwingConstants.LEFT);
			break;
		}

		if (currentFileRow.isSelected()) {
			editorLabeled.setForeground(Color.red);
		} else {
			editorLabeled.setForeground(Color.black);
		}

		setOpaque(true);

		return editorLabeled;
	}
}
