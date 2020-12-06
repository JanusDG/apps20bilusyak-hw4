package ua.edu.ucu;

import ua.edu.ucu.autocomplete.PrefixMatches;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        RWayTrie trie = new RWayTrie();
        PrefixMatches prefixMatches = new PrefixMatches(trie);
//        Tuple tea = new Tuple("cader",5);

        Tuple t = new Tuple("cdefg",5);
        Tuple tea = new Tuple("abcd",4);
        Tuple te = new Tuple("abc",3);
        trie.add(t);
        trie.add(te);
        trie.add(tea);
        LinkedList<String> words = trie.allWords();
        for (String st: words){
            System.out.println(st);
        }
        System.out.println(words.size());
        trie.delete("abc");
//        trie.delete("abcd");
//        words = trie.allWords();
//        for (String st: words){
//            System.out.println(st);
//        }
        Iterable<String> sd = trie.words();
        for (String st: sd){
            System.out.println(st);
        }

    }

}
