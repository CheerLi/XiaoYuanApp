package com.myxiaoapp.android;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;

import com.myxiaoapp.adapter.PersonAdapter;
import com.myxiaoapp.model.FocusFansBean;
import com.myxiaoapp.network.FansList;
import com.myxiaoapp.network.FansList.FansListBean;
import com.myxiaoapp.network.FocusList;
import com.myxiaoapp.network.FocusList.FocusListBean;
import com.myxiaoapp.utils.Utils;

/**
 * 关注的人列表和粉丝列表公用此Activity
 * 
 * @author JiangZhenJie
 * @date 2014-9-26
 */
public class PersonListActivity extends CommonActivity {

	private static final String TAG = "mydebug";
	private static final int DEFAULT_PAGE_SIZE = 20;

	private ListView mPersonsList;

	private FansListBean fansBean;
	private FocusListBean focusBean;

	private String user_id;
	private String follow_id;
	private int flag;

	private static final int WHAT_REQUEST_PERSON = 0x123;

	private Handler handler ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_list);
		setActionBarTitle("粉丝/关注");
		mPersonsList = (ListView) findViewById(R.id.lv_person);
		PersonAdapter adapter = new PersonAdapter(this);
		mPersonsList.setAdapter(adapter);

		handler = new PersonHandler(this);
		
		Intent intent = getIntent();
		user_id = intent.getStringExtra("user_id");
		follow_id = intent.getStringExtra("follow_id");
		flag = intent.getFlags();// 0表示显示粉丝， 1表示显示关注

		if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(follow_id)) {
			throw new IllegalArgumentException(
					"you should pass the user_id and follow_id to PersonListActivity");
		}
		Utils.showProgressDialog(this, true);
		request();
	}

	private void request() {
		if (flag == 0) {
			FansList fansList = new FansList(this, user_id, follow_id,
					DEFAULT_PAGE_SIZE + "", handler, WHAT_REQUEST_PERSON);
			fansList.post();
		} else if (flag == 1) {
			FocusList focusList = new FocusList(this, user_id, follow_id,
					DEFAULT_PAGE_SIZE + "", handler, WHAT_REQUEST_PERSON);
			focusList.post();
		}
	}

	private void updateUI() {
		List<FocusFansBean> beans = null;
		if (flag == 0) {
			beans = fansBean.fansList;
		} else if (flag == 1) {
			beans = focusBean.focusList;
		}
		PersonAdapter adapter = (PersonAdapter) mPersonsList.getAdapter();
		adapter.setData(beans);
		Utils.dismissProgressDialog();
	}

	private static class PersonHandler extends Handler {
		private WeakReference<PersonListActivity> mOuter;

		public PersonHandler(PersonListActivity ac) {
			mOuter = new WeakReference<PersonListActivity>(ac);
		}

		@Override
		public void handleMessage(Message msg) {
			PersonListActivity ac = mOuter.get();
			if (ac == null)
				return;

			switch (msg.what) {
			case WHAT_REQUEST_PERSON:
				if (ac.flag == 0) {
					ac.fansBean = (FansListBean) msg.obj;
					ac.updateUI();
				} else if (ac.flag == 1) {
					ac.focusBean = (FocusListBean) msg.obj;
					ac.updateUI();
				}
				break;

			default:
				break;
			}
		}
	}

}
