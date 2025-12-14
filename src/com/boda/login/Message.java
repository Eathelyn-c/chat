package com.boda.login;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private String username1; // 发送者
    private String username2; // 接收者
    private String content;
    private Date timestamp;

    public Message(String username1, String username2, String content) {
        this.username1 = username1;
        this.username2 = username2;
        this.content = content;
        this.timestamp = new Date();
    }

    public String getUsername1() { return username1; }
    public String getUsername2() { return username2; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }

    public void setUsername1(String username1) { this.username1 = username1; }
    public void setUsername2(String username2) { this.username2 = username2; }
    public void setContent(String content) { this.content = content; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
