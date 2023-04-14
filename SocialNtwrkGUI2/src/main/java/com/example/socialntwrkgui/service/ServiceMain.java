package com.example.socialntwrkgui.service;

import com.example.socialntwrkgui.controller.MessageController;
import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceMain {
    public ServiceUser service_user;
    public ServiceFriendship service_friendship;

    public ServiceMessage service_message;

    Long id_utilizator;

    public ServiceMain(ServiceUser user_srv, ServiceFriendship friendship_srv, ServiceMessage message_srv){
        this.service_user = user_srv;
        this.service_friendship = friendship_srv;
        this.service_message = message_srv;
    }

    public void setIdUtilizator(Long id){
        this.id_utilizator = id;
    }

    public Long getId_utilizator(){
        return this.id_utilizator;
    }

    /**
     Returneaza lista de useri care au in alcatuire si lista de prieteni a fiecaruia
     */
    public List<User> putFriendsInUser() throws Exception {
        List<User> lst_users = this.service_user.getAll();
        List<Friendship> lst_friendships = this.service_friendship.getAll();

        for(User u : lst_users){
            for(Friendship f : lst_friendships){
                if(Objects.equals(f.getUser1(), u.getID())){
                    u.addFriend(this.service_user.getUser(f.getUser2()));
                }
                else if(Objects.equals(f.getUser2(), u.getID())){
                    u.addFriend(this.service_user.getUser(f.getUser1()));
                }
            }
        }

        return lst_users;
    }

    public void addUser(Long id, String first_name, String second_name, String password){
        service_user.addUser(id, first_name, second_name, password);
    }

    public void deleteUser(Long id){
        this.service_friendship.delete_user(id);
        this.service_user.deleteUser(id);
    }

    public void modifUser(Long id, String new_first_name, String new_second_name, String password){
        service_user.modifUser(id, new_first_name, new_second_name, password);
    }

    public List<User> showUsers() throws Exception {
        return this.putFriendsInUser();
    }

    public void addFriendship(Long id1, Long id2){
        this.service_friendship.addFriends(id1, id2);
    }

    public void deleteFriendship(Long id1, Long id2) throws Exception {
        this.service_friendship.deleteFriends(id1, id2);
    }

    public void modifFriendship(Long id, String date) throws Exception {
        this.service_friendship.modifFriendship(id, date);
    }

    public List<User> getAllUsers(){
        return this.service_user.getAll();
    }

    public List<Friendship> showFriendships(){
        return this.service_friendship.getAll();
    }

    public List<User> friendsList(Long id) throws Exception {
        List<Friendship> lst_friendship = this.service_friendship.getAll();
        List<User> lst_users = new ArrayList<>();
        for(Friendship f : lst_friendship){
            if(f.getUser1().equals(id) && f.getPending()){
                User u = this.service_user.repo.findEntity(f.getUser2());
                u.setDate(f.getDate());
                lst_users.add(u);
            }
            if(f.getUser2().equals(id) && f.getPending()){
                User u = this.service_user.repo.findEntity(f.getUser1());
                u.setDate(f.getDate());
                lst_users.add(u);
            }
        }
        return lst_users;

    }

    public List<String> getMessages(Long user1, Long user2){
        return this.service_message.getMessages(user1, user2);
    }

    public List<User> cereriTrimiseList(Long id) throws Exception {
        List<Friendship> lst_friendship = this.service_friendship.getAll();
        List<User> lst_users = new ArrayList<>();
        for(Friendship f : lst_friendship){
            if(f.getUser1().equals(id) && !f.getPending()){
                User u = this.service_user.repo.findEntity(f.getUser2());
                u.setDate(f.getDate());
                lst_users.add(u);
            }
        }
        return lst_users;

    }

    public List<User> cereriPrimiteList(Long id) throws Exception {
        List<Friendship> lst_friendship = this.service_friendship.getAll();
        List<User> lst_users = new ArrayList<>();
        for(Friendship f : lst_friendship){
            if(f.getUser2().equals(id) && !f.getPending()){
                User u = this.service_user.repo.findEntity(f.getUser1());
                u.setDate(f.getDate());
                lst_users.add(u);
            }
        }
        return lst_users;

    }

    public List<User> sugestiiList(Long id) throws Exception {
        List<Friendship> lst_friendship = this.service_friendship.getAll();
        List<User> lst_users = new ArrayList<>();
        for(Friendship f : lst_friendship){
            if(f.getUser2().equals(id)){
                User u = this.service_user.repo.findEntity(f.getUser1());
                LocalDate date = LocalDate.now();
                u.setDate(date);
                lst_users.add(u);
            }
            if(f.getUser1().equals(id)){
                User u = this.service_user.repo.findEntity(f.getUser2());
                LocalDate date = LocalDate.now();
                u.setDate(date);
                lst_users.add(u);
            }
        }
        List<User> lst_all = this.service_user.getAll();
        List<User> lst = new ArrayList<>();
        User u1 = this.service_user.repo.findEntity(id);
        lst_all.remove(u1);

        for(User u : lst_all){
            if(lst_users.contains(u)){
                int ok;
            }
            else {
                LocalDate date = LocalDate.now();
                u.setDate(date);
                lst.add(u);
            }
        }

        return lst;
    }

    public void acceptaFriendship(Long id1, Long id2) throws Exception {
        List<Friendship> lst_friendship = this.service_friendship.getAll();
        Friendship friendship = null;
        for(Friendship f : lst_friendship){
            if(f.getUser1().equals(id2) && f.getUser2().equals(id1))
                friendship = f;
        }
        this.service_friendship.deleteFriends(friendship.getUser1(), friendship.getUser2());
        this.service_friendship.addFriendship(friendship.getID(), friendship.getUser1(), friendship.getUser2());
    }

    public List<Message> getAllMessages(){
        return this.service_message.getAll();
    }

    public void addMessage(Long user1, Long user2, String msg){
        this.service_message.addMessage(user1, user2, msg);
    }

    public int verifyName(String fname, String sname){
        List<User> lst_u = this.service_user.getAll();

        for(User u : lst_u){
            if(u.getFirstName().equals(fname) && u.getSecondName().equals(sname))
                return 1;
        }
        return 0;
     }

    public int communitiesNumber() throws Exception {
        List<User> lst_users = this.putFriendsInUser();
        return service_user.communitiesNumber(lst_users);
    }

    public List<User> mostSocialCom() throws Exception {
        List<User> lst_users = this.putFriendsInUser();
        return this.service_user.mostSocialCom(lst_users);
    }

    public List<List<User>> showComunities() throws Exception {
        List<User> lst_user = this.putFriendsInUser();
        return this.service_user.show_comunities(lst_user);
    }
}
