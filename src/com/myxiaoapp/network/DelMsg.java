/**
 * 2014年11月9日
 * ken
 */
package com.myxiaoapp.network;

import com.myxiaoapp.model.HttpRequestParams;

import android.content.Context;
import android.util.Log;

/**
 * 删除校园圈信息接口参数
 * 
 * @author ken
 * 
 */
public class DelMsg extends AsyncHttpPost {

	private final String CONSTANTLOG = "DelMsg:";

	/**
	 * 删除校园圈信息接口参数
	 * 
	 * @param mContext
	 * @param user_id
	 *            用户id
	 * @param msg_id
	 *            信息id
	 */
	public DelMsg(Context mContext, String user_id, String msg_id) {
		super(mContext);
		this.url = url + "/yaf/index.php/Schoolmsg/delmsg";
		this.params = HttpRequestParams.delMsg(user_id, msg_id);
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
