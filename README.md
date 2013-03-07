# FileSelector 文件选择器

## 简介

文件选择器实现了通过action Intent来唤起文件选择界面，选择文件后返回被选择的路径。FileSelector定于3个Action，分别实现了文件单选，多选，只选目录3个功能。

## 用法

```
public static final String SINGLE = "com.qhm123.fileselector.single";
public static final String MULTIPLE = "com.qhm123.fileselector.multiple";
public static final String DIR = "com.qhm123.fileselector.dir";

```
通过如上三个action的Intent开启对应功能的界面。

## 示例

``````
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	switch (requestCode) {
	case REQUEST_GET_SINGLE_FILE: {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getParcelableExtra("file");
			mSelectPaths.setText(uri.getPath());
		}
	}
		break;
	case REQUEST_GET_FILES: {
		if (resultCode == RESULT_OK) {
			StringBuilder sb = new StringBuilder();
			ArrayList<Uri> uris = data.getParcelableArrayListExtra("files");
			for (Uri uri : uris) {
				sb.append(uri.getPath()).append("\r\n");
			}
			mSelectPaths.setText(sb.toString());
		}
	}
		break;
	case REQUEST_GET_DIR: {
		if (resultCode == RESULT_OK) {
			StringBuilder sb = new StringBuilder();
			ArrayList<Uri> uris = data.getParcelableArrayListExtra("files");
			for (Uri uri : uris) {
				sb.append(uri.getPath()).append("\r\n");
			}
			mSelectPaths.setText(sb.toString());
		}
	}
		break;
	default:
		break;
	}
}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.select_single_file: {
		Intent i = new Intent("com.qhm123.fileselector.single");
		startActivityForResult(i, REQUEST_GET_SINGLE_FILE);
	}
		break;
	case R.id.select_file: {
		Intent i = new Intent("com.qhm123.fileselector.multiple");
		startActivityForResult(i, REQUEST_GET_FILES);
	}
		break;
	case R.id.select_dir: {
		Intent i = new Intent("com.qhm123.fileselector.dir");
		startActivityForResult(i, REQUEST_GET_DIR);
	}
		break;
	default:
		break;
	}
}

``````

## 截图

![http://ww1.sinaimg.cn/bmiddle/6414b943jw1e2hetczq32j.jpg](http://ww1.sinaimg.cn/bmiddle/6414b943jw1e2hetczq32j.jpg)

(多选截图)

## License

This program is free softwareyou can redistribute it and /or modify it under the terms of the GNU General Public License as published by the Free Software Foundataioneither version 2 of the License,or (at your option) any later version.

You should have read the GNU General Public License before start "RTFSC".

If not,see [http://www.gnu.org/licenses/](http://www.gnu.org/licenses/)