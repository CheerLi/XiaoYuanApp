package com.myxiaoapp.android;

import com.loopj.android.http.AsyncHttpClient;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 找回密码
 * 		输入手机号码
 
 * @author liqihang
 * @time 2014-9-12
 * 
 */
public class ForgetPWDVerifyActivity extends CommonActivity implements OnClickListener{
	private Button nextbtn_reset_password;
	private EditText phonenumber_input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_phone_verify);
		setActionBarTitle(R.string.resetpwd_tip);
		showBackButton();
		setActionBarTitle(R.string.forgetpwd_actionbar_title);
		init();
		}
	private void init(){
		phonenumber_input = (EditText)findViewById(R.id.phonenumber_input);
		nextbtn_reset_password = (Button)findViewById(R.id.forgetpwd_next);
		nextbtn_reset_password.setOnClickListener(this);
	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean check(){
		String phoneNumber = phonenumber_input.getText().toString();
		if(phoneNumber.equals("")){
			Toast.makeText(ForgetPWDVerifyActivity.this, "请输入手机号码", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		if(check()){

			final String phoneNumber = phonenumber_input.getText().toString();
			Intent mIntent = new Intent(ForgetPWDVerifyActivity.this,ForgetPWDInputVerifyNumber.class);
			mIntent.putExtra("phoneNumber",phoneNumber);
			startActivity(mIntent);
			finish();
		/*	
			AsyncHttpClient client = new AsyncHttpClient();
			final HttpResponseHandler responseHandler = new HttpResponseHandler();
			final String phoneNumber = phonenumber_input.getText().toString();
			client.post("http://172.31.180.35/yaf/index.php/CheckPhoneNum", HttpRequestParams.checkPhoneParams(phoneNumber), responseHandler);
			responseHandler.setOnResponseListener(new OnResponseListener(){


				@Override
				public void onFailure() {
				}

				@Override
				public void onReceiveSuccess() {
					Intent mIntent = new Intent(ForgetPWDVerifyActivity.this,ForgetPWDInputVerifyNumber.class);
					mIntent.putExtra("phoneNumber",phoneNumber);
					startActivity(mIntent);
					finish();					
				}

				@Override
				public void onReceiveFailure() {
				}	
			});
		*/
		}
	}
}
