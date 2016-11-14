package com.skymxc.lesson_46_shared.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sky-mxc
 */
@Table(name = "user")
public class User extends Model {
    private static final String TAG = "User";

    @Column(name = "account")
    private String account;
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "photo")
    private String photo;
    @Column(name = "nick")
    private String nick;
    @Column(name = "graden")
    private String graden;
    @Column(name = "token")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setGraden(String graden) {
        this.graden = graden;
    }

    public static String getTAG() {

        return TAG;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNick() {
        return nick;
    }

    public String getGraden() {
        return graden;
    }
}
