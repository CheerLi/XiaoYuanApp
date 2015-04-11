/**
 * 2015年4月8日
 * ken
 */
package com.myxiaoapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author ken
 *
 */
public class LastMoment implements Serializable{
	private String m_id;
	private String m_type;
	private String m_info;
	private List<String> m_pictures;
	private List<String> m_spictures;
	private String m_time;
	private String m_collectnum;
	private String m_commentnum;
	private String m_transpondnum;
	private String m_likenum;
	private String m_readnum;
	private String m_label;
	private String user_id;
	private String is_del;
	private String is_close;
}
