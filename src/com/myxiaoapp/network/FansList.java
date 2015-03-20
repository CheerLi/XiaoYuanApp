/**
 * 2014年11月7日
 * ken
 */
package com.myxiaoapp.network;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.myxiaoapp.model.BaseModel;
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.utils.JSONHelper;

/**
 * @author ken
 * 
 */
public class FansList extends AsyncHttpPost {

	private final String CONSTANTLOG = "FansList:";
	private static final String TAG = "mydebug";

	/**
	 * 获取粉丝列表接口
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
	public FansList(Context mContext, String user_id, String follow_id,
			String page_size) {
		super(mContext);
		this.url = url + "/yaf/index.php/Fanslist";
		this.params = HttpRequestParams.fansListParams(user_id, follow_id,
				page_size);
	}

	private Message mMessage;

	public FansList(Context mContext, String user_id, String follow_id,
			String page_size, Handler handler, int what) {
		super(mContext);
		this.url = url + "/yaf/index.php/Fanslist";
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
			JSONObject jsonObject = new JSONObject(new String(
					responseHandler.getResponseBody()));
			String errno = jsonObject.getString("errno");
			if ("20".equals(errno)) {
				FansListBean bean = (FansListBean) JSONHelper.parse(
						jsonObject.get("data"), FansListBean.class);
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

	public static class FansListBean extends BaseModel {
		public List<FocusFansBean> fansList;

		@Override
		public String toString() {
			return "FansListBean [fansList=" + fansList + "]";
		}
		
	}

}
