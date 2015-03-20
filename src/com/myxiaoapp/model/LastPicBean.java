/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

/**
 * @author JiangZhenJie
 *
 */
public class LastPicBean{
	public String p_id;
	public String p_url;
	public String s_url;
	public String p_time;
	public String p_type;
	public String user_id;
	public String messages_id;
	public String is_close;
	@Override
	public String toString() {
		return "LastPicBean [p_id=" + p_id + ", p_url=" + p_url + ", s_url="
				+ s_url + ", p_time=" + p_time + ", p_type=" + p_type
				+ ", user_id=" + user_id + ", message_id=" + messages_id
				+ ", is_close=" + is_close + "]";
	}
	
	
}
