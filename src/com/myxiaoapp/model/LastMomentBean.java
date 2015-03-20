/**
 * 2014-11-15
 * JiangZhenJie
 */
package com.myxiaoapp.model;

import java.util.List;

/**
 * @author JiangZhenJie
 * 
 */
public class LastMomentBean{
	public String m_id;
	public String m_type;
	public String m_info;
	public List<String> m_pictures;
	public List<String> m_spictures;
	public String m_time;
	public String m_collectnum;
	public String m_commentnum;
//	public String m_transpondnum;
	public String m_likesnum;
	public String m_readnum;
	public String m_label;
	public String user_id;
	public String is_del;
	public String is_close;
	@Override
	public String toString() {
		return "MomentBean [m_id=" + m_id + ", m_type=" + m_type + ", m_info="
				+ m_info + ", m_pictures=" + m_pictures + ", m_spictures="
				+ m_spictures + ", m_time=" + m_time + ", m_collectnum="
				+ m_collectnum + ", m_commentnum=" + m_commentnum
				+ ", m_likesnum=" + m_likesnum + ", m_readnum=" + m_readnum
				+ ", m_label=" + m_label + ", user_id=" + user_id + ", is_del="
				+ is_del + ", is_close=" + is_close + "]";
	}

}
