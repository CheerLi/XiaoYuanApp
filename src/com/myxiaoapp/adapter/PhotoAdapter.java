package com.myxiaoapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myxiaoapp.android.PreviewImageActivity;
import com.myxiaoapp.android.R;
import com.myxiaoapp.utils.Constant;

/**
 * 校园圈 界面 相册GridView的Adapter
 * 
 * @author JiangZhenJie
 * @date 2014-9-20
 * @param flag
 *   flag = 0 :校园圈 flag = 1 :“个人资料”和“我”
 */
public class PhotoAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext;
	private int flag;
	List<Integer> resList = new ArrayList<Integer>();
	int[] resIds = { R.drawable.show_pic_1, R.drawable.show_pic_2,
			R.drawable.show_pic_3, R.drawable.show_pic_4, R.drawable.show_pic_5};

	
	public PhotoAdapter(Context context, int flag) {
		this.mContext = context;
		this.flag = flag;
		
		for (int i : resIds) {
			resList.add(i);
		}
		
		
	}
	
	/**
	 * 该构造函数解决查看别人的个人信息与查看自己的个人信息时，个人相册的最后一张是否加添加功能
	 * 当查看别人的个人相册时，调用该构造方法
	 * @param context
	 * @param flag1 Constant.ME
	 * @param flag2 Constant.CAMPUS
	 */
	public PhotoAdapter(Context context, int flag1, int flag2){
			this(context,flag2);
	}

	@Override
	public int getCount() {
		return resList.size();
	}

	@Override
	public Object getItem(int position) {
		return resList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_photos_item,null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.photo);
			viewHolder.imageView.setOnClickListener(this);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		int resId = (Integer) getItem(position);
		Drawable drawable = mContext.getResources().getDrawable(resId);
		viewHolder.imageView.setImageDrawable(drawable);
		return convertView;
	}

	private class ViewHolder {
		ImageView imageView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo:
			ImageView imageView = (ImageView) v;
			Intent intent = new Intent(mContext, PreviewImageActivity.class);
			intent.putExtra("image_url", (String) imageView.getTag());
			mContext.startActivity(intent);
			break;

		default:
			break;
		}
	}

}