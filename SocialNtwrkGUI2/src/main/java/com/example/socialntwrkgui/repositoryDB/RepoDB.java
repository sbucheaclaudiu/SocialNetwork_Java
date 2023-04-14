package com.example.socialntwrkgui.repositoryDB;

import com.example.socialntwrkgui.domain.Entity;
import com.example.socialntwrkgui.domain.Message;

import java.util.List;

public interface RepoDB<ID, E extends Entity<ID>> {

    /**
     * Cauta entitatea cu id-ul dat
     */
    E findEntity(ID id) throws Exception;

    /**
     * Returneaza toate entitatile
     */
    List<E> getAll();

    /**
     * Citeste entitatile din baza de date
     * @return lista de entitati
     */
    List<E> readEntity();

    /**
     * Adauga entitatea in repo
     */
    void addEntity(E new_entity);

    /**
     * Sterge entitatea cu id-ul dat
     */
    default void deleteEntity(ID id) {
    }

    /**
     * Modifica entitatea daca exista
     */
    E modifEntity(E modif_entity);
}