package commander.controller;

import java.io.File;
import java.util.List;

import javax.sound.sampled.BooleanControl;
import javax.swing.JOptionPane;

public class FileManager {

	public static Boolean makeNewDirectory(String destPath, String name) {
		File f = new File(destPath + "\\" + name);

		return f.mkdir();
	}

	public static Boolean copyFiles(String destPath, List<File> fileList) {

		return true;
	}

	public static void removeFiles(List<File> fileList) {
		for (File f : fileList) {
			Boolean success = removeFile(f);
			if (!success) {
				String fileOrFolder;
				if (f.isDirectory())
					fileOrFolder = "directory";
				else
					fileOrFolder = "file";
				JOptionPane
						.showMessageDialog(null, "You cannot delete "
								+ fileOrFolder + " " + f.getName(),
								"MdsCommander error message",
								JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	private static Boolean removeFile(File f) {
		if (f.exists() && f.canWrite()) {
			if (f.isDirectory()) {
				Boolean directoryEmpty = true;
				for (File c : f.listFiles()) {
					Boolean success = removeFile(c);
					if (!success) {
						directoryEmpty = false;
						String fileOrFolder;
						if (c.isDirectory())
							fileOrFolder = "directory";
						else
							fileOrFolder = "file";
						JOptionPane.showMessageDialog(
								null,
								"You cannot delete " + fileOrFolder + " "
										+ c.getName(),
								"MdsCommander error message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			return f.delete();
		}
		return false;
	}

	public static Boolean moveFiles(String destPath, List<File> fileList) {
		return true;
	}
}
