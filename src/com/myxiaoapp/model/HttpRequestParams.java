package com.myxiaoapp.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.myxiaoapp.android.XiaoYuanApp;
import com.myxiaoapp.utils.Constant;
import com.myxiaoapp.utils.EncryptUtils;

/**
 * 
 * @author ken
 * 
 */
public class HttpRequestParams {

	protected static RequestParams getBaseParams() {
		RequestParams params = new RequestParams();
		params.put("iemi", XiaoYuanApp.deviceId);
		params.put("app_version", XiaoYuanApp.appVersion);
		params.put("os", XiaoYuanApp.deviceOS);
		params.put("system_version", XiaoYuanApp.deviceVersion);
		return params;
	}

	/**
	 * 登录接口参数
	 * 
	 * @return
	 */
	public static RequestParams loginParams(String username, String password) {
		RequestParams loginParams = getBaseParams();
		loginParams.put("username", username);
		loginParams.put("password", password);
		String sign = getSignByMd5(loginParams);
		loginParams.put("sign", sign);
		return loginParams;
	}

	public static RequestParams getSchoolParams() {
		RequestParams schoolParams = getBaseParams();
		return schoolParams;
	}

	/**
	 * 发生注册信息 <br/>
	 * 图片过大会出现time out exception<br/>
	 * 解决方案：<br/>
	 * 1. 限制大小<br/>
	 * 2. 图片过大则压缩<br/>
	 * 3. 另开接口上传图片<br/>
	 * 
	 * @return
	 */
	public static RequestParams registerParams() {
		RequestParams registerParams = getBaseParams();
		registerParams.put("school_code", RegisterInfo.getSchoolCode());
		registerParams.put("sex", RegisterInfo.getSex());
		registerParams.put("phone", RegisterInfo.getPhone());
		registerParams.put("username", RegisterInfo.getPhone());
		registerParams.put("password", RegisterInfo.getPassword());
		registerParams.put("nickname", RegisterInfo.getNickname());

		String sign = getSignByMd5(registerParams);
		registerParams.put("sign", sign);
		// String path = Environment.getExternalStorageDirectory() +
		// "/test.jpg";
		String path = RegisterInfo.getPicturePath();
		Log.d("mydebug", "image path ---> " + path);
		File file = new File(path);
		Log.d("mydebug", file.length() + "");
		try {
			registerParams.put("pic_count", 1);
			registerParams.put("picture0", file, "image/jpeg");
			// registerParams.put("picture1", new File(path), "jpeg");
			// registerParams.put("pic2", new File(path), "jpeg");
		} catch (FileNotFoundException e) {
			Log.i("mydebug", path);
			Log.i("mydebug", "photoNotFound");
			e.printStackTrace();
		}
		Log.i("mydebug", registerParams.toString());
		return registerParams;
	}

	/**
	 * username参数在找回密码中是手机号
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static RequestParams setPasswordParams(String username,
			String password) {
		RequestParams setPasswordParams = getBaseParams();
		setPasswordParams.add("username", username);
		setPasswordParams.add("password", password);
		String sign = getSignByMd5(setPasswordParams);
		setPasswordParams.put("sign", sign);
		return setPasswordParams;
	}

	/**
	 * 签名
	 * 
	 * @param params
	 * @return
	 */
	public static String getSignByMd5(RequestParams params) {

		return EncryptUtils.signByMd5(params.toString());
	}

	/**
	 * 验证手机接口
	 * 
	 * @param phone
	 * @return
	 */
	public static RequestParams checkPhoneParams(String phone) {

		RequestParams checkPhoneParams = getBaseParams();
		checkPhoneParams.put("phone", phone);
		String sign = getSignByMd5(checkPhoneParams);
		checkPhoneParams.put("sign", sign);
		// Log.i("mydebug", checkPhoneParams.toString());
		return checkPhoneParams;

	}

	/**
	 * 拉取学校验证码接口
	 * 
	 * @param phone
	 * @return
	 */
	public static RequestParams getVerifyParams(String phone) {
		RequestParams verifyParams = getBaseParams();
		verifyParams.put("phone", phone);
		String sign = getSignByMd5(verifyParams);
		verifyParams.put("sign", sign);
		return verifyParams;

	}

	/**
	 * 学校身份验证接口
	 * 
	 * @param studentCode
	 * @param password
	 * @param verifyCode
	 * @return
	 */
	public static RequestParams schoolVerifyParams(String studentCode,
			String password, String verifyCode) {
		RequestParams verifyParams = getBaseParams();
		verifyParams.put("student_id", studentCode);
		verifyParams.put("password", password);
		verifyParams.put("username", RegisterInfo.getPhone());
		verifyParams.put("verification_code", verifyCode);
		String sign = getSignByMd5(verifyParams);
		verifyParams.put("sign", sign);
		return verifyParams;
	}

	/**
	 * 手机短信验证接口
	 * 
	 * @param verifyCode
	 * @param phone
	 * @return
	 */
	public static RequestParams phoneVerifyParams(String verifyCode,
			String phone) {
		RequestParams phoneVerifyParams = getBaseParams();
		phoneVerifyParams.put("phone", phone);
		phoneVerifyParams.put("verify_code", verifyCode);
		String sign = getSignByMd5(phoneVerifyParams);
		phoneVerifyParams.put("sign", sign);
		return phoneVerifyParams;
	}

