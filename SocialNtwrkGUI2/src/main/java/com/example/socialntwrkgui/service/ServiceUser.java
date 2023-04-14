package com.example.socialntwrkgui.service;

import com.example.socialntwrkgui.domain.User;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.graph.Graph;
import com.example.socialntwrkgui.repositoryDB.RepoDB;

import java.util.*;

public class ServiceUser {

    protected final RepoDB<Long, User> repo;

    public ServiceUser(RepoDB<Long, User> repo){
        this.repo = repo;
    }

    /**
     * Service pentru adaugare user in repo
     */
    public void addUser(Long id, String first_name, String second_name, String password) {
        User user = new User(id, first_name, second_name, password);
        user.setID(id);
        this.repo.addEntity(user);
    }

    /**
     * Service pentru modificare user din repo
     */
    public void modifUser(Long id, String new_first_name, String new_second_name, String password){
        User user = new User(id, new_first_name, new_second_name, password);
        user.setID(id);
        User old_user = findId(id);
        if(old_user == null)
            throw new LackException("\nId-ul nu este bun!\n");

        modifUserFriends(user, old_user);
        this.repo.modifEntity(user);
    }

    /**
     * Transfera lista de prieteni de la user ul vechi la cel nou
     */
    public void modifUserFriends(User user, User old_user){
        List<User> lst_friends = old_user.getFriends();

        for(User friend : lst_friends){
            friend.deleteFriend(old_user);
            friend.addFriend(user);
            user.addFriend(friend);
        }

    }

    /**
     * Service pentru stergere user din repo
     */
    public void deleteUser(Long id){
        User user = findId(id);
        if(user == null)
            throw new LackException("\nNu exista user cu id-ul dat!\n");

        deleteFriendsUser(user);
        this.repo.deleteEntity(id);
    }

    /**
     * Cauta user ul cu un id dat
     */
    private User findId(Long id){
        List<User> lst_users = this.repo.getAll();

        for(User u : lst_users){
            if(Objects.equals(u.getID(), id))
                return u;
        }
        return null;
    }

    /**
     * Sterge lista de prieteni a unui user
     */
    private void deleteFriendsUser(User user){
        List<User> lst_friends = user.getFriends();

        for(User friend : lst_friends){
            friend.deleteFriend(user);
        }
    }

    public User getUser(Long id) throws Exception {
        return this.repo.findEntity(id);
    }

    /**
     *Returneaza lista de useri din sistem(repo)
     */
    public List<User> getAll(){

        List<User> lst_users = this.repo.getAll();

        if(lst_users.size() == 0){
            throw new LackException("\nNu exista useri in aplicatie!\n");
        }
        else{
            return lst_users;
        }
    }

    /**
     * Returneaza o lista de useri repr prietenii userului cu id-ul dat
     */
    public List<User> friendsList(Long id, List<User> lst) {

        for(User u : lst){
            if(Objects.equals(u.getID(), id)){
                return u.getFriends();
            }
        }

        throw new LackException("\nUserul cu id-ul dat nu exista!\n");

    }

    /**
     * Returneaza nr de comunitati din sistem(repo
     */
    public int communitiesNumber(List<User> lst_users){
        int nr_users = 0;
        int max_id = 0;

        for(User u : lst_users){
            if(u.getID().intValue() > max_id)
                max_id = u.getID().intValue();
            nr_users++;
        }

        Graph g = new Graph(max_id);

        for(User user : lst_users) {
            List<User> lst_friends = user.getFriends();

            for (User friend : lst_friends) {
                g.addEdge(((Long) user.getID()).intValue()-1, ((Long) friend.getID()).intValue()-1);
            }
        }

        g.DFS();

        if(nr_users < max_id) {
            int nr = max_id - nr_users;
            return g.ConnecetedComponents() - nr;
        }
        else return g.ConnecetedComponents();
    }

