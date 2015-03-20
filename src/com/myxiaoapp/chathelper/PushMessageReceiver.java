package com.myxiaoapp.chathelper;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.myxiaoapp.android.LoginActivity;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.Utils;

public class PushMessageReceiver extends FrontiaPushMessageReceiver {

	private static final String TAG = "mydebug";

	public static final String ACTION_MESSAGE_RECEIVER = "com.myxiaoapp.action.MESSAGE_RECEIVE";

	@Override
	public void onBind(final Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		Log.d(TAG, "onBind errorCode = " + errorCode + " userId = " + userId
				+ " channelId = " + channelId);

		if (errorCode == 0) {
			Log.d(TAG, "onBind Success");
			// sava userId and channelId
			SharedPreferences sharedPref = context.getSharedPreferences(
					Constant.SHARE_PREFS_CHAT_INFO, Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit();
			editor.putString("chat_user_id", userId);
			editor.putString("chat_channel_id", channelId);
			editor.commit();
//			if (!XiaoYuanApp.isLogin) {
//				redirectToLogin(context);
//			}
			// 查询离线消息
			// ChatHelper chatHelper = new ChatHelper();
			// chatHelper.fetchMessage(XiaoYuanApp.getLoginUser(context).getChatUserId());
		} else {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					PushManager.startWork(context,
							PushConstants.LOGIN_TYPE_API_KEY, RestApi.API_KEY);
				}
			}, 2000 * 1000);
		}

	}

	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> successTags, List<String> failTags, String requestId) {

	}

	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {

	}

	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		// 写入数据库，并且发送广播通知
		Log.i(TAG, "receive message >>> " + message);
		ChatMessage chatMsg = ChatMessage.getChatMessage(message);
		ChatHelper.writeChatMessage2DB(context, chatMsg, 0);
		ChatHelper.updateUnReadedChat(context, chatMsg);
		Intent intent = new Intent(ACTION_MESSAGE_RECEIVER);
		intent.putExtra("message", message);
		context.sendBroadcast(intent);
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContent) {

	}

	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> successTags, List<String> failTags, String requestId) {

	}

	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		Log.i(TAG, "onUnbind errorCode = " + errorCode + " requestId = "
				+ requestId);
	}

	private void redirectToLogin(Context context) {
		Utils.dismissProgressDialog();
		Intent intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
