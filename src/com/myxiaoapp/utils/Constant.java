package com.myxiaoapp.utils;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.myxiaoapp.android.MyXiaoApp;
import com.myxiaoapp.android.R; 
public class Constant {
	public static final int FLAG_ME = 1;
	public static final int FLAG_Discovery = 2;
	public static final int FLAG_CAMPUS = 3;
	/**
	 * 设备系统，2表示Android，修改需慎重。
	 */
	public static final int ANDROID = 2;

	/**
	 * App版本号
	 */
	public static final String APP_VERSION = "v0.1";

	public static final int HTTP_GET = 1;
	public static final int HTTP_POST = 2;

	public static final String TEST_SERVER = "http://120.24.76.148";
	public static final String CHECK_PHONE_URL = "/servlet/CheckPhone";

	/**
	 * 默认字符集
	 */
	public static final String charSet = "UTF-8";

	public static final String SHARE_PRE_LOGIN_INFO = "login_information";
	public static final String SHARE_PREFS_CHAT_INFO = "chat_infomation";

	/*
	 * 表情相关
	 */
	public static final int EXPRESSION_NUMBER = 50;
	public static final int START_ID = R.drawable.emoji_000;
	
	public static int deviceWidth;
	public static int deviceHeight;
	
	static {
		WindowManager wm = (WindowManager) MyXiaoApp.getInstance().getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);
		deviceWidth = wm.getDefaultDisplay().getWidth();
		deviceHeight = wm.getDefaultDisplay().getHeight();
	//	Log.d(TAG, "width="+deviceWidth+","+"height="+deviceWidth);
	}
}
