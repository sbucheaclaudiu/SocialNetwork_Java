package com.example.socialntwrkgui.repositoryDB;

import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.validators.Validator;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepoFriendship extends AbstractDataBaseRepo<Long, Friendship> {

    public DataBaseRepoFriendship(String db_name, String user, String pass, String table_name, Validator<Friendship> validator) {
        super(db_name, user, pass, table_name, validator);
    }

    /**
     * Creeaza friendship ul cu setul de date dat
     */
    public Friendship createEntity(ResultSet friendship_data){
        try{
            Long id = friendship_data.getLong("id");
            Long user_1 = friendship_data.getLong("user_1");
            Long user_2 = friendship_data.getLong("user_2");
            boolean pending = friendship_data.getBoolean("pending");

            String d = friendship_data.getString("data");

            List<String> attr = Arrays.asList(d.split("-"));
            int an = Integer.parseInt(attr.get(0));
            int luna = Integer.parseInt(attr.get(1));
            int zi = Integer.parseInt(attr.get(2));

            LocalDate date = LocalDate.of(an, luna, zi);

            return new Friendship(id, user_1, user_2, pending, date);
        }
        catch (Exception e){
            throw new LackException("Nu se poate forma o prietenie.");
        }
    }

    /**
     * Adauga friendship in repo
     */
    public void addEntity(Friendship new_friendship){
        Statement statement;
        try {
            if(verifFriendship(new_friendship)) {
                String query = String.format("insert into %s(id,user_1,user_2,pending,data) values(%s,'%s','%s','%s','%s');", table_name, new_friendship.getID(), new_friendship.getUser1(), new_friendship.getUser2(), new_friendship.getPending(), new_friendship.getDate());
                statement = this.connection.createStatement();
                statement.executeUpdate(query);
            }
            else{
                throw new LackException();
            }

        } catch (Exception e){
            throw new LackException("\nPrietenia exista deja in sistem.\n");
        }
    }

    /**
     * Modifica friendship ul daca exista
     */
    public Friendship modifEntity(Friendship modif_entity){
        Statement statement;
        try {
            String query=String.format("update %s set user_1='%s', user_2='%s', pending='%s', data='%s' where id=%s", table_name, modif_entity.getUser1(), modif_entity.getUser2(), modif_entity.getPending(), modif_entity.getDate(), modif_entity.getID());
            statement=this.connection.createStatement();
            statement.executeUpdate(query);
            return modif_entity;
        }catch (Exception e){
            throw new LackException("\nEroare la query!\n");
        }
    }

    /**
     * Verifica daca prietenia exista deja in memorie
     */
    public boolean verifFriendship(Friendship new_friendship){
        Statement statement;
        ResultSet rs;
        String table_friendships = "friendships";
        try {
            String query = String.format("select id from %s where user_1=%s and user_2=%s", table_friendships, new_friendship.getUser1(), new_friendship.getUser2());
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);
            return !rs.next();
        }
        catch (Exception e) {
            throw new LackException("\nEroare la query99!\n");
        }
    }

}
