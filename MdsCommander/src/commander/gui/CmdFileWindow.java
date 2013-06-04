package commander.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.FileSystem;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileSystemView;

import model.FileJTable;
import model.FileTableModel;

@SuppressWarnings("serial")
public class CmdFileWindow extends JPanel {
	private static String initialPath = File.listRoots()[0].getAbsolutePath();

	private JScrollPane scroller;
	private FileJTable fileJTable;
	private JComboBox<File> roots;

	public FileJTable getFileJTable() {
		return fileJTable;
	}

	private JLabel pathLabel = new JLabel(initialPath);
	private JLabel summarizingDownLabel = new JLabel("tescior");
	private JPanel northPanel = new JPanel(new BorderLayout());
	private JLabel driveInfo = new JLabel("drive info here");
	private GuiCreator parent;
	private CmdFileWindow otherFileWindow;

	public CmdFileWindow getOtherFileWindow() {
		return otherFileWindow;
	}

	public void setOtherFileWindow(CmdFileWindow otherFileWindow) {
		this.otherFileWindow = otherFileWindow;
	}

	public GuiCreator getMyParent() {
		return parent;
	}

	public String getPathLabel() {
		return pathLabel.getText();
	}

	public CmdFileWindow(GuiCreator parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		fileJTable = new FileJTable(this, new FileTableModel(), initialPath);
		fileJTable.addMouseListener(mouseAdapter);
		summarizingDownLabel.setText(fileJTable.getSummarizingDownLabel());
		scroller = new JScrollPane(fileJTable);
		scroller.getViewport().setBackground(
				UIManager.getColor("Table.background"));

		createCombobox();

		Dimension minSize = new Dimension(0, 0);
		this.setMinimumSize(minSize);

		refreshDriveData();
		northPanel.add(pathLabel, BorderLayout.SOUTH);
		northPanel.add(roots, BorderLayout.WEST);
		northPanel.add(driveInfo, BorderLayout.CENTER);

		this.add(scroller, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(summarizingDownLabel, BorderLayout.SOUTH);

	}

	public void refreshLabelAndSummary() {
		pathLabel.setText(fileJTable.getCurrentPath());
		summarizingDownLabel.setText(fileJTable.getSummarizingDownLabel());
	}

	public void setSelected() {
		fileJTable.setSelected();
	}

	public Boolean isSelected() {
		return fileJTable.isSelected();
	}

	public void setDeselected() {
		fileJTable.setDeselected();
	}

	private void createCombobox() {
		roots = new JComboBox<File>(File.listRoots());
		roots.addActionListener(cbListener);
	}

	private void refreshDriveData() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		DecimalFormat df = new DecimalFormat("###,###", symbols);

		File drive = new File(fileJTable.getCurrentPath().substring(0, 3));
		String freeSpace = df.format(drive.getFreeSpace() / 1024);
		String totalSpace = df.format(drive.getTotalSpace() / 1024);
		String driveDisplayName = FileSystemView.getFileSystemView()
				.getSystemDisplayName(drive);
		String driveLabel;
		if (!driveDisplayName.equals("")) {
			driveLabel = driveDisplayName.substring(0,
					driveDisplayName.lastIndexOf(' ')).toLowerCase();
		} else
			driveLabel = "none";
		driveInfo.setText("[" + driveLabel + "]" + " " + freeSpace + " k z "
				+ totalSpace + " k wolne");
	}

	ActionListener cbListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			File selectedDrive = (File) cb.getSelectedItem();
			String drive = selectedDrive.getAbsolutePath();
			fileJTable.refresh(drive);
			refreshLabelAndSummary();

			refreshDriveData();
		}
	};

	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			fileJTable.setSelected();
			otherFileWindow.fileJTable.setDeselected();
		};
	};

}
