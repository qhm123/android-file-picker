package com.qhm123.fileselector;

import java.io.File;

public class FileInfo {

	public String path;
	public String name;
	public boolean isDir;
	public int scrollY;

	public FileInfo(String path) {
		this.path = path;
		File f = new File(path);
		this.name = f.getName();
		this.isDir = f.isDirectory();

	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		return ((FileInfo) o).path.equals(this.path);
	}
}
