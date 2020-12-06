package ua.edu.ucu.tries;

import sun.misc.Queue;
import ua.edu.ucu.iterable.WordContainer;

import java.util.LinkedList;

public class RWayTrie implements Trie {
    private static final int alphaLen = 26;
    private final Node root;
    private final char FIRST_CH_IN_ALPHA = 'a';
    private int wordCounter = 0;

    public RWayTrie(){
        this.root = new Node(' ');
    }

    private static class Node
    {
        private Node (char val){
            this.val = val;
        }
        public Object val;
        public Node[] next = new Node[alphaLen];
        private int wordIndex = -1;

        private boolean hasNext(){
            for (Node n:this.next){
                if (n != null){
                    return true;
                }
            }
            return false;
        }
    }

    private String createWord(LinkedList<Node> nods){
        StringBuilder rez = new StringBuilder();
        for(Node node: nods){
            if (node == null){
                continue;
            }
            rez.append(node.val);
        }
        return rez.toString();
    }

    private Node[] extendNodeArr(Node... con){
        Node[] newContainer = new Node[con.length + 1];
        int i = 0;
        for (; i < con.length - 1; i++){
            newContainer[i] = con[i];
        }
        return newContainer;
    }

    private String[] extendStringArr(String... con){
        String[] newContainer = new String[con.length + 1];
        int i = 0;
        for (; i < con.length - 1; i++){
            newContainer[i] = con[i];
        }
        return newContainer;
    }

    public LinkedList<String> allWords(){
        LinkedList<Node> nods = new LinkedList<Node>();
        nods.add(root);
        LinkedList<String> st = new LinkedList<String>();
        st.add("");
        return wordList(nods, st);
    }

    private LinkedList<String> wordList(LinkedList<Node> before, LinkedList<String> container){
        for (Node node:before.getLast().next){
            if (node == null){
                continue;
            }

            before.add(node);
            if (before.getLast().wordIndex != -1){
                container.add(createWord(before));
            }
            wordList(before, container);

            before.removeLast();
        }

        return container;
    }

    // Додає в Trie кортеж - Tuple: слово - term, і його вагу - weight.
    // У якості ваги, використовуйте довжину слова
    @Override
    public void add(Tuple t) {
        String word = t.term;
        int len = t.weight;
        Node current = root;
        for (int i = 0; i<len; i++){

            if (current.next[(int)word.charAt(i) - (int)FIRST_CH_IN_ALPHA] == null){
                current.next[(int)word.charAt(i) - (int)FIRST_CH_IN_ALPHA] = new Node(word.charAt(i));
            }

            current = current.next[(int)word.charAt(i) - (int)FIRST_CH_IN_ALPHA];
        }
        current.wordIndex = wordCounter++;
    }

    // Чи є слово в Trie
    @Override
    public boolean contains(String word) {
        return allWords().contains(word);
    }



    // Видаляє слово з Trie
    @Override
    public boolean delete(String word) {
        Node curr = root;

        for (int i = 0; i < word.length(); i++){
            Node next = curr.next[(int)word.charAt(i) - (int)FIRST_CH_IN_ALPHA];
            if (next == null){
                return false;
            }
            if (!next.hasNext()){
                curr.next[(int)word.charAt(i) - (int)FIRST_CH_IN_ALPHA] = null;
                return true;
            }
            curr = next;
        }
        curr.wordIndex = -1;
        return true;
    }

    // Ітератор по всім словам (обхід дерева в ширину)
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    // Ітератор по всім словам, які починаються з pref
    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        int i = 0;
        for (String st:this.allWords()) {
            if (st.startsWith(s,1)){
                i++;
            }
        }
        WordContainer sk = new WordContainer(i);
        int j = 0;
        for (String st:this.allWords()) {
            if (st.startsWith(s,1)){
                sk.add(st.substring(1), j-1);
            }
            j++;
        }
        return sk;
    }

    // Кількість слів в Trie
    @Override
    public int size() { return allWords().size(); }

}
