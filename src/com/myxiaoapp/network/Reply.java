/**
 * 2014年11月9日
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
public class Reply extends AsyncHttpPost {

	private final String CONSTANTLOG = "Reply:";
	/**
	 * 评论/回复消息接口参数
	 * @param mContext
	 * @param msg_id	消息id
	 * @param comment	 评论的内容	,必须做URLencode
	 * @param user_id  	评论人的id号
	 * @param commented_uid 	被回复人的id号	可选 (没有的话为空 ) 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public Reply(Context mContext,String msg_id, String comment, String user_id, String commented_uid) throws UnsupportedEncodingException {
		super(mContext);
		this.url =url+"/yaf/index.php/Comments/reply";
		this.params = HttpRequestParams.reply(msg_id, comment, user_id, commented_uid);
		
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
