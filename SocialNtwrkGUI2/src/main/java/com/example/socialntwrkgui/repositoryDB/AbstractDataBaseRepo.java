package com.example.socialntwrkgui.repositoryDB;

import com.example.socialntwrkgui.domain.Entity;
import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.validators.Validator;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public abstract class AbstractDataBaseRepo<ID, E extends Entity<ID>> implements RepoDB<ID, E>{
    Connection connection;
    protected Validator<E> validator;
    String table_name;

    public AbstractDataBaseRepo(String db_name, String user, String pass, String table_name, Validator<E> validator){
        this.validator = validator;
        this.connection = connectToDb(db_name, user, pass);
        this.table_name = table_name;
    }

    /**
     * Functia pentru conectarea la baza de date
     */
    public Connection connectToDb(String db_name, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5433/"+db_name,user,pass);
            if(conn!=null){
                System.out.println("\nConnection Established\n");
            }
            else{
                System.out.println("\nConnection Failed\n");
            }

        }catch (Exception e){
            System.out.println("\nEroare la conectarea bazei de date.\n");
        }
        return conn;

    }

    /**
     * Citeste entitatile din baza de date
     * @return lista de entitati
     */
    public List<E> readEntity(){
        List<E> lst_entitys = new ArrayList<>();
        Statement statement;
        ResultSet rs;

        try {
            String query = String.format("select * from %s", this.table_name);
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next()){
                E entity = createEntity(rs);
                lst_entitys.add(entity);
            }

            return lst_entitys;

        }
        catch (Exception e){
            throw new LackException("Nu se poate citi baza de date");
        }
    }

    /**
     * Creeaza entitatea cu setul de date dat
     */
    public abstract E createEntity(ResultSet rs) throws SQLException;

    /**
     * Returneaza toate entitatile
     */
    public List<E> getAll(){
        return readEntity();
    }

    /**
     * Cauta entitatea cu id-ul dat
     */
    public E findEntity(ID id){
        Statement statement;
        ResultSet rs;
        try {
            String query = String.format("select * from %s where id= %s",table_name,id);
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()){
                return createEntity(rs);
            }

        }catch (Exception e){
            throw new LackException("\nEroare la id!\n");
        }
        throw new LackException("\nEroare la id!\n");
    }

    /**
     * Adauga entitatea in repo
     */
    public abstract void addEntity(E new_entity);

    /**
     * Sterge entitatea cu id-ul dat
     */
    public void deleteEntity(ID id) {
        Statement statement;
        try {
            String query = String.format("delete from %s where id= %s", table_name, id);
            statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new LackException("\nEroare la stergere!\n");
        }
    }

    /**
     * Modifica entitatea daca exista
     */
    public abstract E modifEntity(E modif_entity);
}
