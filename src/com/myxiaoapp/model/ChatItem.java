package com.myxiaoapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.myxiaoapp.android.R;
import com.myxiaoapp.utils.DataManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ChatItem {
	private String fromUserId;
    private String fromUserName;
    private String toUserId;
    private String toUserName;
    private long time;
    private String message;
    private Bitmap mPortrait;
    private Bitmap hPortrait;
    private boolean isMeChat;

    public String getFromUserId() {
        return fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsMeChat() {
        return isMeChat;
    }

    public void setIsMeChat(boolean b) {
        isMeChat = b;
    }
 
 

	/**
	 * @return the mPortrait
	 */
	public Bitmap getmPortrait() {
		return mPortrait;
	}

	/**
	 * @param mPortrait the mPortrait to set
	 */
	public void setmPortrait(Bitmap mPortrait) {
		this.mPortrait = mPortrait;
	}

	/**
	 * @return the hPortrait
	 */
	public Bitmap gethPortrait() {
		return hPortrait;
	}

	/**
	 * @param hPortrait the hPortrait to set
	 */
	public void sethPortrait(Bitmap hPortrait) {
		this.hPortrait = hPortrait;
	}

	/**
	 * @param isMeChat the isMeChat to set
	 */
	public void setMeChat(boolean isMeChat) {
		this.isMeChat = isMeChat;
	}

	public static ChatItem chat2ChatItem(Chat chat) {
        ChatItem chatItem = new ChatItem();
        chatItem.fromUserId = chat.fromUserId;
        chatItem.fromUserName = chat.fromName;
        chatItem.toUserId = chat.toUserId;
        chatItem.toUserName = chat.toName;
        chatItem.message = chat.message;
        chatItem.time = Long.parseLong(chat.time);
     //   chatItem.mPortrait = chat.
        chatItem.isMeChat = chatItem.fromUserId.equals(DataManager.getInstance().getLoginUserId() + "");
        // TODO portrait
        return chatItem;
    }

    public static ChatItem json2ChatItem(String jsonStr) {
        ChatItem item = new ChatItem();
        try {
            JSONObject json = new JSONObject(jsonStr);
            item.fromUserId = json.getString("fromUserId");
            item.fromUserName = json.getString("fromName");
            item.toUserId = json.getString("toUserId");
            item.toUserName = json.getString("toUserName");
            item.message = json.getString("message");
            item.time = System.currentTimeMillis();
            item.setIsMeChat(false); 
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
