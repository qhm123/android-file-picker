package com.qhm123.fileselector;

import java.io.File;
import java.util.ArrayList;

import com.qhm123.fileselector.FileAdapter.ItemCheckListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FileExplorer extends Activity implements OnItemClickListener,
		OnItemSelectedListener, OnClickListener, ItemCheckListener {

	private static final String TAG = FileExplorer.class.getSimpleName();

	private ListView mList;
	private FileAdapter mFileAdapter;
	private Button mSelectButton;
	private TextView mCurrentDirPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_explorer);

		mSelectButton = (Button) findViewById(R.id.select);
		mSelectButton.setOnClickListener(this);
		mCurrentDirPath = (TextView) findViewById(R.id.dir_path);

		mFileAdapter = new FileAdapter(this);
		mFileAdapter.setItemCheckListener(this);

		mList = (ListView) findViewById(R.id.list);
		mList.setAdapter(mFileAdapter);
		mList.setFastScrollEnabled(true);
		// mList.setItemsCanFocus(false);
		mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mList.setOnItemClickListener(this);
		mList.setOnItemSelectedListener(this);

		String initPath = "/sdcard";
		mFileAdapter.setPath(initPath, true);
		mCurrentDirPath.setText(initPath);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemClick");

		FileInfo fileInfo = mFileAdapter.getItem(position);
		File file = new File(fileInfo.path);
		if (file.isDirectory()) {
			mList.clearChoices();
			mFileAdapter.setPath(fileInfo.path, true);
			mList.scrollTo(0, 0);
			mCurrentDirPath.setText(fileInfo.path);
		}
	}

	@Override
	public void onBackPressed() {
		mList.clearChoices();
		FileInfo fileInfo = mFileAdapter.back();
		if (fileInfo != null) {
			mCurrentDirPath.setText(fileInfo.path);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemSelected");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.d(TAG, "onNothingSelected");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.select:
			Intent data = new Intent(getIntent());
			ArrayList<Uri> files = new ArrayList<Uri>();
			SparseBooleanArray positions = mFileAdapter
					.getCheckedItemPositions();
			for (int i = 0; i < positions.size(); i++) {
				int position = positions.keyAt(i);
				Log.d(TAG, "position: " + position);
				files.add(Uri.parse(mFileAdapter.getItem(position).path));
			}
			data.putParcelableArrayListExtra("files", files);
			setResult(RESULT_OK, data);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemCheckListener(int position, boolean isChecked) {

	}
}
