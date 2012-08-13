package com.qhm123.fileselector;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class FileAdapter extends BaseAdapter {

	private static final String TAG = FileAdapter.class.getSimpleName();

	private ArrayList<FileInfo> mFileInfos = new ArrayList<FileInfo>();

	private Stack<FileInfo> mPathStack = new Stack<FileInfo>();

	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public FileAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mFileInfos.size();
	}

	@Override
	public FileInfo getItem(int position) {
		return mFileInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_explorer, null);
		}

		CheckedTextView filename = (CheckedTextView) v
				.findViewById(R.id.filename);
		FileInfo fileInfo = getItem(position);
		filename.setText(fileInfo.path);

		return v;
	}

	public void setPath(String path, boolean inStack) {
		mFileInfos.clear();

		File file = new File(path);
		Log.d(TAG, "size: " + file.listFiles());
		for (File item : file.listFiles()) {
			mFileInfos.add(new FileInfo(item.getPath()));
		}
		notifyDataSetChanged();

		if (inStack) {
			mPathStack.push(new FileInfo(path));
		}
	}

	public boolean back() {
		if (mPathStack.size() == 1) {
			return false;
		}
		mPathStack.pop();
		FileInfo fileInfo = mPathStack.peek();
		setPath(fileInfo.path, false);
		return true;
	}

}
