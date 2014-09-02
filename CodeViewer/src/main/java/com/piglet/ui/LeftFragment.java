package com.piglet.ui;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.piglet.codeviewer.R;
import com.piglet.codeviewer.UIHandler;
import com.piglet.config.CodeStyle;
import com.piglet.config.Lang;
import com.piglet.core.FileBrowser;
import com.piglet.data.Constant;
import com.piglet.data.FileListAdapter;

public class LeftFragment extends Fragment {
	private UIHandler uiHandler;

	private ListView list;
	private FileListAdapter adapter;
	private FileBrowser browser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.filebrowser, container, false);
		list = (ListView) view.findViewById(R.id.filelist);
		adapter = new FileListAdapter(getActivity());

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HashMap<String, String> item = adapter.getItemMap(position);
				int type = Integer.parseInt(item.get(Constant.FileTypeKey.FILETYPE));

				switch (type) {
				case -2:
					adapter.clear();
					adapter.addItems(browser.goToParent());
					break;
				case -1:
					Toast.makeText(getActivity(), "Not Supported.", Toast.LENGTH_SHORT).show();
					break;
				case 0:
					adapter.clear();
					adapter.addItems(browser.goToChildDir(item.get(Constant.FileTypeKey.FILENAME)));
					break;
				case 1:
					// Toast.makeText(getActivity(), "open file..",
					// Toast.LENGTH_SHORT).show();
					uiHandler.openSourceFile(browser.getFullPath(item.get(Constant.FileTypeKey.FILENAME)), CodeStyle.ECLIPSE, Lang.JAVA);
					break;
				case 2:
					uiHandler.openSourceFile(browser.getFullPath(item.get(Constant.FileTypeKey.FILENAME)), CodeStyle.ECLIPSE, Lang.CPP);
					break;
				case 3:
					uiHandler.openSourceFile(browser.getFullPath(item.get(Constant.FileTypeKey.FILENAME)), CodeStyle.ECLIPSE, Lang.JSCRIPT);
					break;
				case 4:
					break;
				}

			}
		});

		list.setAdapter(adapter);

		browser = FileBrowser.getFileBrowser(getActivity());
		adapter.addItems(browser.goToSdCard());
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		uiHandler = (UIHandler) activity;
	}

}
