package com.myxiaoapp.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.myxiaoapp.chathelper.RestApi;

public class MainUIActivity extends CommonActivity implements
		OnPageChangeListener, OnClickListener, OnMenuItemClickListener {

	private Context mContext;
	private RadioGroup mTabGroup;
	private RadioButton mRbCampus;
	private RadioButton mRbDiscovery;
	private RadioButton mRbChat;
	private RadioButton mRbMe;
	private ViewPager mViewPager;
	private List<Fragment> mFragments;

	private int mCurrentTab = 1;
	private ImageView mCurSexImage;

	public static final String TAG_CAMPUS = "campus";
	public static final String TAG_DISCOVERY = "discovery";
	public static final String TAG_CHAT = "chat";
	public static final String TAG_ME = "me";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ui);
		mContext = this;
		init();
		mApp.destoryOtherLaunchActivitys();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!PushManager.isPushEnabled(this)) {
			PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY,
					RestApi.API_KEY);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PushManager.stopWork(this);
	}

	private void init() {
		CampusFragment campusFragment = new CampusFragment();
		DiscoveryFragment discoveryFragment = new DiscoveryFragment();
		ChatFragment chatFragment = new ChatFragment();
		MyHomePageFragment meFragment = new MyHomePageFragment();
		mFragments = new ArrayList<Fragment>();
		mFragments.add(campusFragment);
		mFragments.add(discoveryFragment);
		mFragments.add(chatFragment);
		mFragments.add(meFragment);

		mViewPager = (ViewPager) findViewById(R.id.vp_main_ui);
		mViewPager
				.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(this);
		mTabGroup = (RadioGroup) findViewById(R.id.tab_group);
		mRbCampus = (RadioButton) findViewById(R.id.rb_campus);
		mRbCampus.setOnClickListener(this);
		mRbDiscovery = (RadioButton) findViewById(R.id.rb_discovery);
		mRbDiscovery.setOnClickListener(this);
		mRbChat = (RadioButton) findViewById(R.id.rb_chat);
		mRbChat.setOnClickListener(this);
		mRbMe = (RadioButton) findViewById(R.id.rb_me);
		mRbMe.setOnClickListener(this);

		showMenuButton(this);
		// mActionBar.setDisplayHomeAsUpEnabled(false);
		// mActionBar.setDisplayOptions(mActionBar.getDisplayOptions()
		// | ActionBar.DISPLAY_SHOW_CUSTOM);

		setTab(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// int menuRes = R.menu.campus_people;
		// switch (mCurrentTab) {
		// case 1:
		// menuRes = R.menu.campus_people;
		// break;
		// case 2:
		// menuRes = R.menu.discovery;
		// break;
		// case 3:
		// menuRes = R.menu.chat;
		// break;
		// case 4:
		// menuRes = R.menu.me;
		// break;
		// default:
		//
		// break;
		// }
		// getMenuInflater().inflate(menuRes, menu);
		return true;
	}

	private void setTab(int position) {
		switch (position) {
		case 0:
			// mActionBar.setTitle(R.string.campus_people);
			// mActionBar.setCustomView(mCurSexImage);
			mCurrentTab = 1;
			mRbCampus.setChecked(true);
			setActionBarTitle(R.string.campus_people);
			Drawable rightDrawable = getResources().getDrawable(
					R.drawable.icon_campus_title_right);
			getActionBarTitle().setCompoundDrawablePadding(10);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, rightDrawable, null);
			getActionBarRightButton().setVisibility(View.VISIBLE);
			break;

		case 1:
			// mActionBar.setTitle(R.string.discovery);
			// mActionBar.setCustomView(null);
			mCurrentTab = 2;
			mRbDiscovery.setChecked(true);
			setActionBarTitle(R.string.discovery);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.INVISIBLE);
			break;
		case 2:
			// mActionBar.setTitle(R.string.chat);
			// mActionBar.setCustomView(null);
			mCurrentTab = 3;
			mRbChat.setChecked(true);
			setActionBarTitle(R.string.chat);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.INVISIBLE);
			break;
		case 3:
			// mActionBar.setTitle(R.string.me);
			// mActionBar.setCustomView(null);
			mCurrentTab = 4;
			mRbMe.setChecked(true);
			setActionBarTitle(R.string.me);
			getActionBarTitle().setCompoundDrawablesWithIntrinsicBounds(null,
					null, null, null);
			getActionBarRightButton().setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		supportInvalidateOptionsMenu();
	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	public void onPageSelected(int arg0) {
		setTab(arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_campus:
			mRbCampus.setChecked(true);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.rb_discovery:
			mRbDiscovery.setChecked(true);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.rb_chat:
			mRbChat.setChecked(true);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.rb_me:
			mRbMe.setChecked(true);
			mViewPager.setCurrentItem(3, false);
			break;
		case R.id.ibtn_right:
			if (mCurrentTab == 1) {
				showMenu(R.menu.campus_people, this);
			} else if (mCurrentTab == 4) {
				showMenu(R.menu.me, this);
			}
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		mCurSexImage = new ImageView(this);
		switch (item.getItemId()) {
		case R.id.action_display_male_only:
			mCurSexImage.setImageResource(R.drawable.male);
			// mActionBar.setCustomView(mCurSexImage, new LayoutParams(
			// ActionBar.LayoutParams.WRAP_CONTENT,
			// ActionBar.LayoutParams.MATCH_PARENT));
			break;
		case R.id.action_display_female_only:
			mCurSexImage.setImageResource(R.drawable.female);
			// mActionBar.setCustomView(mCurSexImage, new LayoutParams(
			// ActionBar.LayoutParams.WRAP_CONTENT,
			// ActionBar.LayoutParams.MATCH_PARENT));
			break;
		case R.id.action_display_all:
			// mActionBar.setCustomView(null);
			break;
		case R.id.action_function_settings:
			startActivity(new Intent(mContext, SettingActivity.class));
			break;

		case R.id.action_feedback:
			startActivity(new Intent(mContext, FeedbackActivity.class));
			break;
		case R.id.action_add_focus:
			startActivity(new Intent(mContext, AddNewFriendActivity.class));
			break;
		default:
			break;
		}
		return true;
	}
}
