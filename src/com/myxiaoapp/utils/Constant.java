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
	
	/*
	 * 头像
	 */
	public static final int IO_BUFFER_SIZE = 1024;
	public static int deviceWidth;
	public static int deviceHeight;
	
	static {
		WindowManager wm = (WindowManager) MyXiaoApp.getInstance().getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);
		deviceWidth = wm.getDefaultDisplay().getWidth();
		deviceHeight = wm.getDefaultDisplay().getHeight();
	//	Log.d(TAG, "width="+deviceWidth+","+"height="+deviceWidth);
	}
	
	public static class RequestId {
        public static final int ID_REGISTER = 100;
        public static final int ID_LOGIN = 101;
        public static final int ID_GET_FRIEND_LIST = 102;
        public static final int ID_PUBLISH_MOMENT = 103;
        public static final int ID_PUBLISH_GAME = 104;
        public static final int ID_SHOW_HALL = 105;
        public static final int ID_SHOW_MOMENT = 106;
        public static final int ID_SHOW_GAME = 107;
        public static final int ID_UPDATE_HALL = 108;
        public static final int ID_UPDATE_HALL_LOCATION = 109;
        public static final int ID_DELETE_IMAGE = 200;
        public static final int ID_GET_FRIEND_SINGLE = 201;
        public static final int ID_ASSOCIATE = 202;
        public static final int ID_INVITE = 203;
        public static final int ID_SHOW_INVITE = 204;
        public static final int ID_REPLY_INVITE = 205;
        public static final int ID_LOGOUT = 206;
        public static final int ID_CHAT = 207;
        public static final int ID_SHOW_FRIEND = 208;
        public static final int ID_ADD_FRIEND = 209;
    }

    public static class RequestUrl {
        public static final String URL_SERVER = "http://192.168.1.102:8080/PingPongServer";
        public static final String URL_REGISTER = URL_SERVER + "/register";
        public static final String URL_LOGIN = URL_SERVER + "/login";
        public static final String URL_SHOW_FRIEND = URL_SERVER + "/showfriend";
        public static final String URL_UPLOAD_IMAGE = URL_SERVER + "/uploadimage";
        public static final String URL_PUBLISH_MOMENT = URL_SERVER + "/publishmoment";
        public static final String URL_PUBLISH_GAME = URL_SERVER + "/publishgame";
        public static final String URL_SHOW_HALL = URL_SERVER + "/showhall";
        public static final String URL_SHOW_MOMENT = URL_SERVER + "/showmoment";
        public static final String URL_SHOW_GAME = URL_SERVER + "/showgame";
        public static final String URL_UPDATE_HALL = URL_SERVER + "/updatehall";
        public static final String URL_UPDATE_HALL_LOCATION = URL_SERVER + "/updatehalllocation";
        public static final String URL_DELETE_IMAGE = URL_SERVER + "/deleteimage";
        public static final String URL_UPLOAD_FRIEND_PORTRAIT = URL_SERVER + "/uploadfriendportrait";
        public static final String URL_ASSOCIATE = URL_SERVER + "/associate";
        public static final String URL_INVITE = URL_SERVER + "/invite";
        public static final String URL_SHOW_INVITE = URL_SERVER + "/showinvite";
        public static final String URL_REPLY_INVITE = URL_SERVER + "/replyinvite";
        public static final String URL_LOGOUT = URL_SERVER + "/logout";
        public static final String URL_CHAT = URL_SERVER + "/chat";
        public static final String URL_ADD_FRIEND = URL_SERVER + "/addfriend";
    }

    public static class ResultCode {
        public static final int RESULT_OK = 0;
        public static final int RESULT_DATA_INVALID = 102;
    }
}
