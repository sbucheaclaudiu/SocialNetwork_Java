package com.example.socialntwrkgui.service;

import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.repositoryDB.RepoDB;

import java.time.LocalDate;
import java.util.*;

public class ServiceFriendship {
    protected final RepoDB<Long, Friendship> repo;

    public ServiceFriendship(RepoDB<Long, Friendship> repo){
        this.repo = repo;
    }

    /**
     * Returneaza lista de prietenii din sistem(repo)
     */
    public List<Friendship> getAll(){
        return this.repo.getAll();
    }


    /**
     * Adauga un friendship in repo intre 2 useri dandu-se id-ul lor
     */
    public void addFriends(Long id1, Long id2){
        List<Friendship> lst = this.repo.getAll();
        List<Long> lst_id = new ArrayList<>();
        for(Friendship m : lst){
            lst_id.add(m.getID());
        }
        Long max = Collections.max(lst_id, null);

        Friendship friendship = new Friendship(max + 2, id1, id2, false);
        this.repo.addEntity(friendship);
    }

    public void addFriendship(Long id, Long id1, Long id2){
        Friendship friendship = new Friendship(id, id1, id2, true);
        this.repo.addEntity(friendship);
    }

    /**
     * Sterge un friendship din repo intre 2 useri dandu-se id-ul lor
     */
    public void deleteFriends(Long id1, Long id2) throws Exception {
        List<Friendship> lst_friendships = this.repo.getAll();
        Long id = null;
        for(Friendship f : lst_friendships){
            if((Objects.equals(f.getUser1(), id1) && Objects.equals(f.getUser2(), id2)) || (Objects.equals(f.getUser1(), id2) && Objects.equals(f.getUser2(), id1))){
                id = f.getID();
            }
        }

        Friendship f = this.repo.findEntity(id);
        this.repo.deleteEntity(f.getID());
    }

    /**
     Sterge fiecare friendship in care apare id-ul persoanei date
     (ajuta la stergerea unui user, se sterg toate prieteniile lui)
     */
    public void delete_user(Long id){
        List<Friendship> lst_friendships = this.repo.getAll();

        for(Friendship f : lst_friendships){
            if(Objects.equals(f.getUser1(), id)  || Objects.equals(f.getUser2(), id)){
                this.repo.deleteEntity(f.getID());
            }
        }
    }

    /**
     *Modifica data friendship ului cu id-ul dat
     */
    public void modifFriendship(Long id, String date) throws Exception {
        Friendship f = this.repo.findEntity(id);

        List<String> attr = Arrays.asList(date.split("-"));
        int an = Integer.parseInt(attr.get(0));
        int luna = Integer.parseInt(attr.get(1));
        int zi = Integer.parseInt(attr.get(2));

        LocalDate data = LocalDate.of(an, luna, zi);

        Friendship modif_friendship = new Friendship(f.getID(),f.getUser1(), f.getUser2(),data);
        Friendship f1 = this.repo.modifEntity(modif_friendship);
    }

}
