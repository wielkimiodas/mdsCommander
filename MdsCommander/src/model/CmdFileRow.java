package model;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Date;

public class CmdFileRow {

	private String name;
	private String fileSize;
	private Date lastModified;
	private String extension;
	private Boolean isFolder;
	private String location;
	private Boolean selected = false;
	private File baseFile;

	public CmdFileRow(File file) {
		baseFile = file;
		this.isFolder = file.isDirectory();
		this.location = file.getAbsolutePath();
		setFileNameAndExt();
		setFileSize(file);
		setLastModified(file);
	}

	public CmdFileRow(File file, Boolean isBaseRow) {
		this(file);

		if (isBaseRow) {
			String path = file.getPath();
			int lastSlash = path.lastIndexOf('\\');
			if (lastSlash != -1) {
				String basePath = path.substring(0, lastSlash + 1);
				this.name = "[..]";
				this.location = basePath;
			}
		}
	}

	public Boolean isSelected() {
		return selected;
	}

	public File getBaseFile() {
		return baseFile;
	}

	public void select() {
		selected = !selected;
	}

	public String getName() {
		return name;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getLocation() {
		return location;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public String getExtension() {
		return extension;
	}

	public Boolean getIsFolder() {
		return isFolder;
	}

	

	private void setFileSize(File file) {
		if (this.isFolder) {
			this.fileSize = "<DIR>";
		} else {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(' ');
			DecimalFormat df = new DecimalFormat("###,###", symbols);
			this.fileSize = df.format(file.length());
		}
	}

	private void setFileNameAndExt() {
		String name = this.baseFile.getName();
		String ext = "";

		if (!this.isFolder) {
			int i = name.lastIndexOf('.');
			if (i > 0) {
				ext = name.substring(i + 1);
				name = name.substring(0, i);
			}
		} else {
			name = '[' + name + ']';
		}

		this.name = name;
		this.extension = ext;
	}

	private void setLastModified(File file) {
		this.lastModified = new Date(file.lastModified());
		// DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
		// DateFormat.SHORT).format(
		// new Date(fileList[i].lastModified()));
	}
}
