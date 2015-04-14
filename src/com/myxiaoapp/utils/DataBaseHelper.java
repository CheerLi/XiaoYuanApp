/**
 * 2015年4月11日
 * ken
 */
package com.myxiaoapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
 


import android.util.Log;

import com.activeandroid.query.Select;
import com.myxiaoapp.model.Chat;
import com.myxiaoapp.model.ChatItem;

/**
 * @author ken
 *
 */
public class DataBaseHelper {
	private final static String TAG = "DataBaseHelper";
    public static void saveChat(final JSONObject json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Chat chat = new Chat();
                    chat.fromUserId = json.getString("fromUserId");
                    chat.fromName = json.getString("fromName");
                    chat.toUserId = json.getString("toUserId");
                    chat.toName = json.getString("toName");
                    chat.message = json.getString("message");
                    chat.time = System.currentTimeMillis() + "";
                    chat.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void saveChat(ChatItem item) {
        Chat chat = new Chat();
        chat.message = item.getMessage();
        chat.fromUserId = item.getFromUserId();
        chat.fromName = item.getFromUserName();
        chat.toUserId = item.getToUserId();
        chat.toName = item.getToUserName();
        chat.time = System.currentTimeMillis() + "";
        chat.save();
    }

    public static List<ChatItem> readChat(String otherUserId) {
        List<ChatItem> chatItems = new ArrayList<>();
        int loginId = DataManager.getInstance().getLoginUserId();
        int otherId = Integer.valueOf(otherUserId);
        Log.d(TAG, "loginId="+loginId+",otherId="+otherId);
        List<Chat> chats = new Select().
                from(Chat.class).
                where("(fromUserId = ? and toUserID = ? ) or (fromUserId = ? and toUserId = ? )", 
                		loginId, otherId, otherId, loginId)
                .execute();
      
        for (Chat chat : chats) {
            chatItems.add(ChatItem.chat2ChatItem(chat));
        }
        Collections.sort(chatItems, new Comparator<ChatItem>() {
            @Override
            public int compare(ChatItem lhs, ChatItem rhs) {
                return (int) (lhs.getTime() - rhs.getTime());
            }
        });

        return chatItems;
    }
}	
