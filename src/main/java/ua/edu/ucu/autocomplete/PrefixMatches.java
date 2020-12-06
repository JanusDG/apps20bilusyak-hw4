package ua.edu.ucu.autocomplete;

import ua.edu.ucu.iterable.WordContainer;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 *
 * @author andrii
 */
public class PrefixMatches {
    private final int MIN_PREF_LEN = 3;
    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    // Формує in-memory словник слів. Метод може приймати слово, рядок,
    // масив слів/рядків. Якщо приходить рядок, то він має бути розділений
    // на окремі слова (слова відокремлюються пробілами).

    // До словника мають додаватися лише слова довші за 2 символи.
    // Передбачається, що у рядках які надходять знаки пунктуації відсутні.
    public int load(String... strings) {
        int i = 0;
        for (String st:strings){
            if (st.length() > 2){
                i ++;
                trie.add(new Tuple(st, st.length()));
            }
        }
        return i;
    }

    // Чи є слово у словнику
    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    // Видаляє слово зі словника.
    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    // Якщо pref довший або дорівнює 2ом символам, то повертається усі слова які починаються з даного префіксу.
    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref == null || pref.length() < 2) {
            throw new IllegalArgumentException("" +
                    "Prefix should have >= 2 characters");
        }

        return wordsWithPrefix(pref, 1000);
    }

    // Якщо pref довший або дорівнює 2ом символам, то повертається набір слів k різних довжин (починаючи з довжини префіксу або 3 якщо префікс містить дві літери) і які починаються з даного префіксу pref.
    // Приклад: задані наступні слова та їх довжини:
    // abc 3
    // abcd 4
    // abce 4
    // abcde 5
    // abcdef 6
    // Вказано префікс pref='abc',
    // - при k=1 повертається 'abc'
    // - при k=2 повертається 'abc', 'abcd', 'abce'
    // - при k=3 повертається 'abc', 'abcd', 'abce', 'abcde'
    // - при k=4 повертається 'abc', 'abcd', 'abce', 'abcde', 'abcdef'
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref == null || pref.length() < 2) {
            throw new IllegalArgumentException("" +
                    "Prefix should have >= 2 characters");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("" +
                    "Indicator k has to be positive valued integer");
        }

        int minPrefLen = pref.length();
        if (pref.length() == 2) {
            minPrefLen = MIN_PREF_LEN;
        }

        WordContainer allWordsWithPrefix =
                (WordContainer) this.trie.wordsWithPrefix(pref);
        ArrayList<String> wordsThatMatch = new ArrayList<>();

        int wordLength;
        for (String word: allWordsWithPrefix.toArray()) {
            wordLength = word.length();
            if (minPrefLen <= wordLength && wordLength <= minPrefLen + k - 1) {
                wordsThatMatch.add(word);
            }
        }

        return wordsThatMatch;
    }

    // Кількість слів у словнику
    public int size() {
        return this.trie.size();
    }
}
