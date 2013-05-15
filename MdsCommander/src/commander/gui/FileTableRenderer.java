package commander.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.DisplayMode;
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
			// boolean fst = false;
			FileSystemView view = FileSystemView.getFileSystemView();
			String location = value.toString();
			// System.out.println(location.substring(location.length() - 1));
			// if (location.substring(location.length() - 1).equals("#")) {

			File f = new File(location);
			String aPath = f.getAbsolutePath();
			Icon icon = view.getSystemIcon(f);
			editorLabeled.setIcon(icon);
			String displayedName = view.getSystemDisplayName(f);

			// if (fst) {
			// displayedName = "..";
			// }

			editorLabeled.setText("[" + displayedName + "]");

		} else {
			editorLabeled.setIcon(null);
		}

		setOpaque(true);

		return editorLabeled;
	}
}
