package av7;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.*;

public class SetAndMapExample {
    public static void main(String[] args) {
        HashSet<String> hashset=new HashSet<>();
        hashset.add("12345");
        hashset.add("Bob");
        hashset.add("Alice");
        hashset.add("someoneeee");
        System.out.println("Hashset gi printa spored tezinata na nivniot hash");
        System.out.println(hashset);

        LinkedHashSet<String> hashset1=new LinkedHashSet<>();
        hashset1.add("12345");
        hashset1.add("Bob");
        hashset1.add("Alice");
        hashset1.add("someoneeee");
        System.out.println("LinkedHashset gi printa spored redosledot na vnesuvanje");
        System.out.println(hashset1);

        TreeSet<String> treeset=new TreeSet<>();
        treeset.add("12345");
        treeset.add("Bob");
        treeset.add("Alice");
        treeset.add("someoneeee");
        System.out.println("Treeset gi sortira");
        System.out.println(treeset);

        System.out.println("=================================");

        HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("1", "123");
        hashMap.put("2", "456");
        hashMap.put("Emi", "emcheemche");
        hashMap.put("Moni", "monimonche");
        System.out.println("Hashmap:");
        System.out.println(hashMap);

        LinkedHashMap<String, String> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put("1", "123");
        linkedHashMap.put("2", "456");
        linkedHashMap.put("Emi", "emcheemche");
        linkedHashMap.put("Moni", "monimonche");
        System.out.println("LinkedHashmap:");
        System.out.println(linkedHashMap);

        TreeMap<String, String> treeMap=new TreeMap<>();
        treeMap.put("1", "123");
        treeMap.put("2", "456");
        treeMap.put("B", "emcheemche");
        treeMap.put("A", "monimonche");
        System.out.println("LinkedHashmap:");
        System.out.println(treeMap);
    }
}
