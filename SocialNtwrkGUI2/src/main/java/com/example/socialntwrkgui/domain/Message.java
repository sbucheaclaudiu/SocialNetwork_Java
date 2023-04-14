package com.example.socialntwrkgui.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Message extends Entity<Long>{
    private Long user1;
    private Long user2;
    private String msg;

    public Message(Long id, Long user1, Long user2){
        super(id);
        this.user1 = user1;
        this.user2 = user2;
    }

        public Message(Long id, Long user1, Long user2, String msg){
        super(id);
        this.user1 = user1;
        this.user2 = user2;
        this.msg = msg;
    }

    //Functii de getter
    public Long getUser1() {
        return user1;
    }

    public Long getUser2() {
        return user2;
    }

    public String getMsg() {
        return msg;
    }

    //Functii de setter
    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString(){
        return "Message: user1 - " + getUser1() + " user2 - " + getUser2() + " messages - " + getMsg();
    }
}
