/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    private final List<String> input;

    public App(List<String> input) {
        this.input = input;
    }

    public class Parsed {
        private List<List<String>> stacks;
        private List<Instruction> instructions;
    }

    public class Instruction {
        private Integer amount;
        private Integer from;
        private Integer to;
    }

    public Parsed parseInput() {
        Parsed parsed = new Parsed();
        parsed.stacks = new ArrayList<>();
        parsed.instructions = new ArrayList<>();
        boolean firstPart = true;
        for (String line: input) {
            if(line.isEmpty()) {
                continue;
            }

            if(firstPart) {
                for (int i = 0; i < line.length(); i += 4) {
                    if (parsed.stacks.size() <= i / 4) {
                        parsed.stacks.add(new ArrayList<>());
                    }

                    Integer start = i;
                    Integer end = start + 3;
                    if (end > line.length()) {
                        end = line.length();
                    }
                    String value = line.substring(start, end).strip().replace("[", "").replace("]", "");
                    if(value.isEmpty()) {
                        continue;
                    }

                    try {
                        int number = Integer.parseInt(value);
                        firstPart = false;
                        break;
                    } catch (NumberFormatException e) {
                        // do nothing
                    }
                    parsed.stacks.get(i / 4).add(value);
                }
            } else {

                List<String> ins = List.of(line.split(" "));

                Instruction instruction = new Instruction();
                instruction.amount =  Integer.parseInt(ins.get(1));
                instruction.from = Integer.parseInt(ins.get(3))-1;
                instruction.to = Integer.parseInt(ins.get(5))-1;
                parsed.instructions.add(instruction);

            }
        }

        for(List<String> stack: parsed.stacks) {
            Collections.reverse(stack);
        }
        return parsed;
    }

    public String getSolutionPart1() {
        String answer = "";

        Parsed parsed = parseInput();

        for(Instruction instruction: parsed.instructions) {
            for (int i = 0; i < instruction.amount; i++) {
                String value = parsed.stacks.get(instruction.from).remove(parsed.stacks.get(instruction.from).size() - 1);
                parsed.stacks.get(instruction.to).add(value);
            }
        }

        for(List<String> stack: parsed.stacks) {
            answer = answer + stack.get(stack.size()-1);
        }
        return answer;
    }

    public String getSolutionPart2() {
        String answer = "";
        Parsed parsed = parseInput();
        for(Instruction instruction: parsed.instructions) {
            List<String> crates = new ArrayList<>();
            for (int i = 0; i < instruction.amount; i++) {
                String value = parsed.stacks.get(instruction.from).remove(parsed.stacks.get(instruction.from).size() - 1);
                crates.add(value);
            }
            Collections.reverse(crates);
            parsed.stacks.get(instruction.to).addAll(crates);
        }
        for(List<String> stack: parsed.stacks) {
            answer = answer + stack.get(stack.size()-1);
        }
        return answer;
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
