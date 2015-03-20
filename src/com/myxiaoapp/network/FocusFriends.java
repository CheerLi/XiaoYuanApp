/**
 * 2014年11月7日
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
public class FocusFriends extends AsyncHttpPost {

	private final String CONSTANTLOG = "FocusFriends:";
	/**关注好友
	 * @param mContext
	 * @param user_id  用户自己的id
	 * @param follow_id  对方用户名的id
	 */
	public FocusFriends(Context mContext,String user_id, String follow_id) {
		super(mContext);
		this.url = url+"/yaf/index.php/Relation/follow";
		this.params = HttpRequestParams.focusFriendsParams(user_id,follow_id);
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
