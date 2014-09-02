package com.piglet.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.piglet.codeviewer.R;

public class FileListAdapter extends BaseAdapter {
	private List<HashMap<String, String>> items;
	private LayoutInflater inflater;
	
	public FileListAdapter(Context context){
		items = new ArrayList<HashMap<String,String>>();
		inflater = LayoutInflater.from(context);
	}
	
	public HashMap<String, String> getItemMap(int position){
		return items.get(position);
	}
	
	public void addItems(List<HashMap<String, String>> items){
		this.items.addAll(items);
		this.notifyDataSetChanged();
	}
	
	public void addItem(HashMap<String, String> item){
		items.add(item);
		this.notifyDataSetChanged();
	}
	
	public void clear(){
		items.clear();
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fileitem, parent, false);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.itemicon);
			holder.name = (TextView) convertView.findViewById(R.id.itemname);
			holder.info = (TextView) convertView.findViewById(R.id.iteminfo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		HashMap<String, String> item = items.get(position);
		holder.name.setText(item.get(Constant.FileTypeKey.FILENAME));
		holder.info.setText(item.get(Constant.FileTypeKey.FILEINFO));

		int type = Integer.parseInt(item.get(Constant.FileTypeKey.FILETYPE));
		switch (type) {
		case -2:
			holder.icon.setImageResource(R.drawable.back);
			break;
		case 0:// folder
			holder.icon.setImageResource(R.drawable.folder);
			break;
		case 1://java
			holder.icon.setImageResource(R.drawable.java);
			break;
		case 2://cpp
			holder.icon.setImageResource(R.drawable.cpp);
			break;
		case 3:
			holder.icon.setImageResource(R.drawable.js);
			break;
		default:
			holder.icon.setImageResource(R.drawable.unknown);
			break;
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView info;
	}
}
