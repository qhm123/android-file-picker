package com.qhm123.fileselector;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private static final int REQUEST_GET_FILES = 0;

	private Button mSelectFiles;
	private TextView mSelectPaths;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSelectFiles = (Button) findViewById(R.id.select_file);
		mSelectFiles.setOnClickListener(this);
		mSelectPaths = (TextView) findViewById(R.id.file_paths);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_GET_FILES:
			if (resultCode == RESULT_OK) {
				StringBuilder sb = new StringBuilder();
				ArrayList<Uri> uris = data.getParcelableArrayListExtra("files");
				for (Uri uri : uris) {
					sb.append(uri.getPath()).append("\r\n");
				}
				mSelectPaths.setText(sb.toString());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.select_file:
			Intent i = new Intent("com.qhm123.fileselector.single");
			startActivityForResult(i, REQUEST_GET_FILES);
			break;
		default:
			break;
		}
	}
}
