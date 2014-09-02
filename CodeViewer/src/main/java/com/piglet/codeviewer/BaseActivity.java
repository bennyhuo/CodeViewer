package com.piglet.codeviewer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.piglet.config.Lang;
import com.piglet.ui.LeftFragment;
import com.piglet.ui.RightFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected LeftFragment leftFragment;
	protected RightFragment rightFragment;
	protected SlidingMenu sm;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		addLeft();
		addRight();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);

		// // customize the ActionBar
		// if (Build.VERSION.SDK_INT >= 11) {
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// }
	}

	private void addLeft() {
		FrameLayout left = new FrameLayout(this);
		left.setId("LEFT".hashCode());
		setBehindLeftContentView(left);
		leftFragment = new LeftFragment();
		getSupportFragmentManager().beginTransaction().replace("LEFT".hashCode(),leftFragment).commit();

		sm = getSlidingMenu();
		sm.setShadowDrawable(R.drawable.shadow, SlidingMenu.LEFT);
		sm.setBehindOffsetRes(R.dimen.actionbar_home_width, SlidingMenu.LEFT);
		sm.setFadeEnabled(true);
		sm.setBehindScrollScale(0.6f, SlidingMenu.LEFT);
		sm.setFadeDegree(1.0f);
	}

	private void addRight() {
		FrameLayout right = new FrameLayout(this);
		right.setId("RIGHT".hashCode());
		this.setBehindRightContentView(right);
		rightFragment = new RightFragment();
		getSupportFragmentManager().beginTransaction().replace("RIGHT".hashCode(), rightFragment).commit();

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowDrawable(R.drawable.shadow_right, SlidingMenu.RIGHT);
		sm.setBehindOffsetRes(R.dimen.actionbar_home_width, SlidingMenu.RIGHT);
	}

	// @Override
	// public void onResume() {
	// super.onResume();
	// getSlidingMenu().showAbove();
	// }

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle(SlidingMenu.LEFT);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openSourceFile(String filename, String path, Lang lang, boolean isNewOpened){
		if(isNewOpened){
			rightFragment.addItem(filename, path, lang);	
		}else{
			rightFragment.switchItem(path);
		}
		
	}
}
