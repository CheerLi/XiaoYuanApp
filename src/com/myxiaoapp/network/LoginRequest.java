package com.myxiaoapp.network;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.JSONHelper;

public class LoginRequest extends AsyncHttpPost {

	private final String CONSTANTLOG = "LoginRequest:";

	private static final boolean isDebug = true;
	private static final String TAG = "mydebug";
	private static final int WHAT_LOGIN_SUCCESS = 1;
	private static final int WHAT_LOGIN_FAILURE = 2;
	public LoginRequest(Context mContext, String userName, String password) {
		super(mContext);
		this.url = Constant.TEST_SERVER + "/yaf/index.php/login";
		this.params = HttpRequestParams.loginParams(userName, password);
	}

	private Message mMessage;

	public LoginRequest(Context mContext, String userName, String password,
			Handler handler) {
		super(mContext);
		this.url += "/yaf/index.php/login";
		this.params = HttpRequestParams.loginParams(userName, password);
		if (handler != null) {
			mMessage = Message.obtain(handler);
		}
	}

	@Override
	public void onFailure() {
		Log.i("mydebug",
				CONSTANTLOG + "statusCode=" + responseHandler.getStatusCode());
		if (mMessage != null) {
			mMessage.obj = null;
			mMessage.sendToTarget();
		}
	}

	@Override
	public void onReceive() {
		try {
			JSONObject localJSONObject = new JSONObject(new String(this.responseHandler.getResponseBody(), "UTF-8"));
		      String str = localJSONObject.getString("errno");
		      Log.d("mydebug", localJSONObject.toString());
		      if ("20".equals(str))
		      {
		        Log.d("mydebug", "login success");
		        this.mMessage.what = WHAT_LOGIN_SUCCESS;
		        UserBean localUserBean = (UserBean)JSONHelper.parse(localJSONObject.get("data"), UserBean.class);
		        Log.d("mydebug", localUserBean.toString());
		        if (this.mMessage != null)
		        {
		          Log.d("mydebug", "send message");
		          this.mMessage.obj = localUserBean;
		          this.mMessage.sendToTarget();
		        }
		      }
		      else if ("40".equals(str))
		      {
		        this.mMessage.what = WHAT_LOGIN_FAILURE;
		        this.mMessage.sendToTarget();
		        return;
		      }

		} catch (Exception e) {
			Log.d(TAG, "Exception");
			e.printStackTrace();
		}

	}
}
