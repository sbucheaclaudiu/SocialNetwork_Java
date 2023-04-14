package com.example.socialntwrkgui.graph;

import java.util.*;

public class Graph {
    private int nr_vf; // No. of varfuri in graph

    private LinkedList<Integer>[] adj; // Adjacency List
    ArrayList<ArrayList<Integer> > components = new ArrayList<>();

   public Graph(int nr_vf)
    {
        this.nr_vf = nr_vf;
        adj = new LinkedList[nr_vf];

        for (int i = 0; i < nr_vf; i++)
            adj[i] = new LinkedList();
    }

    public void addEdge(int u, int w)
    {
        adj[u].add(w);
    }

    public void DFSUtil(int v, boolean[] visited, ArrayList<Integer> al)
    {
        visited[v] = true;
        al.add(v);
        //System.out.print(v + " ");
        Iterator<Integer> it = adj[v].iterator();

        while (it.hasNext()) {
            int n = it.next();
            if (!visited[n])
                DFSUtil(n, visited, al);
        }
    }

    public void DFS()
    {
        boolean[] visited = new boolean[nr_vf];

        for (int i = 0; i < nr_vf; i++) {
            ArrayList<Integer> al = new ArrayList<>();
            if (!visited[i]) {
                DFSUtil(i, visited, al);
                components.add(al);
            }
        }
    }

    public int BFS1(int s, int nr, Map<Integer, List<Integer>> graph) {
        // Mark all the vertices as not visited(By default
        // set as false)
        int dist[] = new int[nr + 1];
        boolean visited[] = new boolean[nr + 1];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            List<Integer> i = graph.get(s);
            for (int n : i) {
                if (!visited[n]) {
                    visited[n] = true;
                    dist[n] = dist[s] + 1;
                    queue.add(n);
                }
            }
        }
        int maxim=0;
        for(int i=1;i<=nr;i++){
            maxim = Math.max(maxim,dist[i]);
        }
        return maxim;
    }

    public int BFS(int s) {

        boolean visited[] = new boolean[nr_vf];
        int dist[] = new int[nr_vf + 1];

        LinkedList<Integer> queue = new LinkedList();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    dist[n] = dist[s] + 1;
                    queue.add(n);
                }
            }
        }

        int maxim=0;
        for(int i = 1; i <= nr_vf; i++){
            maxim = Math.max(maxim, dist[i]);
        }
        return maxim;
    }

    public int ConnecetedComponents() { return components.size(); }

}
