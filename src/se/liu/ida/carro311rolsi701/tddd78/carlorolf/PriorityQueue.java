package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.*;

/**
 * Created by HitSnapper on 2016-08-30.
 */
public class PriorityQueue<E> {
    List<E> objects;
    Map<E, Double> dict;
    Comparator<E> comp;
    public PriorityQueue() {
        objects = new ArrayList<>();
        dict = new HashMap<>();
        comp = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if (dict.get(o1) < dict.get(o2)){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        };
    }

    public void put(E object, double priority){
        dict.put(object, priority);
        if (!objects.contains(object)) {
            objects.add(object);
        }
    }

    public E get(){
        E res = objects.get(0);
        for (E object : objects) {
            if (dict.get(res) < dict.get(object)){
                res = object;
            }
        }
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
}
