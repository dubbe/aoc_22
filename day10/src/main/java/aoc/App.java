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


public class App {

    public App() {
    }


    public Integer getSolutionPart1(List<String> input) {
        int x = 1;
        int cycle = 1;
        List<Integer> cycles = new ArrayList<>();
        cycles.add(20);
        cycles.add(60);
        cycles.add(100);
        cycles.add(140);
        cycles.add(180);
        cycles.add(220);

        int sum = 0;

        for(String line : input) {
            if(line.equals("noop")) {
                cycle +=1;
                if(cycles.contains(cycle)) {
                    sum += (x*cycle);
                }
            } else {
                String[] instr = line.split(" ");
                for(int i = 0; i < 2; i++) {
                    cycle +=1;
                    if(i==1) {
                        x += Integer.parseInt(instr[1]);
                    }
                    if(cycles.contains(cycle)) {
                        sum += (x*cycle);
                    }
                }


            }

        }

        return sum;
    }

    public Integer getSolutionPart2(List<String> input) {
        int x = 1;
        int cycle = 1;

        int sum = 0;

        String answer = "";

        for(String line : input) {
            if(line.equals("noop")) {

                if (cycle >= x && cycle <= x + 2) {
                    answer += "#";
                } else {
                    answer += ".";
                }

                if(cycle == 40) {
                    answer +="\n";
                    cycle=1;
                } else {
                    cycle += 1;
                }
            } else {
                String[] instr = line.split(" ");
                for(int i = 0; i < 2; i++) {

                    if (cycle >= x && cycle <= x + 2) {
                        answer += "#";
                    } else {
                        answer += ".";
                    }


                    if(i==1) {
                        x += Integer.parseInt(instr[1]);
                    }

                    if(cycle == 40) {
                        answer +="\n";
                        cycle=1;
                    } else {
                        cycle += 1;
                    }
                }


            }

        }

        System.out.println(answer);
        return sum;
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
