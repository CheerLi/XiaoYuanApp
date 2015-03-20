package com.myxiaoapp.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**
 * 修改个性签名
 * @author JiangZhenJie
 * @date 2014-9-28
 */
public class ModifyPersonalSignActivity extends CommonActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_personal_sign);
		setActionBarTitle("修改个性签名");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.modify_personal_sign, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			setResult(RESULT_OK);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
