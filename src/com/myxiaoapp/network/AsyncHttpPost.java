/**
 * 2014年11月6日
 * ken
 */
package com.myxiaoapp.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpResponseHandler;

/**
 * 所有接口的基类，继承OnReceiveResponseListener接口，当接受后台返回数据时，回调原接口的OnReceive方法
 * @author ken
 *
 */

public abstract class AsyncHttpPost implements OnResponseListener{
	
	Context mContext;								//上下文，有可能需要被更新的
	AsyncHttpClient client ;						//单例连接，登录之后，一直用同一个连接和后台交互
	String baseUrl = "http://120.24.76.148";
	String url = "http://120.24.76.148";	//请求后台的url,由子类根据自己的需求初始化
	RequestParams params;							//请求后台的参数,同样由子类根据自己的需求初始化
	HttpResponseHandler responseHandler ;			//保存返回数据								
	AsyncHttpPost(Context mContext){
		this.mContext = mContext;
		this.client = SingleAsyncClient.getSingleClient();
		this.responseHandler = new HttpResponseHandler(AsyncHttpPost.this);
	}
	

	/**
	 * 向后台发送数据
	 */
	public void post(){
		client.post(url, params,responseHandler);
	}
}
