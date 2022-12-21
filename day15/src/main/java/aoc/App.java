/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class App {


    public App() {
    }


    public Integer getSolutionPart1(List<String> input, int l) {

        int sum = 0;
        HashMap<Point, Point> sensors = new HashMap<>();

        for(String line : input) {
            String[] parse = line.split(": closest beacon is at ");

            String test = parse[0].substring(10);

            String[] sensor = test.split(", ");
            Integer sx = Integer.parseInt(sensor[0].split("=")[1].trim());
            Integer sy = Integer.parseInt(sensor[1].split("=")[1].trim());


            String[] beacon = parse[1].split(", ");
            Integer bx = Integer.parseInt(beacon[0].split("=")[1].trim());
            Integer by = Integer.parseInt(beacon[1].split("=")[1].trim());


            sensors.put(new Point(sx, sy), new Point(bx, by));
        }

        List<Point> ranges = new ArrayList<>();
        for(Point s: sensors.keySet()) {
            Point b = sensors.get(s);
            int distance = Math.abs(b.x-s.x) + Math.abs(b.y-s.y);

            int range = distance - Math.abs(s.y - l);
            if(range > 0) {

                Point t = new Point(s.x - range, s.x + range);
                ranges.add(t);
            }
        }

        Set<Integer> test = new HashSet<>();
        for(Point r: ranges) {
            for(int i = r.x; i <= r.y; i++) {
                test.add(i);
            }
        }

        List<Integer> sortedList = new ArrayList<>(test);
        Collections.sort(sortedList);

        sum = sortedList.size();
        for(int i: sortedList) {
            Point p = new Point(i, l);
            if(sensors.containsValue(p)) {

                sum--;
            }
        }

        return sum;
    }


    public String getSolutionPart2(List<String> input, int max) {

        HashMap<Point, Point> sensors = new HashMap<>();

        for(String line : input) {
            String[] parse = line.split(": closest beacon is at ");

            String test = parse[0].substring(10);

            String[] sensor = test.split(", ");
            Integer sx = Integer.parseInt(sensor[0].split("=")[1].trim());
            Integer sy = Integer.parseInt(sensor[1].split("=")[1].trim());


            String[] beacon = parse[1].split(", ");
            Integer bx = Integer.parseInt(beacon[0].split("=")[1].trim());
            Integer by = Integer.parseInt(beacon[1].split("=")[1].trim());


            sensors.put(new Point(sx, sy), new Point(bx, by));
        }

        for(int y = max; y > 0; y--) {
            List<Point> ranges = new ArrayList<>();
            for(Point s: sensors.keySet()) {
                Point b = sensors.get(s);
                int distance = Math.abs(b.x-s.x) + Math.abs(b.y-s.y);

                int range = distance - Math.abs(s.y - y);
                if(range > 0) {

                    Point t = new Point(s.x - range, s.x + range);
                    ranges.add(t);
                }
            }



            if(ranges.size() > 1) {
                Collections.sort(ranges, new Comparator<Point>() {
                    @Override
                    public int compare(Point o1, Point o2) {
                        return o1.x - o2.x;
                    }
                });

               Point newRange = new Point(ranges.get(0).x, ranges.get(0).y);
               for(int i = 1; i < ranges.size(); i++) {
                   Point p = ranges.get(i);

                   if(newRange.y + 1 >= p.x) {
                           if(p.y > newRange.y) {
                           newRange.y = p.y;
                       }

                   } else {
                       return Long.toString(((newRange.y+1) * 4000000L) + y);
                   }
               }

            } else {
                System.out.println("some gone at least!");

            }

        }

        return "0";
    }

    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2"))
            System.out.println(new App().getSolutionPart2(input, 4000000));
        else
            System.out.println(new App().getSolutionPart1(input, 2000000));
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .collect(Collectors.toList());
    }
}