package com.myxiaoapp.model;

public class RecentChatItem {
	Long timestamp;
	String userId;
	String chatUserId;
	String chatChannelId;
	String chatUserName;
	String recentMessage;
	int unReadedCount;
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChatUserId() {
		return chatUserId;
	}
	public void setChatUserId(String chatUserId) {
		this.chatUserId = chatUserId;
	}
	public String getChatChannelId() {
		return chatChannelId;
	}
	public void setChatChannelId(String chatChannelId) {
		this.chatChannelId = chatChannelId;
	}
	public String getChatUserName() {
		return chatUserName;
	}
	public void setChatUserName(String chatUserName) {
		this.chatUserName = chatUserName;
	}
	public String getRecentMessage() {
		return recentMessage;
	}
	public void setRecentMessage(String recentMessage) {
		this.recentMessage = recentMessage;
	}
	public int getUnReadedCount() {
		return unReadedCount;
	}
	public void setUnReadedCount(int unReadedCount) {
		this.unReadedCount = unReadedCount;
	}
	
	
}
