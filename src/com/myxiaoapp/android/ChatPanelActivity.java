package com.myxiaoapp.android;

import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.myxiaoapp.adapter.ChatMsgAdapter;
import com.myxiaoapp.chathelper.ChatHelper;
import com.myxiaoapp.chathelper.ChatMessage;
import com.myxiaoapp.chathelper.PushMessageReceiver;
import com.myxiaoapp.chathelper.RestApi;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.SQLiteHelper;
import com.myxiaoapp.view.ExpressionPanel;
import com.myxiaoapp.view.ExpressionPanel.OnExpressionClickListener;

public class ChatPanelActivity extends CommonActivity implements
		OnClickListener, OnTouchListener {

	private static final String TAG = "mydebug";

	private ListView mChatList;
	private ChatMsgAdapter mAdapter;

	private EditText mInputText;
	private Button mSend;
	private ImageView mAddAttach;
	private ImageView mAddExpression;

	private ExpressionPanel mExpressionPanel;

	private MessageReceiver messageReceiver;

	private User mFirstUser;
	private User mSecondUser;

	private InputMethodManager imm;

	// test
	private static final String FIRST_USER_ID = "589396412218359559";
	private static final String FIRST_CHANNEL_ID = "4042017386297853232";
	private static final String SECOND_USER_ID = "606844474145847127";
	private static final String SECOND_USER_CHANNEL_ID = "4600684025511915918";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_panel);
		// setActionBarTitle("聊天");
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!PushManager.isPushEnabled(this)) {
			PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY,
					RestApi.API_KEY);
		}
	}

	private void init() {
		mChatList = (ListView) findViewById(R.id.lv_chat_msg);
		mChatList.setSelection(mChatList.getBottom());
		mChatList.setOnTouchListener(this);
		mInputText = (EditText) findViewById(R.id.et_input_text);
		mInputText.addTextChangedListener(textWatcher);
		mInputText.setOnClickListener(this);
		mInputText.setOnTouchListener(this);
		mSend = (Button) findViewById(R.id.btn_send);
		mSend.setOnClickListener(this);
		mAddAttach = (ImageView) findViewById(R.id.iv_add_attach);
		mAddExpression = (ImageView) findViewById(R.id.iv_add_expression);
		mAddExpression.setOnClickListener(this);
		mAddExpression.setOnTouchListener(this);
		mExpressionPanel = (ExpressionPanel) findViewById(R.id.expression_panel);
		mExpressionPanel.startWork(getSupportFragmentManager());
		mExpressionPanel.setOnExpressionClickListener(expressionListener);

		getActionBarRightButton().setImageDrawable(
				getResources().getDrawable(R.drawable.icon_chat_title_bar));
		getActionBarRightButton().setOnClickListener(this);
		getActionBarRightButton().setVisibility(View.VISIBLE);
		showBackButton();

		messageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter(
				PushMessageReceiver.ACTION_MESSAGE_RECEIVER);
		registerReceiver(messageReceiver, filter);

		mFirstUser = XiaoYuanApp.getLoginUser(this);
		mSecondUser = ChatHelper.chatUser;
		ChatHelper.chatUser = null;
		setActionBarTitle(mSecondUser.userBean.getName());

		// test
		// mSecondUser.setChatUserId(SECOND_USER_ID);
		// mSecondUser.setChatChannelId(SECOND_USER_CHANNEL_ID);
		mAdapter = new ChatMsgAdapter(this, mSecondUser);
		mChatList.setAdapter(mAdapter);
		readChatCache();

		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	private OnExpressionClickListener expressionListener = new OnExpressionClickListener() {

		@Override
		public void onExpressionClick(int resId, String str) {
			Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
			ImageSpan imageSpan = new ImageSpan(ChatPanelActivity.this, bm);
			SpannableString spannableString = new SpannableString(str);
			spannableString.setSpan(imageSpan, str.indexOf("["),
					str.indexOf("]") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mInputText.append(spannableString);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_right:
			startActivity(new Intent(this, HomePageActivity.class));
			break;
		case R.id.et_input_text:
			if (mExpressionPanel.getVisibility() == View.VISIBLE) {
				mExpressionPanel.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_send:
			String text = mInputText.getText().toString().trim();
			if (!TextUtils.isEmpty(text)) {
				// String toWho =
				// XiaoYuanApp.getLoginUser(this).getUserId().equals(
				// mFirstUser.getUserId()) ? mSecondUser.getChatUserId()
				// : mFirstUser.getChatUserId();
				// String fromWho =
				// XiaoYuanApp.getLoginUser(this).getChatUserId();
				//
				// ChatHelper chatHelper = new ChatHelper();
				// ChatMessage chatMessage = new ChatMessage();
				// chatMessage.setMessage(text);
				// chatMessage.setTime(System.currentTimeMillis());
				// chatMessage.setFromWho(fromWho);
				// chatMessage.setToWho(toWho);
				// chatMessage.setToUser(mSecondUser.getUserId());
				// chatMessage.setFromUser(mFirstUser.getUserId());
				// chatMessage.setToUserName(mSecondUser.getName());
				// chatMessage.setFromUserName(mFirstUser.getName());
				// String send = ChatMessage.createMsgJson(chatMessage);
				// Log.d(TAG, "send >>> " + send);
				// chatHelper.pushMessage(toWho, mSecondUser.getChatChannelId(),
				// send);
				// updateChatList(chatMessage);
				// mInputText.setText("");
				// ChatHelper.writeChatMessage2DB(this, chatMessage, 1);
			}
			break;
		case R.id.iv_add_expression:
			int visibility = mExpressionPanel.getVisibility();
			if (visibility == View.VISIBLE) {
				mExpressionPanel.setVisibility(View.GONE);
			} else {
				imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mExpressionPanel.setVisibility(View.VISIBLE);
			}

			break;
		default:
			break;
		}
	}

	private class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("message");
			if (!TextUtils.isEmpty(message)) {
				Log.d(TAG, "receive >> " + message);
				ChatMessage chatMessage = ChatMessage.getChatMessage(message);
				updateChatList(chatMessage);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		updateRecentChat();
		clearUnReaded(mSecondUser.userBean.getUid());
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(messageReceiver);
		super.onDestroy();
	}

	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			String text = mInputText.getText().toString().trim();
			if (!TextUtils.isEmpty(text)) {
				mAddAttach.setVisibility(View.GONE);
				mSend.setVisibility(View.VISIBLE);
			} else {
				mAddAttach.setVisibility(View.VISIBLE);
				mSend.setVisibility(View.GONE);
			}
		}
	};

	/**
	 * 更新聊天列表
	 * 
	 * @param chatMessage
	 */
	private void updateChatList(ChatMessage chatMessage) {
		mAdapter.addChatMsg(chatMessage);
		mChatList.setSelection(mAdapter.getCount() - 1);
	}

	private void updateChatList(int location, ChatMessage chatMessage) {
		mAdapter.addChatMsg(location, chatMessage);
		mChatList.setSelection(mAdapter.getCount() - 1);
	}

	private void updateChatList(List<ChatMessage> chatMsgs) {
		mAdapter.addChatMsg(chatMsgs);
		mChatList.setSelection(mAdapter.getCount() - 1);
	}

	/**
	 * 更新最近聊天列表
	 */
	private void updateRecentChat() {
		ChatMsgAdapter adapter = (ChatMsgAdapter) mChatList.getAdapter();
		if (adapter.getCount() <= 0)
			return;
		ChatMessage chatMsg = (ChatMessage) adapter
				.getItem(adapter.getCount() - 1);

		SQLiteHelper helper = new SQLiteHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query(SQLiteHelper.TABLE_RECENT_CHAT, null,
				"userId = ?", new String[] { chatMsg.getToUser() }, null, null,
				null);

		ContentValues values = new ContentValues();
		values.put("times", chatMsg.getTime());
		values.put("recentMessage", chatMsg.getMessage());
		values.put("unReadedCount", 0);
		if (cursor.getCount() != 0) {
			db.update(SQLiteHelper.TABLE_RECENT_CHAT, values, "userId = ?",
					new String[] { chatMsg.getToUser() });
		} else {
			values.put("userId", chatMsg.getToUser());
			values.put("chatUserId", chatMsg.getToWho());
			values.put("chatUserName", chatMsg.getToUserName());
			// values.put("chatChannelId", chatMsg.getChannelId());
			db.insert(SQLiteHelper.TABLE_RECENT_CHAT, null, values);
		}
		cursor.close();
	}

	/**
	 * 读取聊天记录
	 */
	private void readChatCache() {
		if (mAdapter != null) {
			mAdapter.clear();
		}
		SQLiteHelper helper = new SQLiteHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(SQLiteHelper.TABLE_CHAT_MESSAGE, null,
				"fromUser = ? or toUser = ? ", new String[] {
						mSecondUser.userBean.getUid(), mSecondUser.userBean.getUid() },
				null, null, "times DESC", "20");
		List<ChatMessage> chatMsgs = new LinkedList<ChatMessage>();
		while (cursor.moveToNext()) {
			Long timestamp = cursor.getLong(cursor.getColumnIndex("times"));
			String fromUser = cursor.getString(cursor
					.getColumnIndex("fromUser"));
			String toUser = cursor.getString(cursor.getColumnIndex("toUser"));
			String message = cursor
					.getString(cursor.getColumnIndex("messages"));
			ChatMessage chatMsg = new ChatMessage();
			chatMsg.setTime(timestamp);
			chatMsg.setFromUser(fromUser);
			chatMsg.setToUser(toUser);
			chatMsg.setMessage(message);
			chatMsgs.add(0, chatMsg);
		}
		cursor.close();
		updateChatList(chatMsgs);
	}

	private void clearUnReaded(final String userId) {
		if (TextUtils.isEmpty(userId)) {
			return;
		}
		new Thread() {
			public void run() {
				SQLiteHelper helper = new SQLiteHelper(ChatPanelActivity.this);
				SQLiteDatabase db = helper.getReadableDatabase();
				ContentValues values = new ContentValues();
				values.put("unReadedCount", 0);
				db.update(SQLiteHelper.TABLE_RECENT_CHAT, values, "userId = ?",
						new String[] { userId });
			};
		}.start();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.et_input_text:
			imm.showSoftInput(mInputText, 0);
			mExpressionPanel.setVisibility(View.GONE);
			break;
		case R.id.lv_chat_msg:
			imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
			mExpressionPanel.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		return false;
	}
}
