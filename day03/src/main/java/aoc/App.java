/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class App {
    private final List<String> input;

    public App(List<String> input) {
        this.input = input;
    }

    public Integer getSolutionPart1() {
        int sum = 0;
        for (String line : input) {
            int mid = line.length() / 2;
            String[] parts = {line.substring(0, mid), line.substring(mid)};
            char[] first = parts[0].toCharArray();
            char[] second = parts[1].toCharArray();

            Arrays.sort(first);
            Arrays.sort(second);

            ArrayList<Character> found = new ArrayList<Character>();
            for (char c : first) {
                if (Arrays.binarySearch(second, c) >= 0) {
                    int index = (int) c % 32;
                    if (Character.isUpperCase(c)) {
                        index += 26;
                    }
                    sum = index + sum;
                    break;
                }
            }
        }
        return sum;
    }

    public Integer getSolutionPart2() {
        int sum = 0;
        String[] backpacks = new String[3];
        int i = 0;
        for (String line : input) {
            System.out.println(i % 3);
            backpacks[i % 3] = line;
            if (i % 3 == 2) {
                char[] first = backpacks[0].toCharArray();
                char[] second = backpacks[1].toCharArray();
                char[] third = backpacks[2].toCharArray();

                Arrays.sort(first);
                Arrays.sort(second);
                Arrays.sort(third);

                for (char c : first) {
                    if (Arrays.binarySearch(second, c) >= 0) {
                        if (Arrays.binarySearch(third, c) >= 0) {
                            int index = (int) c % 32;
                            if (Character.isUpperCase(c)) {
                                index += 26;
                            }
                            sum = index + sum;
                            break;
                        }
                    }
                }


            }
            i++;
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2"))
            System.out.println(new App(input).getSolutionPart2());
        else
            System.out.println(new App(input).getSolutionPart1());
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .collect(Collectors.toList());
    }
}
