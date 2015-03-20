package com.myxiaoapp.android;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myxiaoapp.adapter.ChatListAdapter;
import com.myxiaoapp.chathelper.ChatHelper;
import com.myxiaoapp.chathelper.PushMessageReceiver;
import com.myxiaoapp.model.RecentChatItem;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.SQLiteHelper;

public class ChatFragment extends Fragment implements OnItemClickListener {

	private static final String TAG = "mydebug";

	private ListView mChatList;
	private List<RecentChatItem> mRecentChats;

	private MessageReceiver messageReceiver;

	private static final int WHAT_UPDATE_RECENT_CHAT = 1;
	private Handler chatHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		init(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		messageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter(
				PushMessageReceiver.ACTION_MESSAGE_RECEIVER);
		getActivity().registerReceiver(messageReceiver, filter);
		updateRecentChat();
	}
	
	@Override
	public void onPause() {
		getActivity().unregisterReceiver(messageReceiver);
		super.onPause();
	}

	private void init(View view) {
		mChatList = (ListView) view.findViewById(R.id.chat_list);
		mChatList.setAdapter(new ChatListAdapter(getActivity()));
		mChatList.setOnItemClickListener(this);

		chatHandler = new ChatHandler(this);
	}

	/**
	 * 收到消息，更新未读数和最近记录
	 * 
	 * @author JiangZhenJie
	 * @date 2014-11-7
	 */
	private class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			updateRecentChat();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RecentChatItem recentChatItem = mRecentChats.get(position);
		User user = new User();
		// user.setUserId(recentChatItem.getUserId());
		// user.setChatUserId(recentChatItem.getChatUserId());
		// user.setName(recentChatItem.getChatUserName());
		ChatHelper.chatUser = user;
		startActivity(new Intent(getActivity(), ChatPanelActivity.class));
	}

	/**
	 * 更新最近聊天列表
	 */
	private void updateRecentChat() {
		new Thread() {
			@Override
			public void run() {
				SQLiteHelper helper = new SQLiteHelper(getActivity());
				SQLiteDatabase db = helper.getReadableDatabase();
				Cursor cursor = db.query(SQLiteHelper.TABLE_RECENT_CHAT, null,
						null, null, null, null, "times DESC");
				mRecentChats = new LinkedList<RecentChatItem>();
				while (cursor.moveToNext()) {
					Long timestamp = cursor.getLong(cursor
							.getColumnIndex("times"));
					String userId = cursor.getString(cursor
							.getColumnIndex("userId"));
					String chatUserId = cursor.getString(cursor
							.getColumnIndex("chatUserId"));
					String chatChannelId = cursor.getString(cursor
							.getColumnIndex("chatChannelId"));
					String chatUserName = cursor.getString(cursor
							.getColumnIndex("chatUserName"));
					String recentMessage = cursor.getString(cursor
							.getColumnIndex("recentMessage"));
					int unReadedCount = cursor.getInt(cursor
							.getColumnIndex("unReadedCount"));
					// ChatMessage chatMsg = new ChatMessage();
					// chatMsg.setTime(timestamp);
					// chatMsg.setToWho(chatUserId);
					// chatMsg.setToUser(userId);
					// chatMsg.setToUserName(chatUserName);
					// chatMsg.setMessage(recentMessage);
					User loginUser = XiaoYuanApp.getLoginUser(getActivity());
					if (!TextUtils.equals(userId, loginUser.userBean.uid)) {
						RecentChatItem chatItem = new RecentChatItem();
						chatItem.setTimestamp(timestamp);
						chatItem.setChatUserId(chatUserId);
						chatItem.setChatUserName(chatUserName);
						chatItem.setRecentMessage(recentMessage);
						chatItem.setUnReadedCount(unReadedCount);
						chatItem.setUserId(userId);
						mRecentChats.add(chatItem);
					}
				}
				cursor.close();
				chatHandler.sendEmptyMessage(WHAT_UPDATE_RECENT_CHAT);
			}
		}.start();
	}

	private static class ChatHandler extends Handler {
		private WeakReference<ChatFragment> mOuter;

		public ChatHandler(ChatFragment chatFragment) {
			mOuter = new WeakReference<ChatFragment>(chatFragment);
		}

		@Override
		public void handleMessage(Message msg) {
			ChatFragment chatFragment = mOuter.get();
			if (chatFragment == null)
				return;

			switch (msg.what) {
			case WHAT_UPDATE_RECENT_CHAT:
				ChatListAdapter adapter = (ChatListAdapter) chatFragment.mChatList
						.getAdapter();
				adapter.setData(chatFragment.mRecentChats);
				break;

			default:
				break;
			}
		}
	}
}
