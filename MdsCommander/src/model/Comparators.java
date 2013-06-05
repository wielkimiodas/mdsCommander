package model;

import java.util.Comparator;
import java.util.Date;

public class Comparators {

	public enum FileComparators {
		NAME_ASC, NAME_DESC, DATE_ASC, DATE_DESC, EXT_ASC, EXT_DESC, SIZE_ASC, SIZE_DESC
	}

	public static Comparator<CmdFileRow> getNameAscComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o1.getName().toLowerCase()
							.compareTo(o2.getName().toLowerCase());
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getNameDescComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o2.getName().toLowerCase()
							.compareTo(o1.getName().toLowerCase());
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getExtAscComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o1.getExtension().toLowerCase()
							.compareTo(o2.getExtension().toLowerCase());
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getExtDescComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o2.getExtension().toLowerCase()
							.compareTo(o1.getExtension().toLowerCase());
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getSizeAscComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = Long.valueOf(o1.getBaseFile().length()).compareTo(
							Long.valueOf(o2.getBaseFile().length()));
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getSizeDescComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = Long.valueOf(o2.getBaseFile().length()).compareTo(
							Long.valueOf(o1.getBaseFile().length()));
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getDateAscComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o1.getLastModified()
							.compareTo(o2.getLastModified());
				}
				return value;
			}

		};
	}

	public static Comparator<CmdFileRow> getDateDescComparator() {
		return new Comparator<CmdFileRow>() {

			@Override
			public int compare(CmdFileRow o1, CmdFileRow o2) {
				int value = 0;
				Boolean o1Folder = o1.isFolder();
				Boolean o2Folder = o2.isFolder();

				if (o1.getName() == "[..]")
					return -1;
				if (o2.getName() == "[..]")
					return 1;

				if (o1Folder && !o2Folder) {
					value = -1;
				}

				if (!o1Folder && o2Folder) {
					value = 1;
				}

				if (!(o1Folder ^ o2Folder)) {
					value = o2.getLastModified()
							.compareTo(o1.getLastModified());
				}
				return value;
			}

		};
	}

}
