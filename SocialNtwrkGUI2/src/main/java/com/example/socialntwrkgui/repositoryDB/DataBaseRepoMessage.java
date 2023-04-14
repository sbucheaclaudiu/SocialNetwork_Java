package com.example.socialntwrkgui.repositoryDB;

import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.service.ServiceUser;
import com.example.socialntwrkgui.validators.Validator;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataBaseRepoMessage extends AbstractDataBaseRepo<Long, Message>{
    public DataBaseRepoMessage(String db_name, String user, String pass, String table_name, Validator<Message> validator) {
        super(db_name, user, pass, table_name, validator);
    }

    @Override
    public Message createEntity(ResultSet message_data) throws SQLException {
        try{
            Long id = message_data.getLong("id");
            Long user1 = message_data.getLong("user1");
            Long user2 = message_data.getLong("user2");

            String msg = message_data.getString("msgs");

            return new Message(id, user1, user2, msg);
        }
        catch (Exception e){
            throw new LackException("Nu se poate forma un mesaj.");
        }
    }

    @Override
    public void addEntity(Message new_entity) {
        Statement statement;
        try {
            String query = String.format("insert into %s(id,user1,user2,msgs) values(%s,'%s','%s','%s');",table_name,new_entity.getID(),new_entity.getUser1(),new_entity.getUser2(),new_entity.getMsg());
            statement = this.connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            throw new LackException("\nEroare la query!\n");
        }
    }

    @Override
    public Message modifEntity(Message modif_entity) {
        return null;
    }
}
