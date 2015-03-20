package com.myxiaoapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myxiaoapp.android.R;
import com.myxiaoapp.model.User;

public class CampusPeopleAdapter extends BaseAdapter {

	// test data
	// Object[][] data = { { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.male },
	// { "轻风抚面", "我要飞得更高", "1000m", R.drawable.female } };

	private Context mContext;

	private List<User> users;

	public CampusPeopleAdapter(Context context) {
		mContext = context;
	}

	public CampusPeopleAdapter(Context context, List<User> users) {
		mContext = context;
		this.users = users;
	}

	@Override
	public int getCount() {
		if (users == null) {
			return 0;
		}
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		if (users == null) {
			return null;
		}
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		User user = (User) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.campus_people_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.portrait = (ImageView) convertView
					.findViewById(R.id.portrait);
			viewHolder.nickname = (TextView) convertView
					.findViewById(R.id.nickname);
			viewHolder.personalSign = (TextView) convertView
					.findViewById(R.id.personal_sign);
			viewHolder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.nickname.setText(user.userBean.username);
		int sexId = 0;
		if (user.userBean.sex.equals(User.SEX_MALE)) {
			sexId = R.drawable.male;
		} else if (user.userBean.sex.equals(User.SEX_FEMALE)) {
			sexId = R.drawable.female;
		}
		Drawable right = null;
		if (sexId != 0) {
			right = mContext.getResources().getDrawable(sexId);
		}
		viewHolder.nickname.setCompoundDrawablesWithIntrinsicBounds(null, null,
				right, null);
		viewHolder.personalSign.setText(user.userBean.moto);
		viewHolder.distance.setText("距离100m");

		return convertView;
	}

	private class ViewHolder {
		ImageView portrait;
		TextView nickname;
		TextView personalSign;
		TextView distance;
	}

	public void setData(List<User> users) {
		this.users = users;
		notifyDataSetChanged();
	}

	public void addData(User user) {
		this.users.add(user);
		notifyDataSetChanged();
	}

}
