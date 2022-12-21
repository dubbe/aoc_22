/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import com.google.gson.*;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class App {


    public App() {
    }

    public int compare(JsonElement ele1, JsonElement ele2) {
        if(ele1.isJsonPrimitive() && ele2.isJsonPrimitive()) {
            int e1 = ele1.getAsNumber().intValue();
            int e2 = ele2.getAsNumber().intValue();
            return Integer.compare(e1, e2);
        }
        if(ele1.isJsonPrimitive()) {
            ele1 = JsonParser.parseString("[" + ele1 + "]").getAsJsonArray();
        }

        if(ele2.isJsonPrimitive()) {
            ele2 = JsonParser.parseString("[" + ele2 + "]").getAsJsonArray();
        }

        if(ele1.isJsonArray() && ele2.isJsonArray()) {
            JsonArray e1 = ele1.getAsJsonArray();
            JsonArray e2 = ele2.getAsJsonArray();

            for(int i = 0; i<e1.size();i++) {
                if(i > e2.size()-1) {
                    return 1;
                }
                int compare = compare(e1.get(i), e2.get(i));
                if(compare != 0) {
                    return compare;
                }
            }

            if(e1.size() < e2.size()) {
                return -1;
            }

        }

        return 0;
    }

    public Integer getSolutionPart1(List<String> input) {
        int sum = 0;

        for(int i = 0; i<input.size();i+=3) {
            int pair = ((i/3)+1);
            String pair1 = input.get(i);
            String pair2 = input.get(i+1);

            JsonArray json1 =  JsonParser.parseString(pair1).getAsJsonArray();
            JsonArray json2=  JsonParser.parseString(pair2).getAsJsonArray();

            int result = compare(json1, json2);
            if(result == -1) {
                sum += pair;
            }

        }
        return sum;
    }


    public Integer getSolutionPart2(List<String> input) {
        List<JsonArray> items = new ArrayList<>();
        items.add(JsonParser.parseString("[[2]]").getAsJsonArray());
        items.add(JsonParser.parseString("[[6]]").getAsJsonArray());
        for(int i = 0; i<input.size();i++) {
            if(input.get(i).equals("")) {
                continue;
            }
            items.add(JsonParser.parseString(input.get(i)).getAsJsonArray());
        }
        int t6 = 1;
        int t2 = 1;
        Collections.sort(items, (o1, o2) -> compare(o1, o2));
        for(int i = 0; i<items.size();i++) {
            if(items.get(i).equals(JsonParser.parseString("[[2]]").getAsJsonArray())) {
                t2 = i+1;
            }
            if(items.get(i).equals(JsonParser.parseString("[[6]]").getAsJsonArray())) {
                t6 = i+1;
            }
        }
        return t2*t6;
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