package com.myxiaoapp.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myxiaoapp.adapter.CampusNewsCommentAdapter;
import com.myxiaoapp.adapter.PhotoAdapter;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.InputUtils;
import com.myxiaoapp.utils.Utils;
import com.myxiaoapp.view.MyGridView;

/**
 * @author : liqihang
 * @time : 2014/9/19
 * 
 *       第四张图，查看单条详细校园心情，可进行举报，评论，点赞
 */

public class CampusNewsDetailsActivity extends CommonActivity implements
		OnKeyListener, OnClickListener , OnGlobalLayoutListener{

	private ImageView mPortrait;// 头像
	private TextView mContent;// 心情内容
	private MyGridView mNewsPhotos;// 心情照片
	private TextView mLike;// 评论图标
	private TextView mComment;// 点赞图标
	private TextView mLikePeople;// 点赞的人
	private ListView mCommentList;// 评论内容
	private RelativeLayout mLayoutInput;// 输入框，点击评论时弹出
	private ScrollView mScrollView;
	private EditText mEditText;// 输入框，默认隐藏
	private Button mChatOutput;
	InputMethodManager inputManager;// 软键盘，默认隐藏
	RelativeLayout mRelativeLayout;// 屏幕，监听屏幕被点击
	private int clickY;	//点击所在y坐标
	private int inputY;//输入框y坐标
	private int inputStatus;//输入框状态，0=隐藏，1=显示
	private int count;//初始为0，记录layout的次数，因为点击评论后，输入框弹出来会layout两次
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campus_news_details);
		setActionBarTitle("XIZI LUO");
		showBackButton();
		init();
	}

	private void init() {
		mPortrait = (ImageView) findViewById(R.id.portrait);
		mContent = (TextView) findViewById(R.id.comment);
		mNewsPhotos = (MyGridView) findViewById(R.id.campus_comment);
		mNewsPhotos.setAdapter(new PhotoAdapter(this, Constant.FLAG_CAMPUS));
		mComment = (TextView) findViewById(R.id.number_of_people_comment);
		mComment.setOnClickListener(this);
		mLike = (TextView) findViewById(R.id.number_of_people_like);
		mLikePeople = (TextView) findViewById(R.id.people_like);
		mCommentList = (ListView) findViewById(R.id.comment_list);
		mCommentList.setAdapter(new CampusNewsCommentAdapter(this));
		mLayoutInput = (RelativeLayout) findViewById(R.id.layout_chat_input);
		mScrollView = (ScrollView) findViewById(R.id.scroll);
		mEditText = (EditText) findViewById(R.id.chat_input);
		Utils.setListViewHeightBasedOnChildren(mCommentList);
		mChatOutput = (Button) findViewById(R.id.chat_output);
		mChatOutput.setOnClickListener(this);
		inputManager = (InputMethodManager) mEditText.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.layout_canpus_news_detail);
		mRelativeLayout.setOnClickListener(this);

		mRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
		//topYComment = mLikePeople.getScrollY()+mLikePeople.getHeight();
		//Log.v("height", ""+topYComment);
	}

	/**
	 * 举报
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// if(event.getAction() == KeyEvent.)
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.chat_output:
		case R.id.layout_canpus_news_detail:
			// 隐藏软键盘
			inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

			// 隐藏输入框
			mLayoutInput.setVisibility(View.GONE);
			inputStatus = 0;
			break;

		case R.id.number_of_people_comment:
			mLayoutInput.setVisibility(View.VISIBLE);
		//	inputStatus = 1;	//设置
			mEditText.requestFocus();
			inputManager.showSoftInput(mEditText, 0);
			mScrollView.fullScroll(View.FOCUS_DOWN);
		default:
			break;
		}

	}
/*
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if (mLayoutInput.getVisibility() == View.GONE) {
			mLayoutInput.setVisibility(View.VISIBLE);
		}

		if (!mEditText.isFocused()) {
			mEditText.requestFocus();
		}

		if (inputManager.isActive(mEditText)) {
			inputManager.showSoftInput(mEditText, 0);
		}

		clickY = ClickUtils.getRect(view).top+view.getHeight();//被点击该条评论的底部Y坐标l
		count = 0;	//初始化为0，忽略之前已经layout的次数
	//	inputStatus = 1;//设置输入框为显示状态
	}
*/	@Override
	public void onGlobalLayout() {
		/*
		 * count=2时，输入框最后一次layout，输入法也已经弹出来，并且此时输入框已经在输入法的上面
		 * 
		 */		
		count++;
		
		if(0 == inputY && 2 == count){	//当获取输入框坐标后(inputY>0)，以后点击不再重复获取，因为输入框弹出后的坐标是一个定值		
			inputY = InputUtils.getY(mLayoutInput);	//获取输入框弹出后的y坐标	
			mScrollView.scrollBy(0, clickY-inputY);
			inputStatus = 1;
		}else if(1 == inputStatus || 2 == count){
			Log.v("ii", inputStatus+","+count+","+clickY);
			mScrollView.scrollBy(0, clickY-inputY);
			inputStatus = 1;
		}
		
	}


}
