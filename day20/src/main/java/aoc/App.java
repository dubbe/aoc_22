/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


public class App {

    static class Node {

        int index;
        long value;

        Node origNextNode;
        Node origPreviousNode;

        Node nextNode;
        Node previousNode;

        public Node(int index, long value) {
            this.index = index;
            this.value = value;
        }
        public void move(long steps) {

            if(steps == 0) {
                return;
            }

            previousNode.nextNode = nextNode;
            nextNode.previousNode = previousNode;

            Node curr = this.nextNode;
            if(steps > 0) {
                for (long i = 0; i < steps - 1; i++) {
                    curr = curr.nextNode;
                }
            } else {
                for (long i = 0; i < Math.abs(steps) + 1; i++) {
                    curr = curr.previousNode;
                }
            }

            Node nextNode = this.nextNode = curr.nextNode;
            curr.nextNode = nextNode.previousNode = this;
            this.previousNode = curr;
        }
    }

    public class CircularLinkedList {
        private Node head = null;
        private Node tail = null;

        private int size = 0;

        public void add(long value) {
            Node newNode = new Node(size, value);

            if (head == null) {
                head = newNode;
                tail = head;
            } else {
                newNode.previousNode = newNode.origPreviousNode = tail;
                newNode.nextNode = newNode.origNextNode = head;

                tail.nextNode = newNode;
                tail.origNextNode = newNode;

                tail = newNode;

                head.previousNode = tail;
                head.origPreviousNode = tail;

            }

            size++;
        }

        public int size() {
            return size;
        }

        public void moveToValue(int value) {
            Node current = head;
            do {
                if(current.value == value) {
                    head = current;
                    tail = current.previousNode;
                }
                current = current.nextNode;
            } while (current != head);
        }

        public Node move(int steps) {
            steps = steps % size;
            Node current = head;
            for (int i = 0; i < steps; i++) {
                current = current.nextNode;
            }
            return current;
        }

    }

    public App() {
    }


    public String solve(CircularLinkedList cll, int times) {
        Node current = cll.head;

        for(int i = 0; i < times; i++) {
            do {
                long steps = current.value % (cll.size() - 1);
                current.move(steps);
                current = current.origNextNode;
            } while (current != cll.head);
        }

        cll.moveToValue(0);

        Node v1 = cll.move(1000);
        Node v2 = cll.move(2000);
        Node v3 = cll.move(3000);

        long sum = v1.value + v2.value + v3.value;

        return Long.toString(sum);
    }

    public String getSolutionPart1(List<String> input) {
        CircularLinkedList cll = new CircularLinkedList();
        for(String line : input) {
            cll.add(Integer.parseInt(line));
        }

        return solve(cll, 1);

    }
    public String getSolutionPart2(List<String> input) {
        CircularLinkedList cll = new CircularLinkedList();
        for(String line : input) {
            cll.add(Long.parseLong(line) * 811589153L);
        }
        return solve(cll, 10);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2"))
            System.out.println(new App().getSolutionPart2(input));
        else
            System.out.println(new App().getSolutionPart1(input));
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .collect(Collectors.toList());
    }
}
