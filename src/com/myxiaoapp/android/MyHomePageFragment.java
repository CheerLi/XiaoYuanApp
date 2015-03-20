package com.myxiaoapp.android;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.GetUserInfo;
import com.myxiaoapp.network.GetUserInfo.GetUserInfoBean;
import com.myxiaoapp.utils.Constant;

/**
 * 注意：修改布局文件时请保留id不变 —— mark by jzj
 */
public class MyHomePageFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private static final String TAG = "mydebug";

	private ImageView mPortrait; // 头像
	private TextView mName; // 昵称或备注
	private TextView mSchool; // 学校
	private TextView mPersonalSign; // 个性签名
	private LinearLayout mFocus; // 他的关注
	private LinearLayout mFans; // 他的粉丝
	private GridView mPhotoAlbum; // 个人相册
	private GridView mPhotoMood;// 校园心情附带的照片
	private Button mGoFocus; // 关注
	private Button mGoChat; // 聊天
	private LinearLayout personCampus;// 个人校园圈

	private static final int MODIFIY_PERSONAL_SIGN = 1;

	private static final int WHAT_GET_USER_INFO = 0;
	private GetUserInfoBean getUserInfoBean;

	private Handler homeHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home_page, container,
				false);
		findView(view);
		getDetail();
		homeHandler = new HomePageHandler(this);
		return view;
	}

	private void findView(View view) {
		mPortrait = (ImageView) view.findViewById(R.id.portrait);
		mName = (TextView) view.findViewById(R.id.name);
		mSchool = (TextView) view.findViewById(R.id.school);
		mPersonalSign = (TextView) view.findViewById(R.id.person_sign);
		mFocus = (LinearLayout) view.findViewById(R.id.tv_focus_number);
		mFocus.setOnClickListener(this);
		mFans = (LinearLayout) view.findViewById(R.id.tv_fans_number);
		mFans.setOnClickListener(this);
		mPhotoAlbum = (GridView) view.findViewById(R.id.gv_photo_album);
		mPhotoAlbum
				.setAdapter(new PhotoAdapter(getActivity(), Constant.FLAG_ME));
		mPhotoMood = (GridView) view.findViewById(R.id.gv_photo_mood);
		mPhotoMood
				.setAdapter(new PhotoAdapter(getActivity(), Constant.FLAG_ME));
		mPhotoMood.setOnItemClickListener(this);
		mGoFocus = (Button) view.findViewById(R.id.go_focus);
		mGoFocus.setVisibility(View.GONE);
		mGoChat = (Button) view.findViewById(R.id.go_chat);
		mGoChat.setVisibility(View.GONE);
		personCampus = (LinearLayout) view.findViewById(R.id.go_person_campus);
		personCampus.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		User loginUser = XiaoYuanApp.getLoginUser(getActivity());
		Intent intent;
		switch (v.getId()) {
		case R.id.personal_sign:
			startActivityForResult(new Intent(getActivity(),
					ModifyPersonalSignActivity.class), MODIFIY_PERSONAL_SIGN);
			break;
		case R.id.tv_fans_number:
			intent = new Intent(getActivity(), PersonListActivity.class);
			intent.putExtra("user_id", loginUser.userBean.uid);
			intent.putExtra("follow_id", loginUser.userBean.uid);
			intent.setFlags(0);
			startActivity(intent);
			break;
		case R.id.tv_focus_number:
			intent = new Intent(getActivity(), PersonListActivity.class);
			intent.putExtra("user_id", loginUser.userBean.uid);
			intent.putExtra("follow_id", loginUser.userBean.uid);
			intent.setFlags(1);
			startActivity(intent);
			break;
		case R.id.go_person_campus:
			startActivity(new Intent(getActivity(), MessageActivity.class));
			break;
		default:
			break;
		}
	}

	/*
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getActivity(), MessageActivity.class));

	}

	private void getDetail() {
		User user = XiaoYuanApp.getLoginUser(getActivity());
		Log.d(TAG, user.userBean.uid);
		GetUserInfo getUserInfo = new GetUserInfo(getActivity(),
				user.userBean.uid, user.userBean.uid, homeHandler,
				WHAT_GET_USER_INFO);
		getUserInfo.post();
	}

	private void updateUI() {
		if (getUserInfoBean == null)
			return;
		mName.setText(getUserInfoBean.user.name);
		mSchool.setText(getUserInfoBean.user.college);
		String moto = getUserInfoBean.user.moto;
		if (TextUtils.isEmpty(moto) || moto.equals("null")) {
			moto = "签名还在酝酿中...";
		}
		mPersonalSign.setText(moto);

	}

	private static class HomePageHandler extends Handler {
		private WeakReference<MyHomePageFragment> mOuter;

		public HomePageHandler(MyHomePageFragment homeFragment) {
			mOuter = new WeakReference<MyHomePageFragment>(homeFragment);
		}

		@Override
		public void handleMessage(Message msg) {
			MyHomePageFragment homeFragment = mOuter.get();
			if (homeFragment == null)
				return;

			switch (msg.what) {
			case WHAT_GET_USER_INFO:
				homeFragment.getUserInfoBean = (GetUserInfoBean) msg.obj;
				homeFragment.updateUI();
				break;

			default:
				break;
			}
		}
	}
}