	/**
	 * 回传状态揭露 更新地理位置
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param latitude
	 *            维度
	 * @param longitude
	 *            经度
	 * @return
	 */
	public static RequestParams updateLocationParams(String user_id,
			String latitude, String longitude) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 用户信息拉取接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param interest_id
	 *            感兴趣用户(或自己)的id
	 * @return
	 */
	public static RequestParams getUserInfoParams(String user_id,
			String interest_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("interest_name", interest_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 关注好友接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @return
	 */
	public static RequestParams focusFriendsParams(String user_id,
			String follow_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 取消关注接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @return
	 */
	public static RequestParams cancelFocusParams(String user_id,
			String follow_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 设置好友备注接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param remark_name
	 *            备注名称
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static RequestParams remarkFriendsParams(String user_id,
			String follow_id, String remark_name)
			throws UnsupportedEncodingException {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		params.put("remark_name",
				URLEncoder.encode(remark_name, Constant.charSet));
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 设置屏蔽接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @return
	 */
	public static RequestParams shiledFriendsParams(String user_id,
			String follow_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 获取用户相册接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param page_size
	 *            照片数量，最大一次获取八张，具体取决于界面最大显示多少张
	 * @return
	 */
	public static RequestParams userAlbumParams(String user_id,
			String follow_id, int page_size) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		params.put("page_size", page_size);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 校园圈列表获取接口： 校园圈返回的数据包括单条校园圈中的图片列表，回复列表和点赞列表。
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param msg_id
	 *            (1)如果是要查看新的消息，参数是最新的消息id;(2)如果要查看之前的消息，参数是最后那条消息id
	 * @param msg_tyle
	 *            (0表示从获取新的消息，1表示获取已经之前的信息)
	 * @param page_size
	 *            一次返回的校园圈信息数量，最大20条
	 * @return
	 */
	public static RequestParams campusCircle(String user_id, String follow_id,
			String msg_id, String msg_tyle, String page_size) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		params.put("msg_id", msg_id);
		params.put("msg_tyle", msg_tyle);
		params.put("page_size", page_size);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 用户关注列表获取接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param page_size
	 *            一次返回的条数 （可选）默认返回全部
	 * @return
	 */
	public static RequestParams focusListParams(String user_id,
			String follow_id, String page_size) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		params.put("page_size", page_size);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 获取粉丝列表接口
	 * 
	 * @param user_id
	 *            用户自己的id
	 * @param follow_id
	 *            对方用户名的id
	 * @param page_size
	 *            一次返回的条数 （可选）默认返回全部
	 * @return
	 */
	public static RequestParams fansListParams(String user_id,
			String follow_id, String page_size) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("follow_id", follow_id);
		params.put("page_size", page_size);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 发布校园消息接口参数
	 * 
	 * @param user_id
	 *            用户id
	 * @param message_info
	 *            消息的内容(不能为空),要用URLencode进行编码
	 * @param pic_count
	 *            消息附带图片的数量 可选(没有的话为空)
	 * @param pics
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static RequestParams uploadMsg(String user_id, String message_info,
			int pic_count, String pics[]) throws UnsupportedEncodingException,
			FileNotFoundException {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("message_info",
				URLEncoder.encode(message_info, Constant.charSet));
		params.put("pic_count", pic_count);
		String sign = getSignByMd5(params);
		for (int i = 0; i < pic_count; i++) {
			params.put("picture" + i, new File(pics[i]), "jpeg");
		}

		params.put("sign", sign);
		return params;
	}

	/**
	 * 删除校园圈信息接口参数
	 * 
	 * @param user_id
	 *            用户id
	 * @param msg_id
	 *            信息id
	 * @return
	 */
	public static RequestParams delMsg(String user_id, String msg_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("msg_id", msg_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 评论/回复消息接口参数
	 * 
	 * @param msg_id
	 *            消息id
	 * @param comment
	 *            评论的内容 ,必须做URLencode
	 * @param user_id
	 *            评论人的id号
	 * @param commented_uid
	 *            被回复人的id号 可选 (没有的话为空 )
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static RequestParams reply(String msg_id, String comment,
			String user_id, String commented_uid)
			throws UnsupportedEncodingException {
		RequestParams params = getBaseParams();
		params.put("msg_id", msg_id);
		params.put("comment", URLEncoder.encode(comment, Constant.charSet));
		params.put("user_id", user_id);
		params.put("commented_uid", commented_uid);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 删除评论/回复接口参数
	 * 
	 * @param comment_id
	 *            该评论的id
	 * @param user_id
	 *            评论人的id号
	 * @return
	 */
	public static RequestParams delComment(String comment_id, String user_id) {
		RequestParams params = getBaseParams();
		params.put("comment_id", comment_id);
		params.put("user_id", user_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

	/**
	 * 点赞接口参数
	 * 
	 * @param user_id
	 *            用户id
	 * @param usered_id
	 *            被点赞用户id
	 * @return
	 */
	public static RequestParams addLike(String user_id, String usered_id) {
		RequestParams params = getBaseParams();
		params.put("user_id", user_id);
		params.put("usered_id", usered_id);
		String sign = getSignByMd5(params);
		params.put("sign", sign);
		return params;
	}

}
