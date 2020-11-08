package com.proj.server;

import java.util.HashMap;
import java.util.Map;

public class LaddersMap {

    private static  final Map<Integer, Integer> Map= new HashMap<>();

    static {
        //ladders
        Map.put(1,38);
        Map.put(4,14);
        Map.put(8,30);
        Map.put(21,42);
        Map.put(28,76);
        Map.put(50,67);
        Map.put(71,92);
        Map.put(80,99);

        //snakes
        Map.put(32,10);
        Map.put(36,6);
        Map.put(48,26);
        Map.put(62,18);
        Map.put(88,24);
        Map.put(95,56);
        Map.put(97,78);
    }

    public static int getPosition(int pos){
        //If the key is present, sent the position ,
        // else default is same position.

        return Map.getOrDefault(pos,pos);
    }
}
