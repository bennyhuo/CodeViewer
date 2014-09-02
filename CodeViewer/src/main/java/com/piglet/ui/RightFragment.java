package com.piglet.ui;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.piglet.codeviewer.R;
import com.piglet.codeviewer.UIHandler;
import com.piglet.config.CodeStyle;
import com.piglet.config.Lang;
import com.piglet.core.FileBrowser;
import com.piglet.data.Constant;
import com.piglet.data.FileListAdapter;
import com.piglet.data.OpenedListAdapter;

public class RightFragment extends Fragment {
	private UIHandler uiHandler;

	private ListView list;
	private OpenedListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.filebrowser, container, false);
		list = (ListView) view.findViewById(R.id.filelist);
		adapter = new OpenedListAdapter(getActivity());
		adapter.setUiHandler(uiHandler);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HashMap<String, Object> item = adapter.getItemMap(position);
				Lang type = (Lang) item.get(Constant.FileTypeKey.FILETYPE);
				//switch
				uiHandler.openSourceFile(item.get(Constant.FileTypeKey.FILEINFO).toString(), null, null);
			}
		});

		list.setAdapter(adapter);


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

	public void addItem(String filename, String path, Lang lang){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.FileTypeKey.FILEINFO, path);
		map.put(Constant.FileTypeKey.FILENAME, filename);
		map.put(Constant.FileTypeKey.FILETYPE, lang);
		map.put(Constant.FileTypeKey.FILECHOSEN,  Integer.valueOf(1));
		adapter.addItem(map);
	}
	
	public void switchItem(String path){
		adapter.switchItem(path);
	}
}
