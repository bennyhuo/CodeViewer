package com.piglet.data;

import java.io.File;
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
import com.piglet.pojo.SourceFileInfo;

public class RecentFileAdapter extends BaseAdapter {
	private List<SourceFileInfo> items;
	private LayoutInflater inflater;
	
	public RecentFileAdapter(Context context){
		items = new ArrayList<SourceFileInfo>();
		inflater = LayoutInflater.from(context);
	}
	
	public SourceFileInfo getItemInfo(int position){
		return items.get(position);
	}
	
	public void addItems(List<SourceFileInfo> items){
		this.items.addAll(items);
		this.notifyDataSetChanged();
	}
	
	public void addItem(SourceFileInfo item){
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

		SourceFileInfo item = items.get(position);
		String filename = item.getPath().substring(item.getPath().lastIndexOf(File.separator) + 1);
		holder.name.setText(filename);
		holder.info.setText(item.getPath());
		
		switch (item.lang()) {
		case JAVA:// java
			holder.icon.setImageResource(R.drawable.java);
			break;
		case CPP:// cpp
			holder.icon.setImageResource(R.drawable.cpp);
			break;
		case JSCRIPT:
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
