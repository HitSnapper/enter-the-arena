package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.*;

/**
 * Created by HitSnapper on 2016-08-30.
 */
public class PriorityQueue<E> {
    List<E> objects;
    Map<E, Double> priorityMap;
    Comparator<E> comp;
    public PriorityQueue() {
        objects = new ArrayList<>();
        priorityMap = new HashMap<>();
        comp = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if (priorityMap.get(o1) < priorityMap.get(o2)){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        };
    }

    public void put(E object, double priority){
        priorityMap.put(object, priority);
        if (!objects.contains(object)) {
            objects.add(object);
        }
    }

    public E get(){
        E res = objects.get(0);
        for (E object : objects) {
            if (priorityMap.get(res) > priorityMap.get(object)){
                res = object;
            }
        }
        objects.remove(res);
        return res;
    }

    public boolean isEmpty(){
        return objects.size() == 0;
    }

    /**
     * Returns the objects sorted after priority.
     * @return
     */
    public List<E> getObjects(){
        objects.sort(comp);
        return objects;
    }

    public int size(){
        return objects.size();
    }
}