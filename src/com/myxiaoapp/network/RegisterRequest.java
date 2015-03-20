package com.myxiaoapp.network;

import org.json.JSONObject;

import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.utils.Constant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author JiangZhenJie
 * 
 */
public class RegisterRequest extends AsyncHttpPost {

	private final String CONSTANTLOG = "RegisterRequest:";
	private static final String TAG = "mydebug";

	/**
	 * @param mContext
	 */
	public RegisterRequest(Context mContext) {
		super(mContext);
		this.url = Constant.TEST_SERVER + "/yaf/index.php/Register";
		this.params = HttpRequestParams.registerParams();
	}

	private Message mMessage;

	public RegisterRequest(Context mContext, Handler handler, int what) {
		super(mContext);
		this.url = Constant.TEST_SERVER + "/yaf/index.php/Register";
		this.params = HttpRequestParams.registerParams();
		if (handler != null) {
			mMessage = Message.obtain(handler, what);
		}
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure()
	 */
	@Override
	public void onFailure() {
		Log.i(TAG,
				CONSTANTLOG + "statusCode=" + responseHandler.getStatusCode()
						+ "   message="
						+ (new String(responseHandler.getResponseBody())));
		if (mMessage != null) {
			mMessage.obj = null;
			mMessage.sendToTarget();
		}
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceive()
	 */
	@Override
	public void onReceive() {
		Log.i("mydebug", CONSTANTLOG + "成功获取数据");
		try {
			String rec = new String(responseHandler.getResponseBody(), "UTF-8");
			Log.d(TAG, rec);
			JSONObject jsonObj = new JSONObject(rec);
			String errno = jsonObj.getString("errno");
			if ("20".equals(errno)) {
				mMessage.obj = 1; // 只要不返回空就好
				mMessage.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
