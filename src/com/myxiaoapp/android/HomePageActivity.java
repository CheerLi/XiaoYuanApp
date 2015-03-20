package com.myxiaoapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.chathelper.ChatHelper;
import com.myxiaoapp.model.User;
import com.myxiaoapp.network.CancelFocus;
import com.myxiaoapp.view.CircleImageView;

/**
 * 个人主页
 * 
 * @author JiangZhenJie
 * @date 2014-10-20
 */
public class HomePageActivity extends CommonActivity implements
		OnClickListener, OnMenuItemClickListener {

	private User userInfo;

	private CircleImageView mPortrait; // 头像
	private TextView mName; // 昵称或备注
	private TextView mSchool; // 学校
	private TextView mPersonalSign; // 个性签名
	private LinearLayout mFocus; // 他的关注
	private LinearLayout mFans; // 他的粉丝
	//private TextView mAlbum;//相册标签提示
	private GridView mPhotoAlbum; // 相册
	private GridView mPhotoMood;//心情附带的照片
	private Button mGoFocus; // 关注
	private Button mGoChat; // 聊天
	private Button mExit;//退出登录，隐藏
	//private int leftMargin;
	//private LinearLayout mOperateLayout;

	// private LinearLayout mOperateLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		setActionBarTitle(R.string.title_activity_details);
		initView();
		userInfo = CampusFragment.selectedUser;
		CampusFragment.selectedUser = null;
		mName.setText(userInfo.userBean.name);
	}

	private void initView() {
		mPortrait = (CircleImageView) findViewById(R.id.portrait);
		mName = (TextView) findViewById(R.id.name);
		mSchool = (TextView) findViewById(R.id.school);
		mPersonalSign = (TextView) findViewById(R.id.personal_sign);
		mFocus = (LinearLayout) findViewById(R.id.tv_focus_number);
		mFocus.setOnClickListener(this);
		mFans = (LinearLayout) findViewById(R.id.tv_fans_number);
		mFans.setOnClickListener(this);

	//	mAlbum = (TextView)findViewById(R.id.myAlbum);
		mPhotoAlbum = (GridView) findViewById(R.id.gv_photo_album); 
		mPhotoAlbum.setAdapter(new PhotoAdapter(this ,Constant.FLAG_ME,Constant.FLAG_CAMPUS));
		
		mPhotoMood = (GridView)findViewById(R.id.gv_photo_mood);
		mPhotoMood.setAdapter(new PhotoAdapter(this ,Constant.FLAG_CAMPUS));
		mGoFocus = (Button)findViewById(R.id.go_focus);
		mGoFocus.setOnClickListener(this);
		mGoChat = (Button) findViewById(R.id.go_chat);
		mGoChat.setOnClickListener(this);
	//	mExit = (Button)findViewById(R.id.exit);
	//	mExit.setVisibility(View.GONE);

/*		mPhotoAlbum = (GridView) findViewById(R.id.gv_photo_album);
		mPhotoAlbum.setAdapter(new PhotoAdapter(this, 0));
		mGoFocus = (Button) findViewById(R.id.go_focus);
		mGoFocus.setOnClickListener(this);
		mGoChat = (Button) findViewById(R.id.go_chat);
		mGoChat.setOnClickListener(this);
*/		// mOperateLayout = (LinearLayout) findViewById(R.id.ll_operate);
		// mOperateLayout.setVisibility(View.VISIBLE);

		showMenuButton(R.menu.details, this);
		showBackButton();
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_sign:
			break;
		case R.id.tv_focus_number:
			startActivity(new Intent(this, PersonListActivity.class));
			break;
		case R.id.tv_fans_number:
			startActivity(new Intent(this, PersonListActivity.class));
			break;
		case R.id.go_chat:
			ChatHelper.chatUser = userInfo;
			startActivity(new Intent(this, ChatPanelActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_note:
			startActivity(new Intent(this, RemarkActivity.class));
			break;

		default:
			break;
		}
		return true;
	}
}
