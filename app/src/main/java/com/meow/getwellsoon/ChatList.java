package com.meow.getwellsoon;

import java.io.Serializable;

public class ChatList implements Serializable {

    String username, myusername;

    public ChatList(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMyusername() {
        return myusername;
    }

    public void setMyusername(String myusername) {
        this.myusername = myusername;
    }
}
