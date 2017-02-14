package com.im1020.modle.bean;

/**
 * Created by Administrator on 2017/2/14.
 */

public class UserInfo {

    private String username;
    private String hxid;
    private String photo;
    private String nick;


    public UserInfo() {
    }

    public UserInfo(String username) {
        this.username = username;
        this.hxid = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
