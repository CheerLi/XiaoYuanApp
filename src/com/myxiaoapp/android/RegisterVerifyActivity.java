package com.myxiaoapp.android;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.network.SingleAsyncClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 获取手机身份验证
 * 		输入手机号
 * 		获取手机验证码（60秒没有收到验证码,获取失败，可重新获取）
 * 		
 * 进入下一步
 */
public class RegisterVerifyActivity extends CommonActivity implements OnClickListener{
	
	private Button registerNext;
	private EditText phoneNumber;
	private final String url = "http://120.24.76.148/yaf/index.php/Checkphonenum";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register_phone_verify);
		setActionBarTitle(R.string.title_activity_register_verify);
		showBackButton();
		init();
	}
	private void init(){
		mApp.addLaunchActivity(this);
		phoneNumber = (EditText)findViewById(R.id.phonenumber_input);
		phoneNumber.setText("10086");
		registerNext = (Button)findViewById(R.id.register_next);
		registerNext.setOnClickListener(this);
	
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
	
	//获取验证码
	private boolean check(String phone){ 
		if(phone.equals("")){
			Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	} 
	@Override
	public void onClick(View v) {

		final String phone = phoneNumber.getText().toString();
		if(check(phone)){
			AsyncHttpClient client = SingleAsyncClient.getSingleClient();
			final HttpResponseHandler responseHandler = new HttpResponseHandler(); 
			responseHandler.setOnResponseListener(new OnResponseListener(){

				@Override
				public void onFailure() {
				}

				@Override
				public void onReceive() {
					String errno = responseHandler.getErrno();
					if(errno.equals("20")){
						RegisterInfo.setPhone(phone);
						Intent intent = new Intent(RegisterVerifyActivity.this,RegisterInputVerifyActivity.class);
						intent.putExtra("phone", phone);
						startActivity(intent);
						finish();
					}else if(errno.equals("42")){
						Toast.makeText(RegisterVerifyActivity.this, "手机已注册", Toast.LENGTH_SHORT).show();
					}else if(errno.equals("47")){
						Toast.makeText(RegisterVerifyActivity.this, "手机的格式错误", Toast.LENGTH_SHORT).show();
					}
				}
				
			});
			client.post(url, HttpRequestParams.checkPhoneParams(phone), responseHandler);
			
		}
	} 

}
