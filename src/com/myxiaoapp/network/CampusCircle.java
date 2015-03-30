/**
 * 2014年11月7日
 * ken
 */
package com.myxiaoapp.network;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.MomentBean;
import com.myxiaoapp.network.GetUserInfo.GetUserInfoBean;
import com.myxiaoapp.utils.JSONHelper;
import com.myxiaoapp.utils.Utils;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.pulltorefresh.library.PullToRefreshListView;

/**
 * @author ken
 * 
 */
public class CampusCircle extends AsyncHttpPost {
	private final String CONSTANTLOG = "CampusCircle:";

	private static final String TAG = "mydebug";

	PullToRefreshListView mPullToRefreshListView;

	/**
	 * 校园圈列表获取接口： 校园圈返回的数据包括单条校园圈中的图片列表，回复列表和点赞列表。
	 * 
	 * @param mContext
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param msg_id
	 *            (1)如果是要查看新的消息，参数是最新的消息id;(2)如果要查看之前的消息，参数是最后那条消息id
	 * @param msg_tyle
	 *            (0表示从获取新的消息，1表示获取已经之前的信息)
	 * @param page_size
	 *            一次返回的校园圈信息数量，最大20条
	 * 
	 */
	public CampusCircle(Context mContext, String user_id, String follow_id,
			String msg_id, String msg_tyle, String page_size) {
		super(mContext);
		this.url = url + "/yaf/index.php/Schoolinfo";
		this.params = HttpRequestParams.campusCircle(user_id, follow_id,
				msg_id, msg_tyle, page_size);
	}

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onFailure()
	 */
	@Override
	public void onFailure() {

		Log.i("mydebug",
				CONSTANTLOG + "statusCode=" + responseHandler.getStatusCode());
	}

	private OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if (mPullToRefreshListView.isHeaderShown()) { // 上拉
				Utils.showProgressDialog(mContext, R.string.refreshing, true,
						cancelListener);
			} else if (mPullToRefreshListView.isFooterShown()) { // 下拉
				Utils.showProgressDialog(mContext, R.string.loading, true,
						cancelListener);
			}
		}
	};
	private OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			mPullToRefreshListView.onRefreshComplete();
		}
	};

	/*
	 * @see com.myxiaoapp.listener.OnResponseListener#onReceive()
	 */
	@Override
	public void onReceive() {
		String rec = null;
		try {
			rec = new String(responseHandler.getResponseBody(),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
		Log.i(TAG, CONSTANTLOG + "成功获取数据");
		Log.d(TAG, rec);
		
		CampusCircleBean bean;
		try {
			JSONObject jsonObj = new JSONObject(rec);
			String errno = jsonObj.getString("errno");
			if("20".equals(errno)){
				Object obj = jsonObj.get("data");
				bean = (CampusCircleBean) JSONHelper.parse(obj,
						CampusCircleBean.class);
				Log.d(TAG, bean.toString());
			}else {
				//do something
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//
//		try {
//			ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
//			JSONArray ja = new JSONArray(new String(
//					responseHandler.getResponseBody()));
//			int len = ja.length();
//			for (int i = 0; i < len; i++) {
//				// 插入顺序：昵称、时间、内容、url数目、缩略图url、原图url、赞人数、评论人数
//				ArrayList<String> data = new ArrayList<String>();
//				JSONObject jo = ja.getJSONObject(i);
//
//				data.add(jo.getString("user_id"));
//
//				data.add(jo.getString("m_time"));
//
//				data.add(URLDecoder.decode(jo.getString("m_info")));
//
//				JSONArray smallUrlArray = jo.getJSONArray("m_spictures");
//				int urlNum = smallUrlArray.length();
//				data.add("" + urlNum);
//				for (int j = 0; j < urlNum; j++) {
//					data.add(baseUrl + smallUrlArray.getString(j));
//				}
//
//				JSONArray urlArray = jo.getJSONArray("m_pictures");
//				for (int j = 0; j < urlNum; j++) {
//					data.add(baseUrl + urlArray.getString(j));
//				}
//
//				data.add(jo.getString("m_likesnum"));
//
//				data.add(jo.getString("m_commentnum"));
//
//				datas.add(data);
//			}
//			View v = LayoutInflater.from(mContext).inflate(
//					R.layout.activity_campus_news, null);
//			mPullToRefreshListView = (PullToRefreshListView) v
//					.findViewById(R.id.campus_news_list);
//			mPullToRefreshListView.setMode(Mode.BOTH);
//			mPullToRefreshListView.setOnRefreshListener(refreshListener);
//			ListView mNewsList = mPullToRefreshListView.getRefreshableView();
//			Log.d("mydebug", CONSTANTLOG + "before adapter");
//			// mNewsList.setAdapter(new CampusNewsAdapter(mContext,datas));
//			Log.d("mydebug", CONSTANTLOG + "after adapter");
//
//		} catch (JSONException e) {
//
//			e.printStackTrace();
//		}
	}

	public static class CampusCircleBean {
		List<MomentBean> momentList;

		@Override
		public String toString() {
			return "CampusCircleBean [momentList=" + momentList + "]";
		}
		
		
	}

}
