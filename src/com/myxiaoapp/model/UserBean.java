package com.myxiaoapp.model;

/**
 * 
 * @author JiangZhenJie
 * 
 */
public class UserBean extends BaseModel{
	public String cellphone;
	public String college;
	public String is_close;
	public String lat;
	public String lng;
	public String moto;
	public String name; 
	public String portrait;
	public String register_time;
	public String s_portrait;
	public String sex;
	public String sid;
	public String uid;
	public String username;
	public String validate;
	@Override
	public String toString() {
		return "UserBean [uid=" + uid + ", sid=" + sid + ", name=" + name
				+ ", username=" + username 
				+ ", cellphone=" + cellphone + ", college=" + college
				+ ", portrait=" + portrait + ", s_portrait=" + s_portrait
				+ ", sex=" + sex + ", moto=" + moto + ", latitude=" + lat
				+ ", longitude=" + lng + ", validate=" + validate
				+ ", register_time=" + register_time + ", is_close=" + is_close
				+ "]";
	}
}
