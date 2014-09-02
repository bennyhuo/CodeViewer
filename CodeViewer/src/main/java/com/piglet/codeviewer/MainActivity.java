package com.piglet.codeviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ben.data.DatabaseHelper;
import com.piglet.config.CodeStyle;
import com.piglet.config.Lang;
import com.piglet.core.CodeGen;
import com.piglet.data.RecentFileAdapter;
import com.piglet.pojo.SourceFileInfo;
import com.piglet.pojo.SourceFileInfoDao;
import com.piglet.ui.CodeFragment;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity implements UIHandler {
	private FragmentManager fragmentManager = null;

	private Set<SourceFileInfo> recentFiles;
	private int currentIndex = 0;

	private ListView list;
	private RecentFileAdapter adapter;

	private SourceFileInfoDao dao;

	public MainActivity() {
		super(R.string.app_title);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlidingActionBarEnabled(true);// 设置可以滑动标题，ActionBar
		setContentView(R.layout.activity_main);

		DatabaseHelper.getInstance().init(this);
		dao = new SourceFileInfoDao();

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.recent_files);

		list = (ListView) findViewById(R.id.recentlist);
		adapter = new RecentFileAdapter(this);
		adapter.addItems(dao.findAll());

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SourceFileInfo info = adapter.getItemInfo(position);
				openSourceFile(info.getPath(), CodeStyle.ECLIPSE, info.lang());
			}
		});
		list.setAdapter(adapter);
		fragmentManager = getSupportFragmentManager();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initFragment() {

	}

	public void toggleMenu() {
		if (sm.isBehindShowing()) {
			sm.showAbove();
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = super.onKeyUp(keyCode, event);
		if (!b) {
			this.finish();
			b = true;
		}
		return b;
	}

	public void clearFragments() {

	}

	private Fragment currentCodeView;

	public void openSourceFile(String path, CodeStyle style, Lang lang) {
		toggleMenu();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		CodeFragment cf = (CodeFragment) fragmentManager.findFragmentByTag(path);
		try {
			if (cf == null) {
				CodeGen gen = CodeGen.getCodeGen(this);
				String content = gen.generate(path, style, lang);
				cf = new CodeFragment();

				ft.add(R.id.container, cf, path);
				String filename = path.substring(path.lastIndexOf(File.separator) + 1);
				openSourceFile(filename, path, lang, true);
				cf.initData(filename, content);

				ft.show(cf);
				if (currentCodeView != null) {
					ft.hide(currentCodeView);
				}
				currentCodeView = cf;

				SourceFileInfo sfi = dao.findById(path);
				if (sfi == null) {
					sfi = new SourceFileInfo();
					sfi.setPath(path);
					sfi.setLang(lang.name());
					dao.save(sfi);
					adapter.addItem(sfi);
				}

			} else if (cf != currentCodeView) {
				String filename = path.substring(path.lastIndexOf(File.separator) + 1);
				openSourceFile(filename, path, lang, false);

				ft.show(cf);
				if (currentCodeView != null) {
					ft.hide(currentCodeView);
				}
				currentCodeView = cf;
			}

			ft.commit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close(String path, String candidatePath) {
		CodeFragment cf = (CodeFragment) fragmentManager.findFragmentByTag(path);

		if (cf != null) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			if (candidatePath != null) {
				CodeFragment cfshow = (CodeFragment) fragmentManager.findFragmentByTag(candidatePath);
				if (cfshow != null) {
					ft.show(cfshow);
					openSourceFile(null, candidatePath, null, false);// 次奥，什么代码。。
				}
			}
			ft.remove(cf);
			ft.commit();
		}
	}

}
