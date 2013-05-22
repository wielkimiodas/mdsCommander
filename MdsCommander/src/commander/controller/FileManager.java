package commander.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.List;

import javax.sound.sampled.BooleanControl;
import javax.swing.JOptionPane;

public class FileManager {

	public static Boolean makeNewDirectory(String destPath, String name) {
		File f = new File(destPath + "\\" + name);

		return f.mkdir();
	}

	public static Boolean copyFiles(String destination, List<File> fileList) {

		for (File file : fileList) {
			Path srcPath = Paths.get(file.getAbsolutePath());
			Path destPath = Paths.get(new File(destination).getAbsolutePath());
			try {
				// Files.walkFileTree(srcPath,
				// EnumSet.of(FileVisitOption.FOLLOW_LINKS),Integer.MAX_VALUE,
				// Files.copy(srcPath, destPath,
				// StandardCopyOption.REPLACE_EXISTING));

				Files.copy(srcPath, destPath,
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static void removeFiles(List<File> fileList) {

		int result = JOptionPane.showConfirmDialog(null,
				"Are you sure to delete selected file(s)?", "Mds Commander",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {

			for (File f : fileList) {
				Boolean success = removeFile(f);
				if (!success) {
					String fileOrFolder;
					if (f.isDirectory())
						fileOrFolder = "directory";
					else
						fileOrFolder = "file";
					JOptionPane.showMessageDialog(null, "You cannot delete "
							+ fileOrFolder + " " + f.getName(),
							"MdsCommander error message",
							JOptionPane.ERROR_MESSAGE);
				}
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
