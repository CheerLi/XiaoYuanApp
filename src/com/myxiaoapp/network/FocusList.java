/**
 * 2014年11月7日
 * ken
 */
package com.myxiaoapp.network;

import java.util.List;

import org.json.JSONObject;

import com.myxiaoapp.model.BaseModel;
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.network.FansList.FansListBean;
import com.myxiaoapp.utils.JSONHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author ken
 * 
 */
public class FocusList extends AsyncHttpPost {

	private final String CONSTANTLOG = "FocusList:";
	
	private final static String TAG= "mydebug";

	/**
	 * 用户关注列表获取接口
	 * 
	 * @param mContext
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param page_size
	 *            一次返回的条数 （可选）默认返回全部
	 * 
	 */
	public FocusList(Context mContext, String user_id, String follow_id,
			String page_size) {
		super(mContext);
		this.url = url + "/yaf/index.php/Followslist";
		this.params = HttpRequestParams.focusListParams(user_id, follow_id,
				page_size);
	}

	private Message mMessage;

	public FocusList(Context mContext, String user_id, String follow_id,
			String page_size, Handler handler, int what) {
		super(mContext);
		this.url = url + "/yaf/index.php/Followslist";
		this.params = HttpRequestParams.fansListParams(user_id, follow_id,
				page_size);
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
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceive()
	 */
	@Override
	public void onReceive() {
		Log.i("mydebug", CONSTANTLOG + "成功获取数据");
		
		try {
			JSONObject jsonObject = new JSONObject(new String(
					responseHandler.getResponseBody()));
			String errno = jsonObject.getString("errno");
			if ("20".equals(errno)) {
				FocusListBean bean = (FocusListBean) JSONHelper.parse(
						jsonObject.get("data"), FocusListBean.class);
				if (mMessage != null) {
					mMessage.obj = bean;
					mMessage.sendToTarget();
				}
				Log.d(TAG, bean.toString());
			}
			else {
				//do something
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static class FocusListBean extends BaseModel {
		public List<FocusFansBean> focusList;

		@Override
		public String toString() {
			return "FansListBean [fansList=" + focusList + "]";
		}

	}
}
