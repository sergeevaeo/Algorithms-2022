package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) { //a
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Сложная
     */

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new myTrieIterator();
    }

    public class myTrieIterator implements Iterator<String> {

        ArrayDeque<Iterator<Map.Entry<Character,Node>>> deque = new ArrayDeque<>();
        String element;
        StringBuilder word;
        String delete;

        void finder() {
            Iterator<Map.Entry<Character, Node>> node = deque.peekLast();
            if(!deque.isEmpty()) {
                while (!node.hasNext()) {
                    deque.removeLast();
                    if (!deque.isEmpty()) {
                        word.deleteCharAt(word.length() - 1);
                        node = deque.peekLast();
                    } else {
                        node = null;
                        break;
                    }
                }
            }
            if (!deque.isEmpty()) {
                while (node.hasNext()) {
                    Map.Entry<Character, Node> nextNode = node.next();
                    word.append(nextNode.getKey());
                    node = new HashMap<>(nextNode.getValue().children).entrySet().iterator();
                    deque.addLast(node);
                }
                if (word.charAt(word.length() - 1) != (char) 0) {
                    finder();
                } else {
                    word.deleteCharAt(word.length() - 1);
                    deque.removeLast();
                }
            }
        }


        private myTrieIterator() {
            word = new StringBuilder();
            if (size != 0) {
                deque.push(root.children.entrySet().iterator());
                finder();
            }
        }

        //Трудоемкость О(1)
        //Ресурсоемкость O(1)
        @Override
        public boolean hasNext() {
            return word.length() != 0;
        }

        //Трудоемкость О(n)
        //Ресурсоемкость O(n)
        @Override
        public String next() throws NoSuchElementException {
            if (hasNext()) {
                element = word.toString();
                delete = element;
                finder();
                return element;
            } else {
                throw new NoSuchElementException();
            }
        }

        //Трудоемкость О(n) n - длина строки
        //Ресурсоемкость O(1)
        @Override
        public void remove() throws IllegalStateException {
            if (delete == null) {
                throw new IllegalStateException();
            } else {
                Trie.this.remove(delete);
                delete = null;
            }
        }
    }






}