package commander.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.swing.SwingWorker;

public class FileManager {

	public static Boolean makeNewDirectory(String destPath, String name) {

		File f = new File(destPath + "\\" + name);
		if (!f.exists()) {
			f.mkdir();
			System.out.println("nowy folder " + f.getAbsolutePath());
		} else {
			System.out.println("folder juz istnieje");
		}

		return f.mkdir();
	}

	public static Boolean copyFiles(final String destination,
			final List<File> fileList) {

		new SwingWorker<Object, String>() {

			@Override
			protected Object doInBackground() throws Exception {
				try {
					for (File file : fileList) {
						copyFile(destination, file);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return null;
			}

		}.execute();
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

	@SuppressWarnings("unused")
	private static Boolean copyFile(String dest, File src) {
		InputStream inStream = null;
		OutputStream outStream = null;
		File afile;
		File bfile;
		try {

			if (src.isDirectory()) {
				makeNewDirectory(dest, src.getName());

				for (File f : src.listFiles()) {
					copyFile(dest + "\\" + src.getName(), f);
				}
			}

			afile = src;
			String srcPath = src.getAbsolutePath();
			String destName = dest + "\\"
					+ srcPath.substring(srcPath.lastIndexOf('\\'));

			bfile = new File(destName);
			if (afile.canExecute() && afile.canRead()) {

				System.out.println("kopiuje: " + afile.getAbsolutePath()
						+ " do " + bfile.getAbsolutePath());

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {
					outStream.write(buffer, 0, length);
				}
				inStream.close();
				outStream.close();
				System.out.println("File is copied successful!");
			} else {
				JOptionPane
						.showMessageDialog(null, "Access denied",
								"MdsCommander error message",
								JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static Boolean moveFiles(String destPath, List<File> fileList) {
		copyFiles(destPath, fileList);
		removeFiles(fileList);
		return true;
	}
}
