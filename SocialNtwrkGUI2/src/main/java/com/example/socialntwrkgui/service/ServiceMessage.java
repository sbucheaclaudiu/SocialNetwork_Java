package com.example.socialntwrkgui.service;

import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.repositoryDB.RepoDB;
import com.example.socialntwrkgui.utils.events.MessageEntityChangeEvent;
import com.example.socialntwrkgui.utils.observer.Observable;
import com.example.socialntwrkgui.utils.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceMessage {
    protected final RepoDB<Long, Message> repo;

    public ServiceMessage(RepoDB<Long, Message> repo) {
        this.repo = repo;
    }

    public List<Message> getAll() {
        return this.repo.getAll();
    }

    public void addMessage(Long user1, Long user2, String msg) {
        List<Message> lst = this.repo.getAll();
        List<Long> lst_id = new ArrayList<>();
        for (Message m : lst) {
            lst_id.add(m.getID());
        }
        Long max = Collections.max(lst_id, null);
        Message message = new Message(max + 2, user1, user2, msg);
        this.repo.addEntity(message);
    }

    public List<String> getMessages(Long user1, Long user2) {
        List<String> msgs = new ArrayList<>();
        List<Message> lst = this.repo.getAll();

        for (Message m : lst) {
            if ((m.getUser1().equals(user1) && m.getUser2().equals(user2)) || (m.getUser1().equals(user2) && m.getUser2().equals(user1))) {
                msgs.add(m.getMsg());
            }
        }

        return msgs;
    }
}
