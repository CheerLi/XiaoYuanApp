/**
 * 2014年11月5日
 * ken
 */
package com.myxiaoapp.android;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.myxiaoapp.listener.OnResponseListener;
import com.myxiaoapp.model.HttpRequestParams;
import com.myxiaoapp.model.HttpResponseHandler;
import com.myxiaoapp.model.RegisterInfo;
import com.myxiaoapp.network.SingleAsyncClient;

/**
 * @author ken
 * 
 */
public class RegisterInputVerifyActivity extends CommonActivity implements
		OnClickListener {
	private ActionBar mActionBar;
	private TextView phone_number;
	private EditText verify_code;
	private Button mButton;
	private String code;
	private String errno;
	private final String url = "http://120.24.76.148/yaf/index.php/Getshortmsg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_inputverify);
		showBackButton();
		setActionBarTitle(R.string.title_activity_register_inputverify);
		init();
		getVerifyCode();
	}
	private void getVerifyCode(){
		/*
		 *手机账号验证接口，后台还没做 
		 */
		String phone = getIntent().getStringExtra("phone");
		AsyncHttpClient client = SingleAsyncClient.getSingleClient();
		final HttpResponseHandler responseHandler = new HttpResponseHandler();
		responseHandler.setOnResponseListener(new OnResponseListener(){

			@Override
			public void onFailure() {
			}

			@Override
			public void onReceive() {
				errno = responseHandler.getErrno(); 
				if(errno.equals("20")){
					try { 
						JSONObject jo = new JSONObject(new String(responseHandler.getResponseBody(),"UTF-8"));
						Log.d("mydebug1", jo.toString());
						code = responseHandler.getValue(jo, "code");
					} catch (JSONException e) {
						
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					}
				}
			}
			
		});
		client.post(url, HttpRequestParams.getVerifyParams(phone),responseHandler);
	}
	private void init() {
		mActionBar = getSupportActionBar();
		phone_number = (TextView) findViewById(R.id.phone_number);
		phone_number.setText(RegisterInfo.getPhone());
		verify_code = (EditText)findViewById(R.id.verify_number); 
		mButton = (Button) findViewById(R.id.resetpwd_button);
		mButton.setOnClickListener(this);

	}

	private boolean checkCode() {
		if(code == null) return false;
		
		if(code.equals(verify_code.getText().toString()))
			return true;
		else if(code.equals("45")){
			Toast.makeText(RegisterInputVerifyActivity.this, "运营商发送短信失败", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if (checkCode()) {
			startActivity(new Intent(RegisterInputVerifyActivity.this, RegisterSchoolActivity.class));
			finish();
		}
	}

}
