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
public class UserAlbum extends AsyncHttpPost {

	private final String CONSTANTLOG = "UserAlbum:";

	/**
	 * 获取用户相册接口
	 * 
	 * @param mContext
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param page_size
	 *            照片数量，最大一次获取八张，具体取决于界面最大显示多少张
	 */
	public UserAlbum(Context mContext, String user_id, String follow_id,
			int page_size) {
		super(mContext);
		this.url = "*";
		this.params = HttpRequestParams.userAlbumParams(user_id, follow_id,
				page_size);
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
