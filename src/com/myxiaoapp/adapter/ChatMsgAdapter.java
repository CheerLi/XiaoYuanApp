package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.chathelper.ChatMessage;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.Utils;
import com.myxiaoapp.view.CircleImageView;

public class ChatMsgAdapter extends BaseAdapter {

	private static final String TAG = "mydebug";

	private Context mContext;

	private List<ChatMessage> mChatMsgs;

	private User chatUser;
	private User loginUser;

	public ChatMsgAdapter(Context c, User chatUser) {
		this.mContext = c;
		this.chatUser = chatUser;
		this.loginUser = XiaoYuanApp.getLoginUser(c);
		mChatMsgs = new ArrayList<ChatMessage>();
	}

	@Override
	public int getCount() {
		return mChatMsgs.size();
	}

	@Override
	public Object getItem(int position) {
		return mChatMsgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		ChatMessage msg = (ChatMessage) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.chat_msg_list_item, parent, false);
			holder = new ViewHolder();
			// holder.chatTipsLayout = (LinearLayout) convertView
			// .findViewById(R.id.ll_tips);
			holder.hisChatMsgLayout = (RelativeLayout) convertView
					.findViewById(R.id.ll_his_chat);
			holder.myChatMsgLayout = (RelativeLayout) convertView
					.findViewById(R.id.ll_my_chat);
			// holder.chatTips = (TextView) convertView
			// .findViewById(R.id.tv_chat_tips);
			holder.hisChatMsg = (TextView) convertView
					.findViewById(R.id.tv_his_chat_msg);
			holder.myChatMsg = (TextView) convertView
					.findViewById(R.id.tv_my_chat_msg);
			holder.myChatMsg.setMaxWidth(getChatBackGroundMaxWidth());
			holder.hisPortrait = (CircleImageView) convertView
					.findViewById(R.id.iv_his_portrait);
			holder.myPortrait = (CircleImageView) convertView
					.findViewById(R.id.iv_my_portrait);
			holder.hisImage = (ImageView) convertView
					.findViewById(R.id.iv_his_image);
			holder.myImage = (ImageView) convertView
					.findViewById(R.id.iv_my_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		resetView(holder);
		if (msg.getFromUser().equals(loginUser.chatIdBean.chatUserId)) {
			holder.myChatMsgLayout.setVisibility(View.VISIBLE);
			holder.myChatMsg.setText(Utils.convertStringToSpannableString(
					mContext, msg.getMessage()));
		} else if (msg.getFromUser().equals(chatUser.chatIdBean.chatUserId)) {
			holder.hisChatMsgLayout.setVisibility(View.VISIBLE);
			holder.hisChatMsg.setText(Utils.convertStringToSpannableString(
					mContext, msg.getMessage()));
		}
		return convertView;
	}

	private void resetView(ViewHolder holder) {
		// holder.chatTipsLayout.setVisibility(View.GONE);
		holder.hisChatMsgLayout.setVisibility(View.GONE);
		holder.myChatMsgLayout.setVisibility(View.GONE);
	}

	class ViewHolder {
		// LinearLayout chatTipsLayout;
		RelativeLayout hisChatMsgLayout;
		RelativeLayout myChatMsgLayout;
		TextView chatTips;
		TextView hisChatMsg;
		TextView myChatMsg;
		CircleImageView hisPortrait;
		CircleImageView myPortrait;
		ImageView hisImage;
		ImageView myImage;
	}

	private int getChatBackGroundMaxWidth() {
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		return (int) (metrics.widthPixels * 1 / 2);
	}

	public void addChatMsg(ChatMessage info) {
		mChatMsgs.add(info);
		notifyDataSetChanged();
	}

	public void addChatMsg(int location, ChatMessage info) {
		mChatMsgs.add(location, info);
		notifyDataSetChanged();
	}

	public void addChatMsg(List<ChatMessage> chatMsgs) {
		mChatMsgs.addAll(chatMsgs);
		notifyDataSetChanged();
	}

	public void clear() {
		mChatMsgs.clear();
		notifyDataSetChanged();
	}

	// private static final Pattern EMOTION_URL = Pattern.compile("\\[*\\]");
	//
	// private CharSequence convertStringToSpannableString(String message) {
	// String hackTxt;
	// if (message.startsWith("[") && message.endsWith("]")) {
	// hackTxt = message + " ";
	// } else {
	// hackTxt = message;
	// }
	// SpannableString value = SpannableString.valueOf(hackTxt);
	//
	// Matcher localMatcher = EMOTION_URL.matcher(value);
	// while (localMatcher.find()) {
	// String str2 = localMatcher.group(0);
	// int k = localMatcher.start();
	// int m = localMatcher.end();
	// Log.d(TAG, "matcher >>> "+str2);
	// int resId = ExpressionPanel.getFaceResId(str2);
	// if (resId != 0) {
	// Bitmap bitmap = BitmapFactory.decodeResource(
	// mContext.getResources(), resId);
	// if (bitmap != null) {
	// ImageSpan localImageSpan = new ImageSpan(mContext, bitmap,
	// ImageSpan.ALIGN_BASELINE);
	// value.setSpan(localImageSpan, k, m,
	// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	// }
	// }
	// }
	// return value;
	// }

	// private CharSequence convertStringToSpannableString(String msg) {
	// if (TextUtils.isEmpty(msg))
	// return null;
	// int start = 0, end = msg.length();
	// SpannableString spanStr = SpannableString.valueOf(msg);
	// while (start < end) {
	// int indexS = msg.indexOf("[", start);
	// int indexE = msg.indexOf("]", indexS);
	// if (indexS < 0 || indexS >= end || indexE < 0 || indexE >= end) {
	// break;
	// }
	// String str = msg.substring(indexS, indexE + 1);
	// Log.d(TAG, str);
	// int resId = ExpressionPanel.getFaceResId(str);
	// if (resId != 0) {
	// ImageSpan imageSpan = new ImageSpan(mContext.getResources()
	// .getDrawable(resId));
	// spanStr.setSpan(imageSpan, indexS, indexE + 1,
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	// start = indexE;
	// }
	// }
	// return spanStr;
	// }
}
