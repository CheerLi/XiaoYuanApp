package com.myxiaoapp.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.myxiaoapp.model.BaseModel;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.LastPicBean;
import com.myxiaoapp.model.LastMomentBean;
import com.myxiaoapp.model.UserBean;
import com.myxiaoapp.utils.JSONHelper;

/**
 * 获取用户信息
 * 
 * @author ken
 * 
 */
public class GetUserInfo extends AsyncHttpPost {

	private final String CONSTANTLOG = "GetUserInfo:";
	private static final String TAG = "mydebug";

	/**
	 * 
	 * @param mContext
	 * @param user_id
	 *            用户id
	 * @param interest_id
	 *            对方id(或自己的id)
	 */
	public GetUserInfo(Context mContext, String user_id, String interest_id) {
		super(mContext);
		url = url + "/yaf/index.php/Getinfo";
		params = HttpRequestParams.getUserInfoParams(user_id, interest_id);
	}

	/*
	 * 建议：每个请求，实现一个内部模型类，通过Handler向调用处回传请求模型类
	 */
	private Message mMessage;

	public GetUserInfo(Context mContext, String user_id, String interest_id,
			Handler handler, int what) {
		super(mContext);
		url = url + "/yaf/index.php/Getinfo";
		params = HttpRequestParams.getUserInfoParams(user_id, interest_id);
		if (handler != null) {
			mMessage = Message.obtain(handler, what);
		}
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure()
	 */
	@Override
	public void onFailure() {
		Log.i("mydebug",
				CONSTANTLOG + "statusCode=" + responseHandler.getStatusCode());
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
		String rec = new String(responseHandler.getResponseBody());
		Log.i(TAG, CONSTANTLOG + "成功获取数据");
		Log.d(TAG, rec);
		GetUserInfoBean bean;
		try {
			JSONObject jsonObj = new JSONObject(rec);
			//在这里判断返回数据是否正确
			String errno = jsonObj.getString("errno");
			if("20".equals(errno)){
				Object obj = jsonObj.get("data");
				bean = (GetUserInfoBean) JSONHelper.parse(obj,
						GetUserInfoBean.class);
				Log.d(TAG, bean.toString());
				if (mMessage != null) {
					mMessage.obj = bean;
					mMessage.sendToTarget();
				}
			}
			else {
				//do something
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class GetUserInfoBean extends BaseModel {
		public UserBean user;
		public List<LastPicBean> last_pic_list;
		public List<LastMomentBean> last_moments;

		public GetUserInfoBean() {
			user = new UserBean();
			last_pic_list = new ArrayList<LastPicBean>();
			last_moments = new ArrayList<LastMomentBean>();
		}

		@Override
		public String toString() {
			return "GetUserInfoBean [user=" + user
					+ "***************** last_pic_list=" + last_pic_list
					+ "****************** last_moments=" + last_moments + "]";
		}
	}

}
