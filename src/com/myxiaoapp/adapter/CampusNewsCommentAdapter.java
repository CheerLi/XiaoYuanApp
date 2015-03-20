package com.myxiaoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myxiaoapp.android.R;

public class CampusNewsCommentAdapter extends BaseAdapter{

	Context mContext;
	public CampusNewsCommentAdapter(Context mContext){
		this.mContext = mContext;
	}
	
	int count = 10;
	//String data[] = new String[5];
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
		ViewHolder mViewHolder;
		if(convertView == null){
			if(position % 2 == 1){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.campus_news_reply_comment_item, null);
				mViewHolder = new ViewHolder();
				mViewHolder.replyPeople = (TextView)convertView.findViewById(R.id.reply_people);
				mViewHolder.commentPeople = (TextView)convertView.findViewById(R.id.comment_people);
				mViewHolder.content = (TextView)convertView.findViewById(R.id.content);
				convertView.setTag(mViewHolder);
			}else {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.campus_news_comment_item, null);
				mViewHolder = new ViewHolder();
				mViewHolder.commentPeople = (TextView)convertView.findViewById(R.id.comment_people);
				mViewHolder.content = (TextView)convertView.findViewById(R.id.content);
				convertView.setTag(mViewHolder);
			}
		}else{
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		return convertView;
		
	}
	private class ViewHolder{
		TextView replyPeople;
		TextView commentPeople;
		TextView content;
	}
}
