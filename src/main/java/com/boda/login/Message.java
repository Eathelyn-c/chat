package com.boda.login;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private String username1; // sender
    private String username2; // receiver
    private String content;
    private Date timestamp;      // 时间戳
    private boolean isBroadcast;

    public Message(String username1, String username2, String content,boolean isBroadcast) {
        this.username1 = username1;
        this.username2 = username2;
        this.content = content;
        this.isBroadcast=isBroadcast;
        this.timestamp = new Date();  // 自动设置当前时间
    }

    // Getter 方法
    public String getUsername1() { return username1; }
    public String getUsername2() { return username2; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public boolean isBroadcast() { return isBroadcast; }

    // Setter 方法
    public void setUsername1(String username1) { this.username1 = username1; }
    public void setUsername2(String username2) { this.username2 = username2; }
    public void setContent(String content) { this.content = content; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
