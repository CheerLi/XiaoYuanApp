package com.myxiaoapp.chathelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatMessage {
	private String message;
	private Long time;

	/**
	 * from chat user id(baidu)
	 */
	private String fromWho;

	/**
	 * to chat user id(baidu)
	 */
	private String toWho;
	/**
	 * from user id
	 */
	private String fromUser;
	/**
	 * to user id
	 */
	private String toUser;

	private String toUserName;

	private String fromUserName;

	private int isReaded;

	public ChatMessage() {

	}

	public ChatMessage(String message, Long time, String fromWho, String toWho,
			String fromUser, String toUser) {
		super();
		this.message = message;
		this.time = time;
		this.fromWho = fromWho;
		this.toWho = toWho;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", time=" + time
				+ ", fromWho=" + fromWho + ", toWho=" + toWho + ", fromUser="
				+ fromUser + ", toUser=" + toUser + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * 返回发送方的百度账号
	 * 
	 * @return
	 */
	public String getFromWho() {
		return fromWho;
	}

	/**
	 * 设置发送方的百度账号
	 * 
	 * @param fromWho
	 */
	public void setFromWho(String fromWho) {
		this.fromWho = fromWho;
	}

	/**
	 * 返回接收方的百度账号
	 * 
	 * @return
	 */
	public String getToWho() {
		return toWho;
	}

	/**
	 * 设置接收方的百度账号
	 * 
	 * @param toWho
	 */
	public void setToWho(String toWho) {
		this.toWho = toWho;
	}

	/**
	 * 返回发送方的本地账号
	 * 
	 * @return
	 */
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * 设置发送方的本地账号
	 * 
	 * @param fromUser
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * 返回接收方的本地账号
	 * 
	 * @return
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * 设置接收方的本地账号
	 * 
	 * @param toUser
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * 返回接收方的用户名称
	 * 
	 * @return
	 */
	public String getToUserName() {
		return toUserName;
	}

	/**
	 * 设置接收方的用户名称
	 * 
	 * @param toUserName
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public int getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}

	/**
	 * 根据ChatMessage对象生成json字符串
	 * 
	 * @param msg
	 * @return
	 */
	public static String createMsgJson(final ChatMessage msg) {
		String ret = null;
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("message", msg.getMessage());
			jsonObj.put("time", msg.getTime());
			jsonObj.put("toWho", msg.getToWho());
			jsonObj.put("fromUser", msg.getFromUser());
			jsonObj.put("toUser", msg.getToUser());
			jsonObj.put("toUserName", msg.getToUserName());
			jsonObj.put("fromUserName", msg.getFromUserName());
			jsonObj.put("fromWho", msg.getFromWho());
			ret = jsonObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 根据json字符串生成ChatMessage对象
	 * 
	 * @param msg
	 * @return
	 */
	public static ChatMessage getChatMessage(String msg) {
		ChatMessage chatMessage = new ChatMessage();
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(msg);
			String message = jsonObj.getString("message");
			Long time = jsonObj.getLong("time");
			String toWho = jsonObj.getString("toWho");
			String fromWho = jsonObj.getString("fromWho");
			String fromUser = jsonObj.getString("fromUser");
			String toUser = jsonObj.getString("toUser");
			String toUserName = jsonObj.getString("toUserName");
			String fromUserName = jsonObj.getString("fromUserName");
			chatMessage.setMessage(message);
			chatMessage.setTime(time);
			chatMessage.setToWho(toWho);
			chatMessage.setFromWho(fromWho);
			chatMessage.setFromUser(fromUser);
			chatMessage.setToUser(toUser);
			chatMessage.setToUserName(toUserName);
			chatMessage.setFromUserName(fromUserName);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return chatMessage;
	}
}
