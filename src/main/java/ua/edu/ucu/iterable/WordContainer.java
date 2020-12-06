package ua.edu.ucu.iterable;

import java.util.Iterator;

public class WordContainer implements Iterable<String>{
    private final String[] container;
    private final int size;

    public WordContainer(int length) {
        this.size = length;
        this.container = new String[length];
    }

    public void add(String word, int index) {
        if (index >= 0 && index < container.length) {this.container[index] = word;}
        else {throw new IndexOutOfBoundsException("Index " +index+ " out of bounds!");}
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int elementIndex;

            @Override
            public boolean hasNext() {
                return elementIndex < size;
            }

            @Override
            public String next() {
                if (this.hasNext()) {
                    elementIndex++;
                    return container[elementIndex-1];
                }
                return null;
            }
        };
    }

    public String[] toArray() {
        return this.container.clone();
    }


}