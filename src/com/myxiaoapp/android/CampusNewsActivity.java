package com.myxiaoapp.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.myxiaoapp.adapter.CampusNewsAdapter;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.CampusCircle;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Utils;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.pulltorefresh.library.PullToRefreshListView;

/**
 * modify:新增OnItemClickListener接口，监听listview
 * 
 * @author:liqihang
 */
public class CampusNewsActivity extends CommonActivity implements
		OnClickListener, OnItemClickListener, OnLongClickListener {

	private Context mContext;
	private ListView mNewsList;
	private PullToRefreshListView mPullToRefreshListView;
	private ImageButton mSendNews;
	private int mFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_news);
		setActionBarTitle("校园圈");

		mContext = this;
		// 获取校园圈列表信息
		User user = XiaoYuanApp.getLoginUser(this);
		CampusCircle mCampusCircle = new CampusCircle(this, ""
				+ user.userBean.uid, "xiaoyuan", null, "1", "1");
		mCampusCircle.post();

		Intent in = getIntent();
		mFlag = in.getFlags();
		if (mFlag == Constant.FLAG_Discovery) {

			/*
			 * View view = LayoutInflater.from(this).inflate(
			 * R.layout.send_campus_news_button, mNewsList, false); mSendNews =
			 * (ImageButton) view.findViewById(R.id.send_campus_news);
			 * mSendNews.setOnClickListener(this);
			 * mSendNews.setOnLongClickListener(this);
			 */
			/*
			 * mActionBarRightButton.setImageDrawable(getResources().getDrawable(
			 * R.drawable.send_campus_news));
			 * mActionBarRightButton.setVisibility(View.VISIBLE);
			 * mActionBarRightButton.setOnClickListener(this);
			 * mActionBarRightButton.setOnLongClickListener(this);
			 */// showImageButton(this);
				// View view =
				// LayoutInflater.from(this).inflate(R.layout.send_campus_news_button);
				// getActionBarRightButton().set
				// View view = LayoutInflater.from(this).inflate(
				// R.layout.send_campus_news_button, mNewsList, false);
				// // mSendNews = (ImageButton)
				// view.findViewById(R.id.send_campus_news);
				// //
				// //// addCustomView(view);
			mSendNews = getActionBarRightButton();
			mSendNews.setImageDrawable(getResources().getDrawable(
					R.drawable.send_campus_news));
			mSendNews.setOnClickListener(this);
			mSendNews.setOnLongClickListener(this);
		}

		showBackButton();
		init();
	}

	private void init() {
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.campus_news_list);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(refreshListener);
		mNewsList = mPullToRefreshListView.getRefreshableView();
		mNewsList.setAdapter(new CampusNewsAdapter(this));
	//	mNewsList.setAdapter(new CampusNewsAdapter(this,));
	}

	private OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if (mPullToRefreshListView.isHeaderShown()) { // 上拉
				Utils.showProgressDialog(mContext, R.string.refreshing, true,
						cancelListener);
			} else if (mPullToRefreshListView.isFooterShown()) { // 下拉
				Utils.showProgressDialog(mContext, R.string.loading, true,
						cancelListener);
			}
		}
	};

	private OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			mPullToRefreshListView.onRefreshComplete();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.campus_news, menu);
		if (mFlag != Constant.FLAG_ME) { // 从校内附近的人进来
			menu.removeItem(R.id.action_information);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_information:
			startActivity(new Intent(this, MessageActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivityForResult(new Intent(mContext,
					SelectPicGroupActivity.class), 1);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivity(new Intent(mContext, PublishTextActivity.class));
			break;

		default:
			break;
		}
		return true;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("mydebug", "item click " + position);
		startActivity(new Intent(this, CampusNewsDetailsActivity.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			data.setClass(this, PublishImageActivity.class);
			startActivity(data);
		}
	}

}
