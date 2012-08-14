package com.qhm123.fileselector.adapters;

import java.io.File;

import com.qhm123.fileselector.FileInfo;

import android.content.Context;
import android.util.Log;

public class DirFileAdapter extends FileAdapter {

	public DirFileAdapter(Context context) {
		super(context);
	}

	@Override
	public void setPath(String path, boolean inStack) {
		mFileInfos.clear();

		File file = new File(path);
		Log.d(TAG, "size: " + file.listFiles());
		for (File item : file.listFiles()) {
			if (!item.canRead() && item.isDirectory()) {
				continue;
			}
			if (item.isDirectory()) {
				mFileInfos.add(new FileInfo(item.getPath()));
			}
		}
		notifyDataSetChanged();

		if (inStack) {
			mPathStack.push(new FileInfo(path));
		}
	}
}
