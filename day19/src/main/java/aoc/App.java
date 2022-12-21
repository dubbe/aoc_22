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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class App {


    public App() {
    }

    public Integer getSolutionPart1(List<String> input) {
        Pattern p = Pattern.compile("Blueprint (\\d*): Each ore robot costs (\\d*) ore. Each clay robot costs (\\d*) ore. Each obsidian robot costs (\\d*) ore and (\\d*) clay. Each geode robot costs (\\d*) ore and (\\d*) obsidian.");
        for(String line : input) {
            Matcher m = p.matcher(line);
            if(m.matches()) {
                int blueprint = Integer.parseInt(m.group(1));
                int oreRobotCost = Integer.parseInt(m.group(2));
                int clayRobotCost = Integer.parseInt(m.group(3));
                int obsidian = Integer.parseInt(m.group(4));
                int geode = Integer.parseInt(m.group(5));
                int oreCost = Integer.parseInt(m.group(6));
                int obsidianCost = Integer.parseInt(m.group(7));

                int ore = 0;
                int clay = 0;
                int oreCollectors = 1;
                int clayCollectors = 0;
                int minute = 1;
                boolean contiune = true;
                while(contiune) {
                    System.out.println("== Minute " + minute + " ==");
                    int newClayCollector = 0;
                    // Check if we canin build something?
                    if(ore >= clayRobotCost) {
                        System.out.println("Spend " + clayRobotCost + " ore to start building a clay-collecting robot.");
                        ore -= clayRobotCost;
                        newClayCollector++;
                    }

                    // Collect the ore
                    if(oreCollectors > 0) {
                        ore += oreCollectors;
                        System.out.println(oreCollectors + " ore-collection robot collects " + oreCollectors + " ore; you now have " + ore + " ore.");
                    }
                    if(clayCollectors > 0) {
                        clay += clayCollectors;
                        System.out.println(clayCollectors + " clay-collection robot collects " + clayCollectors + " clay; you now have " + clay + " clay.");
                    }

                    if(newClayCollector > 0) {
                        clayCollectors += newClayCollector;
                        System.out.println("The new clay-collection robot is ready; you now have " + clayCollectors + " of them.");
                    }
                    minute++;
                    if(minute > 25) {
                        contiune = false;
                    }
                    System.out.println();
                }

            }
        }
        return 0;
    }
    public String getSolutionPart2(List<String> input) {

        return "0";
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