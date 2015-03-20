package com.myxiaoapp.network;

import android.content.Context;
import android.util.Log;

import com.myxiaoapp.model.HttpRequestParams;

/**
 * 回传经纬度，更新用户地理位置
 * 
 * @author ken
 * 
 */
public class UpdateLocation extends AsyncHttpPost {

	private final String CONSTANTLOG = "UpdateLocation:";

	private static final String TAG = "mydebug";

	/**
	 * @param mContext
	 * @param username
	 *            用户名（手机号）
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 */
	public UpdateLocation(Context mContext, String username, String latitude,
			String longitude) {
		super(mContext);
		this.url = url + "/yaf/index.php/Backstate";
		this.params = HttpRequestParams.updateLocationParams(username,
				latitude, longitude);
		Log.d(TAG, params.toString());
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
	}

}
