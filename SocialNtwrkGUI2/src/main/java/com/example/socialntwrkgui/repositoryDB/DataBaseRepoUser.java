package com.example.socialntwrkgui.repositoryDB;

import java .sql.*;

import com.example.socialntwrkgui.domain.User;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.validators.Validator;

public class DataBaseRepoUser extends AbstractDataBaseRepo<Long, User>{

    public DataBaseRepoUser(String db_name, String user, String pass, String table_name, Validator<User> validator){
        super(db_name, user, pass, table_name, validator);
    }

    /**
     * Creeaza user ul cu setul de date dat
     */
    public User createEntity(ResultSet user_data) {
        try {
            Long id = user_data.getLong("id");
            String first_name = user_data.getString("first_name");
            String second_name = user_data.getString("second_name");
            String password  = user_data.getString("password");

            return new User(id, first_name, second_name, password);

        } catch (Exception e) {
            throw new LackException("Nu se poate forma o entitate.");
        }
    }

    /**
     * Adauga user in repo
     */
    public void addEntity(User new_user){
        Statement statement;
        try {
            String query = String.format("insert into %s(id,first_name,second_name,password) values(%s,'%s','%s','%s');",table_name,new_user.getID(),new_user.getFirstName(),new_user.getSecondName(),new_user.getPassword());
            statement = this.connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            throw new LackException("\nEroare la query!\n");
        }
    }

    /**
     * Modifica user ul daca exista
     */
    public User modifEntity(User modif_entity){
        Statement statement;
        try {
            String query=String.format("update %s set first_name='%s', second_name='%s', password='%s' where id=%s",table_name,modif_entity.getFirstName(),modif_entity.getSecondName(),modif_entity.getPassword(),modif_entity.getID());
            statement=this.connection.createStatement();
            statement.executeUpdate(query);
            return modif_entity;
        }catch (Exception e){
            throw new LackException("\nEroare la query!\n");
        }
    }

}
