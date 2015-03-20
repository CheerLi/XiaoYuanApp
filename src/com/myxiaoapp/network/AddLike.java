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
public class AddLike extends AsyncHttpPost {
	private final String CONSTANTLOG = "AddLike:";
	
	/**
	 * 点赞接口参数	 
	 * @param mContext
	 * @param user_id  用户id
	 * @param usered_id  被点赞用户id
	 */
	public AddLike(Context mContext,String user_id,String usered_id) {
		super(mContext);
		this.url = url+"/yaf/index.php/Likes/addlike";
		this.params = HttpRequestParams.addLike(user_id, usered_id);

	}


	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure()
	 */
	@Override
	public void onFailure() {
		
		Log.i("mydebug", CONSTANTLOG+"statusCode="+responseHandler.getStatusCode());
	}

	/* 
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceive()
	 */
	@Override
	public void onReceive() {

		Log.i("mydebug", CONSTANTLOG+"成功获取数据");
	}


}
