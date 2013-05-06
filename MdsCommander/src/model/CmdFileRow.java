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

	public CmdFileRow(File file) {
		this.isFolder = file.isDirectory();
		this.location = file.getPath();
		setFileNameAndExt(file);
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

	public static Comparator<CmdFileRow> getNameComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder;
				Boolean o2Folder = o2.isFolder;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o1.name.compareTo(o2.name);
				}
				return value;
			}

		};
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

	private void setFileNameAndExt(File file) {
		String name = file.getName();
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
