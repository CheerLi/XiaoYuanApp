package com.myxiaoapp.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 发布文字
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class PublishTextActivity extends CommonActivity {

	private EditText mPublishText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_text);
		setActionBarTitle(R.string.title_activity_publish_text);
		mPublishText = (EditText) findViewById(R.id.publish_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.publish_text, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_send:

			break;

		default:
			break;
		}
		return true;
	}

}