    private void dfs(int x, int[] visited, int[][]matrix, int nr, List<Long> membri){
        //System.out.print(x + "-");
        membri.add((long) (x + 1));
        visited[x] = 1;
        int ramificare = 1;
        int i;

        for(i = 0; i < nr; i++){
            if(visited[i] == 0 && matrix[i][x] == 1){
                if(ramificare == 0){
                    //System.out.println(" ("+x+") \n");
                    ramificare = 1;
                }
                dfs(i, visited, matrix, nr, membri);
                ramificare = 0;
            }
        }

    }

    /**
     * Determina cea mai sociabila comunitate
     * Returneaza o lista de utilizatori care fac parte din acea comunitate
     */
    public List<User> mostSocialCom(List<User> lst_users) throws Exception {
        int max_id = 0;

        List<User> lst_user_comunitie = new ArrayList<>();
        List<List<Long>> lst_comunitati =  new ArrayList<>();

        for(User u : lst_users){
            if(u.getID().intValue() > max_id)
                max_id = u.getID().intValue();
        }

        int[][] matrix = new int[max_id][max_id];

        //Det matricea de adiacenta

        for(User user : lst_users) {
            List<User> lst_friends = user.getFriends();

            for (User friend : lst_friends) {
                matrix[((Long) user.getID()).intValue()-1][((Long) friend.getID()).intValue()-1] = 1;
            }
        }

        int i;
        int[] visited = new int[max_id];

        for(i = 0; i < max_id; i++){
            visited[i] = 0;
        }

        //Det tuturor comunitatilor

        for(i = 0; i < max_id; i++){
            if(visited[i] == 0){
                List<Long> membri = new ArrayList<>();
                dfs(i, visited, matrix, max_id, membri);
                lst_comunitati.add(membri);
                //System.out.println("|\n");
            }
        }

        Graph g = new Graph(max_id);

        for(User user : lst_users) {
            List<User> lst_friends = user.getFriends();

            for (User friend : lst_friends) {
                g.addEdge(((Long) user.getID()).intValue()-1, ((Long) friend.getID()).intValue()-1);
            }
        }

        int max = 0, k = 0;
        int nr = 0;

        for(List<Long> e : lst_comunitati) {
            for (Long id : e) {
                int n = g.BFS(id.intValue()-1);
                if (n >= max) {
                    max = n;
                    k = nr;
                }
            }
            nr++;
        }

        i = 0;
        for(List<Long> e : lst_comunitati){
            if( i == k){
                for(Long id : e){
                    User u = this.repo.findEntity(id);
                    lst_user_comunitie.add(u);
                }
                return lst_user_comunitie;
            }
            i++;
        }

        return null;


    }

    /**
     * Returneaza o lista de liste care repr toate comunitatile din sistem(repo)
     */
    public List<List<User>> show_comunities(List<User> lst_users) throws Exception {

        int max_id = 0;

        List<List<Long>> lst_comunitati =  new ArrayList<>();
        List<List<User>> lst_comunitati_usertipe =  new ArrayList<>();

        for(User u : lst_users){
            if(u.getID().intValue() > max_id)
                max_id = u.getID().intValue();
        }

        int[][] matrix = new int[max_id][max_id];

        //Det matricea de adiacenta

        for(User user : lst_users) {
            List<User> lst_friends = user.getFriends();

            for (User friend : lst_friends) {
                matrix[((Long) user.getID()).intValue()-1][((Long) friend.getID()).intValue()-1] = 1;
            }
        }

        int i;
        int[] visited = new int[max_id];

        for(i = 0; i < max_id; i++){
            visited[i] = 0;
        }

        //Det tuturor comunitatilor

        for(i = 0; i < max_id; i++){
            if(visited[i] == 0){
                List<Long> membri = new ArrayList<>();
                dfs(i, visited, matrix, max_id, membri);
                lst_comunitati.add(membri);
            }
        }


        for(List<Long> l : lst_comunitati){
            List<User> lst_usr = new ArrayList<>();
            for(Long id : l){
                User u = this.repo.findEntity(id);
                lst_usr.add(u);
            }
            lst_comunitati_usertipe.add(lst_usr);
        }

        if(lst_comunitati_usertipe.isEmpty())
            throw new LackException("\nNu exista comunitati!\n");

        return lst_comunitati_usertipe;
    }

}

