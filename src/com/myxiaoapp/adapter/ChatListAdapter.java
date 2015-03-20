package com.myxiaoapp.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.myxiaoapp.android.R;
import com.myxiaoapp.chathelper.ChatMessage;
import com.myxiaoapp.model.RecentChatItem;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.Utils;

public class ChatListAdapter extends BaseAdapter {

	private Context mContext;
	private List<RecentChatItem> mRecentChats;

	public ChatListAdapter(Context c) {
		this.mContext = c;
	}

	public ChatListAdapter(Context c, List<RecentChatItem> recentChats) {
		this.mContext = c;
		this.mRecentChats = recentChats;
	}

	@Override
	public int getCount() {
		if (mRecentChats == null) {
			return 0;
		}
		return mRecentChats.size();
	}

	@Override
	public Object getItem(int position) {
		if (mRecentChats == null) {
			return 0;
		}
		return mRecentChats.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		RecentChatItem recentChat = (RecentChatItem) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.chat_list_item, parent, false);
			holder = new ViewHolder();
			holder.portrait = (ImageView) convertView
					.findViewById(R.id.portrait);
			holder.badgeView = new BadgeView(mContext);
			holder.badgeView.setTargetView(holder.portrait);
			holder.userName = (TextView) convertView
					.findViewById(R.id.user_name);
			holder.messagePreview = (TextView) convertView
					.findViewById(R.id.message_preview);
			holder.time = (TextView) convertView.findViewById(R.id.time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.userName.setText(recentChat.getChatUserName());
		holder.messagePreview.setText(recentChat.getRecentMessage());
		holder.time.setText(Utils.formatTime(recentChat.getTimestamp()));

		holder.badgeView.setVisibility(View.VISIBLE);
		holder.badgeView.setBadgeCount(recentChat.getUnReadedCount());
		// if (recentChat.getUnReadedCount() != 0) {
		// holder.badgeView.setVisibility(View.VISIBLE);
		// holder.badgeView.setBadgeCount(recentChat.getUnReadedCount());
		// } else {
		// holder.badgeView.setVisibility(View.GONE);
		// }

		return convertView;
	}

	class ViewHolder {
		ImageView portrait;
		BadgeView badgeView;
		TextView userName;
		TextView messagePreview;
		TextView time;
	}

	public void setData(List<RecentChatItem> recentChats) {
		if (mRecentChats == null) {
			mRecentChats = new LinkedList<RecentChatItem>();
		}
		mRecentChats.clear();
		mRecentChats.addAll(recentChats);
		notifyDataSetChanged();
	}
	// public void addData(User user){
	// this.mRecentChats.add(user);
	// notifyDataSetChanged();
	// }

}
