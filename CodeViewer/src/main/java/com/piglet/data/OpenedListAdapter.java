package com.piglet.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.piglet.codeviewer.R;
import com.piglet.codeviewer.UIHandler;
import com.piglet.config.Lang;

public class OpenedListAdapter extends BaseAdapter {
	private List<HashMap<String, Object>> items;
	private LayoutInflater inflater;
	private UIHandler uihandler;

	public OpenedListAdapter(Context context) {
		items = new ArrayList<HashMap<String, Object>>();
		inflater = LayoutInflater.from(context);
	}

	public void setUiHandler(UIHandler uihandler) {
		this.uihandler = uihandler;
	}

	public HashMap<String, Object> getItemMap(int position) {
		return items.get(position);
	}

	public void addItems(List<HashMap<String, Object>> items) {
		this.items.addAll(items);
		this.notifyDataSetChanged();
	}

	public void addItem(HashMap<String, Object> item) {
		for (HashMap<String, Object> map : items) {
			map.put(Constant.FileTypeKey.FILECHOSEN, Integer.valueOf(0));
		}
		items.add(item);
		this.notifyDataSetChanged();
	}

	public void switchItem(String filepath) {
		for (HashMap<String, Object> map : items) {
			if (filepath.equals(map.get(Constant.FileTypeKey.FILEINFO).toString())) {
				map.put(Constant.FileTypeKey.FILECHOSEN, Integer.valueOf(1));
			} else {
				map.put(Constant.FileTypeKey.FILECHOSEN, Integer.valueOf(0));
			}
		}
		this.notifyDataSetChanged();
	}

	public void clear() {
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
			convertView = inflater.inflate(R.layout.openeditem, parent, false);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.itemicon);
			holder.name = (TextView) convertView.findViewById(R.id.itemname);
			holder.close = (ImageView) convertView.findViewById(R.id.itemoperate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final HashMap<String, Object> item = items.get(position);
		holder.name.setText(item.get(Constant.FileTypeKey.FILENAME).toString());

		Lang type = (Lang) item.get(Constant.FileTypeKey.FILETYPE);
		switch (type) {
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

		holder.close.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// fileinfo = full path.

				items.remove(item);
				String candidatePath = null;
				if (items.size() > 0) {
					candidatePath = items.get(0).get(Constant.FileTypeKey.FILEINFO).toString();
				}
				uihandler.close(item.get(Constant.FileTypeKey.FILEINFO).toString(), candidatePath);
				notifyDataSetChanged();
			}
		});
		
		int chosen = (Integer) item.get(Constant.FileTypeKey.FILECHOSEN);
		if(chosen == 0){
			convertView.setBackgroundColor(0xff000000);
		}else{
			convertView.setBackgroundColor(0xff00bffb);
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
		ImageView close;
	}
}
