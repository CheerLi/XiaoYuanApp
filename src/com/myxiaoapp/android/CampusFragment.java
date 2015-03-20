package com.myxiaoapp.android;

import java.util.LinkedList;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myxiaoapp.adapter.CampusPeopleAdapter;
import com.myxiaoapp.model.User;
import com.myxiaoapp.utils.Utils;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshBase.Mode;
import com.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.pulltorefresh.library.PullToRefreshListView;

public class CampusFragment extends Fragment implements OnClickListener,
		OnItemClickListener, OnRefreshListener<ListView> {

	private ListView mPeopleList;
	private PullToRefreshListView mPullToRefreshListView;
	private List<User> mCampusUsers;
	public static User selectedUser;

	// private EditText mSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_campus_people,
				container, false);
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.people_list);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(this);
		mPeopleList = mPullToRefreshListView.getRefreshableView();
		CampusPeopleAdapter peopleAdapter = new CampusPeopleAdapter(
				getActivity());
		mPeopleList.setAdapter(peopleAdapter);
		mPeopleList.setOnItemClickListener(this);

		// mSearch = (EditText) view.findViewById(R.id.et_search);
		// mSearch.clearFocus();

		mCampusUsers = new LinkedList<User>();
		// test
//		User user1 = new User("589396412218359559", "4042017386297853232", "10089", "jiang", "jiang", "1", null, null,
//				"深圳大学", "18825193807", "write the code change the world!", 125,
//				120, 130, null, 2);
//		User user2 = new User("606844474145847127", "4600684025511915918", "10001", "admin", "admin", "1", null, null,
//				"深圳大学", "18825193807", "write the code,change the world!", 125,
//				120, 130, null, 2);
//		User user3 = new User(null, null, "10004", "Jie", "Cool", "1", null, null,
//				"深圳大学", "18825193807", "write the code change the world!", 125,
//				120, 130, null, 2);
//		User user4 = new User(null, null, "10005", "JieGe", "Miss", "1", null, null,
//				"深圳大学", "18825193807", "write the code change the world!", 125,
//				120, 130, null, 2);
//
//		mCampusUsers.add(user1);
//		mCampusUsers.add(user2);
//		mCampusUsers.add(user3);
//		mCampusUsers.add(user4);
		
		peopleAdapter.setData(mCampusUsers);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		/* 跳到第二张图 */
		startActivity(new Intent(getActivity(), HomePageActivity.class));
		selectedUser = mCampusUsers.get(position - 1);
		/* 跳到第三张图 */
		// startActivity(new Intent(getActivity(),DetailsActivity.class));

		/* 跳到第四张图 */
		// startActivity(new Intent(getActivity(),CampusNewsActivity.class));

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Utils.showProgressDialog(getActivity(), R.string.loading, true,
				new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						mPullToRefreshListView.onRefreshComplete();
					}
				});
	}

}
