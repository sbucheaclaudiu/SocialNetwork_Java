package com.example.socialntwrkgui.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Entity <ID> implements Serializable{

    @Serial
    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    public Entity(ID id){
        if(id==null){
            this.id =  (ID) UUID.randomUUID().toString();
        }
        else{
            this.id=id;
        }
    }

    public ID getID(){
        return this.id;
    }

    public void setID(ID new_id){
        this.id = new_id;
    }

    public String toString() {
        return "Entity: " + "id = " + id;
    }
}
