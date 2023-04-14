package com.example.socialntwrkgui.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Friendship extends Entity<Long>{
    private Long user1;
    private Long user2;
    LocalDate date;

    private boolean pending = true;

    public static final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Friendship(Long id, Long user1, Long user2, boolean pending){
        super(id);
        this.user1 = user1;
        this.user2 = user2;
        this.date = LocalDate.now();
        this.pending = pending;
    }

    public Friendship(Long id, Long user1, Long user2, LocalDate date){
        super(id);
        //this.setID(id);
        this.user1 = user1;
        this.user2 = user2;
        this.date = date;
    }

    public Friendship(Long id, Long user1, Long user2, boolean pending, LocalDate date){
        super(id);
        //this.setID(id);
        this.user1 = user1;
        this.user2 = user2;
        this.date = date;
        this.pending = pending;
    }

    public Friendship(Long id, Long user1, Long user2){
        super(id);
        //this.setID(id);
        this.user1 = user1;
        this.user2 = user2;
        this.date = LocalDate.now();
    }

    //Functii de getter
    public Long getUser1() {
        return this.user1;
    }
    public Long getUser2() {
        return this.user2;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public boolean getPending(){ return this.pending; }

    //Functii de settter
    public void setUser1(Long user1) {
        this.user1 = user1;
    }
    public void setUser2(Long user2) {
        this.user2 = user2;
    }
    public void setDate(LocalDate date) { this.date = date;}
    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    //TO STRING
    public String toString(){
        return "Friendship: id: " + getID() + "|user1 id: " + getUser1() + "|user2 id: " + getUser2() + "|data: " + getDate();
    }
}
