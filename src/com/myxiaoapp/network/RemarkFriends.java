/**
 * 2014年11月7日
 * ken
 */
package com.myxiaoapp.network;

import java.io.UnsupportedEncodingException;

import com.myxiaoapp.model.HttpRequestParams;

import android.content.Context;
import android.util.Log;

/**
 * @author ken
 *
 */
public class RemarkFriends extends AsyncHttpPost {

	private final String CONSTANTLOG = "RemarkFriends:";
	/**
	 * 设置好友备注接口
	 * @param mContext
	 * @param user_id  用户自己的id
	 * @param follow_id  对方用户名的id
	 * @param remark_name 备注名称
	 * @throws UnsupportedEncodingException 
	 */
	public RemarkFriends(Context mContext,String user_id, String follow_id,String remark_name) throws UnsupportedEncodingException {
		super(mContext);
		this.url =url+"/yaf/index.php/Relation/setremark";
		this.params = HttpRequestParams.remarkFriendsParams(user_id, follow_id, remark_name);
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
