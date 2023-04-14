package com.example.socialntwrkgui.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
    Clasa pentru a tine informatiile utilizatorilor
 */

public class User extends Entity<Long>{
    private String first_name;
    private String second_name;
    private List<User> friends;
    private String password;
    LocalDate date;

    public User(Long id, String first_name, String second_name, String password){
        super(id);
        //this.setID(id);
        this.first_name = first_name;
        this.second_name = second_name;
        this.password = password;
        this.friends = new ArrayList<>();
    }

    //Functii de getter
    public String getFirstName(){
        return this.first_name;
    }
    public String getFirst_name(){
        return this.first_name;
    }
    public String getSecond_name(){
        return this.second_name;
    }
    public String getSecondName(){
        return this.second_name;
    }
    public List<User> getFriends(){
        return this.friends;
    }
    public String getPassword() {return this.password; }
    public LocalDate getDate() {return this.date;}

    //Functii de setter
    public void setFirstName(String new_first_name){
        this.first_name = new_first_name;
    }

    public void setSecondName(String new_second_name) {
        this.second_name = new_second_name;
    }

    public void setPassword(String new_password) { this.password = new_password; }

    public void setDate(LocalDate date) { this.date = date; }

    //Adaugare si stergere prieten
    public void addFriend(User user){
        this.friends.add(user);
    }
    public void deleteFriend(User user){
        this.friends.remove(user);
    }

    //TO STRING
    public String toString(){
        StringBuilder friendsStr = new StringBuilder();

        friendsStr.append("[");
        int ok = 1;
        for (User u : this.friends) {
            if(ok == 1){
                ok = 0;
            }
            else friendsStr.append(" | ");
            friendsStr.append(u.getID()).append(" ").append(u.getFirstName()).append(" ").append(u.getSecondName());
        }
        friendsStr.append("]");

        return "User: " + "id - " + getID() + " | " + " first name - " + getFirstName() + " | " + " second name - " + getSecondName() +
                "\n" + "friends - " + friendsStr + "." + "date - " + getDate();
    }

    //definirea cand 2 users sunt egali
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return first_name.equals(user.first_name) && second_name.equals(user.second_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, second_name);
    }
}
