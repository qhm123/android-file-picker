package com.qhm123.fileselector.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.qhm123.fileselector.FileInfo;
import com.qhm123.fileselector.R;

public class FileAdapter extends BaseAdapter {

	protected static final String TAG = FileAdapter.class.getSimpleName();

	protected ArrayList<FileInfo> mFileInfos = new ArrayList<FileInfo>();

	protected Stack<FileInfo> mPathStack = new Stack<FileInfo>();

	private SparseBooleanArray mCheckedArray = new SparseBooleanArray();

	protected Context mContext;
	protected LayoutInflater mLayoutInflater;

	protected ItemCheckListener mItemCheckListener;

	public interface ItemCheckListener {
		void onItemCheckListener(int position, boolean isChecked);
	}

	public FileAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setItemCheckListener(ItemCheckListener l) {
		mItemCheckListener = l;
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

	public SparseBooleanArray getCheckedItemPositions() {
		return mCheckedArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_explorer, null);
		}

		FileInfo fileInfo = getItem(position);
		fileInfo.position = position;

		TextView filename = (TextView) v.findViewById(R.id.filename);
		filename.setText(fileInfo.name);
		TextView filemodifydate = (TextView) v
				.findViewById(R.id.filemodifydate);
		filemodifydate.setText(fileInfo.lastModified);
		TextView filesize = (TextView) v.findViewById(R.id.filesize);
		filesize.setText(fileInfo.size);
		ImageView icon = (ImageView) v.findViewById(R.id.filetype);
		icon.setImageResource(fileInfo.isDir ? R.drawable.folder
				: R.drawable.file_icon_default);
		CheckBox checkbox = (CheckBox) v.findViewById(R.id.checkbox);
		if (!isCheckBoxVisibile()) {
			checkbox.setVisibility(View.GONE);
		} else {
			checkbox.setOnCheckedChangeListener(null);
			checkbox.setTag(fileInfo);
			checkbox.setChecked(getItem(position).isChecked);
			checkbox.setOnCheckedChangeListener(mOnCheckedChangeListener);
		}

		return v;
	}

	protected boolean isCheckBoxVisibile() {
		return true;
	}

	OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			FileInfo fileInfo = (FileInfo) buttonView.getTag();
			fileInfo.isChecked = isChecked;
			int p = fileInfo.position;
			if (isChecked) {
				mCheckedArray.put(p, true);
			} else {
				mCheckedArray.delete(p);
			}

			if (mItemCheckListener != null) {
				mItemCheckListener.onItemCheckListener(p, isChecked);
			}
		}
	};

	class SortByIsDir implements Comparator<FileInfo> {
		public int compare(FileInfo file1, FileInfo file2) {
			if (file1.isDir) {
				if (file2.isDir) {
					return 0;
				} else {
					return -1;
				}
			} else {
				if (file2.isDir) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	public void setPath(String path, boolean inStack) {
		mFileInfos.clear();

		File file = new File(path);
		Log.d(TAG, "size: " + file.listFiles());
		for (File item : file.listFiles()) {
			if (!item.canRead() && item.isDirectory()) {
				continue;
			}
			mFileInfos.add(new FileInfo(item.getPath()));
		}
		Collections.sort(mFileInfos, new SortByIsDir());
		notifyDataSetChanged();

		if (inStack) {
			mPathStack.push(new FileInfo(path));
		}
	}

	public FileInfo back() {
		if (mPathStack.size() == 1) {
			return null;
		}
		mPathStack.pop();
		FileInfo fileInfo = mPathStack.peek();
		setPath(fileInfo.path, false);
		return fileInfo;
	}

}
