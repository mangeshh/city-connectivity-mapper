package com.mc.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DisjointSet {

    private Map<String, Node> map = new HashMap<>();

    public Map<String, Node> getMap(){
        return map;
    }

    public static class Node{
        public Node(){
            super();
        }

        String city;
        int rank;
        Node parent;

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + city +
                    ", rank=" + rank +
                    '}';
        }
    }

    public void makeSet(String city){
    	if(map.get(city)!=null) {
    		return;
    	}
    	
        Node node = new Node();
        node.city = city;
        if(node.parent == null){
            node.parent = node;
        }
        node.rank = 0;
        map.put(node.city, node);
    }

    public void union(String c1, String c2){

        Node parent1 = findSet(map.get(c1));
        Node parent2 = findSet(map.get(c2));
        
        if(parent1 == null || parent2 == null) {
        	return;
        }
        
        if(parent1.rank == parent2.rank) {
            parent1.rank = parent1.rank + 1;
            parent2.parent = parent1;
        }
        if(parent1.rank > parent2.rank){
            parent2.parent = parent1;
        }else{
            parent1.parent = parent2;
        }
    }

    public Node findSet(Node nd){
    	if(nd == null) return null;
        if(nd.parent == nd){
            return nd;
        }
        else{
            return findSet(nd.parent);
        }
    }

    public Node findSet(String city){
        return findSet(map.get(city));
    }

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet();

        ds.makeSet("A");
        ds.makeSet("B");
        ds.makeSet("C");
        ds.makeSet("D");
        ds.makeSet("E");
        ds.makeSet("F");
        ds.makeSet("G");
        ds.makeSet("X");


        ds.union("A", "B");
        ds.union("B", "C");
        ds.union("C", "D");
        ds.union("D", "E");
        ds.union("E", "F");
        ds.union("F", "G");

        System.out.println(ds.findSet("A"));
        System.out.println(ds.findSet("B"));
        System.out.println(ds.findSet("C"));
        System.out.println(ds.findSet("D"));
        System.out.println(ds.findSet("E"));
        System.out.println(ds.findSet("F"));
        System.out.println(ds.findSet("G"));

        Node n1 = ds.findSet("A");
        Node n2 = ds.findSet("G");

        if(n1 == n2){
            System.out.println("connected");
        } else{
            System.out.println("not connected");

        }

    }
}
