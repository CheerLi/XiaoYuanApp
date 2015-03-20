/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.util.List;

/**
 * 获取粉丝和关注者共同使用该Bean
 * 
 * @author JiangZhenJie
 * 
 */
public class FocusFansBean {
	public String uid;
	public String sid;
	public String name;
	public String username;
	public String password;
	public String cellphone;
	public String college;
	public String portrait;
	public String s_portrait;
	public String sex;
	public String moto;
	public String latitude;
	public String longitude;
	public String validate;
	public String register_time;
	public String is_close;
	public String relation_id;
	public String relation_time;
	public String relation_type;
	// String relation_groupname;
	public String remark;
	public String user_id;
	public String user_byid;
	public String is_shielding;
	public List<String> last_pic_list;
	public List<String> last_moments;

	@Override
	public String toString() {
		return "FocusFansBean [uid=" + uid + ", sid=" + sid + ", name=" + name
				+ ", username=" + username + ", password=" + password
				+ ", cellphone=" + cellphone + ", college=" + college
				+ ", portrait=" + portrait + ", s_portrait=" + s_portrait
				+ ", sex=" + sex + ", moto=" + moto + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", validate=" + validate
				+ ", register_time=" + register_time + ", is_close=" + is_close
				+ ", relation_id=" + relation_id + ", relation_time="
				+ relation_time + ", relation_type=" + relation_type
				+ ", remark=" + remark + ", user_id=" + user_id
				+ ", user_byid=" + user_byid + ", is_shielding=" + is_shielding
				+ ", last_pic_list=" + last_pic_list + ", last_moments="
				+ last_moments + "]";
	}
}
