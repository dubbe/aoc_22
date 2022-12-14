/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class App {

    public class Coord implements Comparable<Coord> {
        public int x = 0;
        public int y = 0;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public ArrayList<Coord> directNeighbors() {
            ArrayList<Coord> list = new ArrayList<Coord>();
            for (int yOff = -1; yOff < 2; yOff++) {
                for (int xOff = -1; xOff < 2; xOff++) {
                    //if not diagonal or self
                    if (xOff == 0 ^ yOff == 0) {
                        list.add(new Coord(x + xOff, y + yOff));
                    }
                }
            }
            return list;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Coord other = (Coord) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public int compareTo(Coord o) {
            if (this.equals(o))
                return 0;
            else if (o.y > this.y)
                return -1;
            else if (o.y < this.y)
                return 1;
            else
                return (o.x > this.x ? -1 : 1);
        }
    }

    public App() {
    }


    public Integer getSolutionPart1(List<String> input) {
        int sum = 0;
        Map<Coord, Integer> map = new HashMap<>();
        Coord start = null;
        Coord end = null;
        int x = 0;
        int y = 0;
        for (String line : input) {
            for (Character c : line.toCharArray()) {
                Coord p = new Coord(x, y);
                if (c == 'S') {
                    map.put(p, 1);
                    start = p;
                } else if (c == 'E') {
                    map.put(p, (int) 'z' - 96);
                    end = p;
                } else {
                    map.put(p, (int) c - 96);
                }
                x++;
            }
            y++;
            x = 0;
        }

        System.out.println(map);
        sum = BFSPathFind(map, start, end);
        return sum;
    }

    public Integer BFSPathFind(Map<Coord, Integer> map, Coord start, Coord end) {
        int sum = 0;
        HashMap<Coord, Integer> cost = new HashMap<Coord, Integer>();
        HashMap<Coord, Coord> parent = new HashMap<Coord, Coord>();
        Queue<Coord> queue = new LinkedList<>();
        queue.add(start);
        cost.put(start, 0);
        while (!queue.isEmpty()) {
            Coord curr = queue.poll();
            if (curr.equals(end)) {
                while (parent.containsKey(curr)) {
                    curr = parent.get(curr);
                    sum++;
                }
            }
            for (Coord p : curr.directNeighbors()) {
                if (!map.containsKey(p) || map.get(p) > map.get(curr) + 1) {
                    continue;
                }
                int possible = cost.get(curr) + 1;
                if (possible < cost.get(curr) || !cost.containsKey(p)) {
                    cost.put(p, possible);
                    parent.put(p, curr);
                    queue.add(p);
                }
            }
        }
        return sum;
    }

    public Integer getSolutionPart2(List<String> input) {

        Map<Coord, Integer> map = new HashMap<>();
        Coord end = null;
        int x = 0;
        int y = 0;
        for (String line : input) {
            for (Character c : line.toCharArray()) {
                Coord p = new Coord(x, y);
                if (c == 'S') {
                    map.put(p, 1);
                } else if (c == 'E') {
                    map.put(p, (int) 'z' - 96);
                    end = p;
                } else {
                    map.put(p, (int) c - 96);
                }
                x++;
            }
            y++;
            x = 0;
        }

        int minSteps = Integer.MAX_VALUE;
        for (Coord c : map.keySet()) {
            if (map.get(c) == 1) {
                int path = BFSPathFind(map, c, end);
                if (path < minSteps && path != 0) {
                    minSteps = path;
                }
            }
        }

        return minSteps;
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
