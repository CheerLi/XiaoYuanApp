package com.myxiaoapp.chathelper;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.SQLiteHelper;
import com.myxiaoapp.view.ExpressionPanel;

public class ChatHelper {

	private static final String TAG = "mydebug";

	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_METHOD_POST = "POST";

	private Handler handler = null;

	public static User chatUser = null; // loginUser <-->chatUser;

	private static final int MAX_RETRY = 10;
	private int retry = 0;

	public ChatHelper() {
	}

	public ChatHelper(Handler handler) {
		this.handler = handler;
	}

	public boolean pushMessage(String userId, String channelId, String message) {
		RestApi restApi = pushMessageParams();
		restApi.put(RestApi._USER_ID, userId);
		restApi.put(RestApi._MESSAGES, message);
		if (!TextUtils.isEmpty(channelId)) {
			restApi.put(RestApi._CHANNEL_ID, channelId);
		}
		String sign = getSign(restApi, REQUEST_METHOD_POST,
				RestApi.PUSH_MESSAGE_URL);
		restApi.put(RestApi._SIGN, sign);
		RequestParams params = new RequestParams(restApi);
		Log.d(TAG, "params >>> " + params.toString());
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.post(RestApi.PUSH_MESSAGE_URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int responseCode, Header[] headers,
							byte[] response) {
						if (responseCode == 200) {
							Log.d(TAG, "push success!");
							if (handler != null) {
								Message message = Message.obtain();
								message.what = 0;
								message.obj = new String(response);
								handler.sendMessage(message);
							}
						} else {

						}
					}

					@Override
					public void onFailure(int responseCode, Header[] headers,
							byte[] response, Throwable throwable) {
						// retry++;
						Log.d(TAG, "push fail , try " + retry);
						Log.d(TAG, new String(response));
					}
				});
		return true;
	}

	private RestApi pushMessageParams() {
		Long timestamp = System.currentTimeMillis() / 1000;
		RestApi restApi = new RestApi();
		restApi.put(RestApi._METHOD, RestApi.METHOD_PUSH_MESSAGE);
		restApi.put(RestApi._APIKEY, RestApi.API_KEY);
		restApi.put(RestApi._PUSH_TYPE, RestApi.PUSH_TYPE_USER);
		restApi.put(RestApi._MESSAGE_TYPE, RestApi.MESSAGE_TYPE_MESSAGE);
		restApi.put(RestApi._TIMESTAMP, timestamp + "");
		restApi.put(RestApi._MESSAGE_KEYS, timestamp + "");
		return restApi;
	}

	/**
	 * 查询离线消息
	 */
	public void fetchMessage(final String userId) {
		RestApi restApi = fetchMessageParams(userId);
		Long timestamp = System.currentTimeMillis() / 1000;
		restApi.put(RestApi._TIMESTAMP, timestamp + "");
		String sign = getSign(restApi, REQUEST_METHOD_POST,
				RestApi.FETCH_MESSAGE_URL);
		restApi.put(RestApi._SIGN, sign);
		RequestParams params = new RequestParams(restApi);
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.post(RestApi.FETCH_MESSAGE_URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int responseCode, Header[] headers,
							byte[] response) {
						Log.d(TAG, "fetch msg success");
						String responseStr = new String(response);
						Log.d(TAG, responseStr);
					}

					@Override
					public void onFailure(int responseCode, Header[] headers,
							byte[] response, Throwable throwable) {
						Log.d(TAG, "fetch msg fail");
						String responseStr = new String(response);
						Log.d(TAG, responseStr);
					}

				});
	}

	private RestApi fetchMessageParams(String userId) {
		RestApi restApi = new RestApi();
		restApi.put(RestApi._METHOD, RestApi.METHOD_FETCH_MESSAGE);
		restApi.put(RestApi._APIKEY, RestApi.API_KEY);
		restApi.put(RestApi._USER_ID, userId);
		return restApi;
	}

	private String getSign(RestApi restApi, String requestMethod, String url) {
		String str = restApi.toString();
		str = str.substring(1, str.length() - 1);
		str = sortStrings(str.split(", "));
		str = requestMethod + url + str + RestApi.SECRET_KEY;
		Log.d(TAG, "sign before >>> " + str);
		try {
			str = URLEncoder.encode(str, "UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(str.getBytes());
			byte[] md5 = md.digest();
			StringBuffer sb = new StringBuffer();
			sb.setLength(0);
			for (byte b : md5)
				sb.append(String.format("%02x", b & 0xff));
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryBindList() {
		return null;
	}

	/**
	 * 保存聊天记录
	 * 
	 * @param context
	 * @param chatMsg
	 * @param isReaded
	 *            0表示未读，1表示已读
	 */
	public static void writeChatMessage2DB(final Context context,
			final ChatMessage chatMsg, final int isReaded) {
		if (chatMsg == null)
			return;
		new Thread() {
			public void run() {
				SQLiteHelper helper = new SQLiteHelper(context);
				SQLiteDatabase db = helper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("times", chatMsg.getTime());
				values.put("toUser", chatMsg.getToUser());
				values.put("fromUser", chatMsg.getFromUser());
				values.put("messages", chatMsg.getMessage());
				values.put("isReaded", isReaded);
				db.insert(SQLiteHelper.TABLE_CHAT_MESSAGE, null, values);
			};
		}.start();
	}

	/**
	 * 更新最近聊天列表
	 * 
	 * @param context
	 * @param chatMsg
	 */
	public static void updateUnReadedChat(final Context context,
			final ChatMessage chatMsg) {
		if (chatMsg == null)
			return;
		new Thread() {
			public void run() {
				SQLiteHelper helper = new SQLiteHelper(context);
				SQLiteDatabase db = helper.getWritableDatabase();
				Cursor cursor = db.query(SQLiteHelper.TABLE_RECENT_CHAT, null,
						"userId = ?", new String[] { chatMsg.getFromUser() },
						null, null, null);

				ContentValues values = new ContentValues();
				values.put("times", chatMsg.getTime());
				values.put("recentMessage", chatMsg.getMessage());
				if (cursor.getCount() != 0) {
					cursor.moveToFirst();
					int unReadedCount = cursor.getInt(cursor
							.getColumnIndex("unReadedCount"));
					values.put("unReadedCount", unReadedCount + 1);
					db.update(SQLiteHelper.TABLE_RECENT_CHAT, values,
							"userId = ?",
							new String[] { chatMsg.getFromUser() });
				} else {
					values.put("userId", chatMsg.getFromUser());
					values.put("chatUserId", chatMsg.getFromWho());
					values.put("chatUserName", chatMsg.getFromUserName());
					// values.put("chatChannelId", chatMsg.getChannelId());
					values.put("unReadedCount", 1);
					db.insert(SQLiteHelper.TABLE_RECENT_CHAT, null, values);
				}
				cursor.close();
			};
		}.start();
	}

	/**
	 * 对字符串数组进行从小到大的排序
	 * 
	 * @param paramStr
	 * @return k1=v1k2=v2k3=v3
	 */
	private String sortStrings(String[] ss) {
		List<String> list = new ArrayList<String>();
		for (String s : ss) {
			list.add(s);
		}
		Collections.sort(list, new StringsComparator());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			sb.append(str);
		}
		return sb.toString();
	}

	private class StringsComparator implements Comparator<String> {

		@Override
		public int compare(String lhs, String rhs) {
			return lhs.compareTo(rhs);
		}
	}
}
