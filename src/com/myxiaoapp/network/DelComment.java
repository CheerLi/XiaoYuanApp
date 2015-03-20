/**
 * 2014年11月9日
 * ken
 */
package com.myxiaoapp.network;

import com.myxiaoapp.model.HttpRequestParams;

import android.content.Context;
import android.util.Log;

/**
 * @author ken
 * 
 */
public class DelComment extends AsyncHttpPost {

	private final String CONSTANTLOG = "DelComment:";

	/**
	 * 删除评论/回复接口参数
	 * 
	 * @param mContext
	 * @param comment_id
	 *            该评论的id
	 * @param user_id
	 *            评论人的id号
	 */
	public DelComment(Context mContext, String comment_id, String user_id) {
		super(mContext);
		this.url = url + "/yaf/index.php/Comments/delcommen";
		this.params = HttpRequestParams.delComment(comment_id, user_id);

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
