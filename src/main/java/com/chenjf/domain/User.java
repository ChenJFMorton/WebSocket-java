package com.chenjf.domain;

import java.io.Serializable;

/**
 * Created by chenjf on 2017-09-10.
 */
public class User implements Serializable {

    private String nick;
    private String mobile;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
