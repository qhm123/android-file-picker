package com.qhm123.fileselector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfo {

	public String path;
	public String name;
	public boolean isDir;
	public String size;
	public String lastModified;

	//
	public int scrollY;
	public boolean isChecked;
	public int position;

	public FileInfo(String path) {
		this.path = path;
		File f = new File(path);
		this.name = f.getName();
		this.isDir = f.isDirectory();
		this.size = formatSize(f.length());
		this.lastModified = sSimpleDateFormat
				.format(new Date(f.lastModified()));
	}

	public static String formatSize(float size) {
		long kb = 1024;
		long mb = (kb * 1024);
		long gb = (mb * 1024);
		if (size < kb) {
			return String.format("%d B", (int) size);
		} else if (size < mb) {
			return String.format("%.2f KB", size / kb); // 保留两位小数
		} else if (size < gb) {
			return String.format("%.2f MB", size / mb);
		} else {
			return String.format("%.2f GB", size / gb);
		}
	}

	private SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		return ((FileInfo) o).path.equals(this.path);
	}
}
