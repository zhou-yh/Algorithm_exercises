package com.myApp.algorithmproject.trie;

/**
 * author: zhouyh
 * created on: 2020/7/30 11:52 PM
 * description:
 */
public class TrieDemo {


    public static void main(String[] args){

        Trie<Integer> trie = new Trie<>();
        trie.add("cat",1);
        trie.add("dog",2);
        trie.add("duck",3);
        trie.add("cast",4);
        trie.add("小马哥",5);
        System.out.println(trie.get("小马哥"));
        System.out.println(trie.get("cat"));
        System.out.println(trie.startsWith("ca"));
        System.out.println(trie.startsWith("dd"));
        System.out.println(trie.remove("cat"));
        System.out.println(trie.remove("cast"));
        System.out.println(trie.size());
        System.out.println(trie.startsWith("小"));
    }
}
