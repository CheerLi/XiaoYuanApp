package com.myxiaoapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myxiaoapp.android.CampusNewsDetailsActivity;
import com.myxiaoapp.android.HomePageActivity;
import com.myxiaoapp.android.R;
import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.view.CircleImageView;

public class CampusNewsAdapter extends BaseAdapter implements OnClickListener {
	
	private Context mContext;
	private String CONSTANTLOG="CampusNewsAdapter:";
	// test data
	private int count = 10;

	//real data
	private ArrayList<ArrayList<String> > datas; 
	private String nickName;
	private String time;
	private String content;
	private String[] smallUrl;
	private String[] url;
	private String likes;
	private String comments;
	public CampusNewsAdapter(Context c) {
		mContext = c;
	}
	public CampusNewsAdapter(Context c,ArrayList<ArrayList<String>> datas){
		mContext = c;
		this.datas = datas;
		Log.d("mydebug", CONSTANTLOG+"create Adapter");
	}
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	//	ArrayList<String> data = datas.get(position);
		Log.d("mydebug", CONSTANTLOG+"getView");
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.campus_news_item, null);
			viewHolder = new ViewHolder();
/*
			// 封面
			viewHolder.coverLayout = (RelativeLayout) convertView
					.findViewById(R.id.rl_cover);
			viewHolder.myPortrait = (CircleImageView) convertView
					.findViewById(R.id.ci_my_portrait);
			viewHolder.myName = (TextView) convertView
					.findViewById(R.id.tv_my_name);
			viewHolder.mySign = (TextView) convertView
					.findViewById(R.id.tv_personal_sign);
*/
			// 校园圈
			viewHolder.commonNewsLayout = (LinearLayout) convertView
					.findViewById(R.id.ll_common_news);
			viewHolder.commonNewsLayout.setOnClickListener(this);
			
			viewHolder.portrait = (ImageView) convertView
					.findViewById(R.id.portrait);
			viewHolder.portrait.setOnClickListener(this);
			
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.tv_user_name);
		//	viewHolder.userName.setText(data.get(0));
			
			viewHolder.dateTime = (TextView) convertView
					.findViewById(R.id.tv_time);
		//	viewHolder.dateTime.setText(data.get(1));
			
			viewHolder.newsContent = (TextView) convertView
					.findViewById(R.id.tv_content);
		//	viewHolder.newsContent.setText(data.get(2));
			
			viewHolder.newsPhotos = (GridView) convertView
					.findViewById(R.id.news_photos);
			
			viewHolder.newsPhotos.setAdapter(new PhotoAdapter( mContext, Constant.FLAG_CAMPUS));
			
			viewHolder.like = (TextView) convertView.findViewById(R.id.tv_like);
		//	viewHolder.like.setText(data.get(data.size()-2));
			
			viewHolder.comment = (TextView) convertView
					.findViewById(R.id.tv_comment);
		//	viewHolder.comment.setText(data.get(data.size()-1));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

/*		if (position == 0) {
			// 显示封面
			viewHolder.commonNewsLayout.setVisibility(View.GONE);
			viewHolder.coverLayout.setVisibility(View.VISIBLE);
		} else {
			// 显示校园圈
			viewHolder.commonNewsLayout.setVisibility(View.VISIBLE);
			viewHolder.coverLayout.setVisibility(View.GONE);
		}
*/		
		viewHolder.commonNewsLayout.setVisibility(View.VISIBLE);
		return convertView;
	}

	private class ViewHolder {
		// 封面
//		RelativeLayout coverLayout;
//		CircleImageView myPortrait;
//		TextView myName;
//		TextView mySign;

		// 校园圈列表
		LinearLayout commonNewsLayout;
		ImageView portrait;
		TextView userName;
		TextView dateTime;
		TextView newsContent;
		GridView newsPhotos;
		TextView like;
		TextView comment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_common_news:
			mContext.startActivity(new Intent(mContext,
					CampusNewsDetailsActivity.class));
			break;
		case R.id.portrait:
			mContext.startActivity(new Intent(mContext, HomePageActivity.class));
			break;

		default:
			break;
		}
	}

}
