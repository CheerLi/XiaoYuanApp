/**
 * 2014年11月9日
 * ken
 */
package com.myxiaoapp.network;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.myxiaoapp.model.HttpRequestParams;

import android.content.Context;
import android.util.Log;

/**
 * @author ken
 *
 */
public class UploadMsg extends AsyncHttpPost {

	private final String CONSTANTLOG = "UploadMsg:";
	/**
	 * 发布校园消息接口参数
	 * @param mContext
	 * @param user_id 用户id
	 * @param message_info 消息的内容(不能为空),要用URLencode进行编码	 
	 * @param pic_count 消息附带图片的数量	可选(没有的话为空)
	 * @param pic
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 **/
	public UploadMsg(Context mContext,String user_id, String message_info, int pic_count, String pic[]) throws UnsupportedEncodingException, FileNotFoundException {
		super(mContext);
		this.url=url+"/yaf/index.php/Schoolmsg/updatemsg";
		this.params = HttpRequestParams.uploadMsg(user_id, message_info, pic_count, pic);
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
