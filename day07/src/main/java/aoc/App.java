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

    public static class Directory {
        public String name;
        public List<Directory> directories;
        public Directory parent;
        public List<File> files;
        public Integer size;
    }

    public static class File {
        public String name;
        public Integer size;
    }

    public App() {
    }


    public Integer calculateSize(Directory dir, Integer maxSize)  {
        Integer size = 0;
        if(dir.directories != null) {
            for (Directory d : dir.directories) {
                size += calculateSize(d, maxSize);
            }
        }
        if (dir.size < maxSize) {
            size += dir.size;
        }
        return size;
    }

    public void findSmallestUnder (Directory dir, Integer under, List<Integer> found)  {
        if(dir.directories != null) {
            for (Directory d : dir.directories) {
                findSmallestUnder(d, under, found);
            }
        }
        if (dir.size > under) {
            found.add(dir.size);
        }
    }
    public Integer getSolutionPart1(List<String> input) {
        return calculateSize(getDirectory(input), 100000);
    }

    private static Directory getDirectory(List<String> input) {
        Directory dir = new Directory();
        dir.name = "/";
        dir.size = 0;
        Directory currDir = dir;
        int currSize = 0;
        for(String line: input) {

            String[] params = line.split(" ");
            String command = params[1];
            if(params[0].equals("$")) {
                command = params[1];
                if(command.equals("cd")) {
                    currDir.size += currSize;
                    currSize = 0;

                    if(params[2].equals("..")) {
                        currDir.parent.size += currDir.size;
                        currDir = currDir.parent;
                    } else {
                        if(params[2].equals("/")) {
                            continue;
                        }
                        Directory newDir = new Directory() {};
                        newDir.name = params[2];
                        newDir.parent = currDir;
                        newDir.size = 0;
                        if (currDir.directories == null) {
                            currDir.directories = new ArrayList<>();
                        }
                        currDir.directories.add(newDir);
                        currDir = newDir;
                    }

                }
            }
            else {
                if(params[0].equals("dir")) {
                    continue;
                }
                File file = new File() {};
                file.name = params[1];
                file.size = Integer.parseInt(params[0]);
                if(currDir.files == null) {
                    currDir.files = new ArrayList<>();
                }
                currDir.files.add(file);
                currSize += file.size;
            }
        }

        while(currDir.parent != null) {
            currDir.size += currSize;
            currDir.parent.size += currDir.size;
            currDir = currDir.parent;
        }
        return dir;
    }

    public Integer getSolutionPart2(List<String> input) {
        Directory dir = getDirectory(input);
        int needed = 30000000 - (70000000 - dir.size);
        List<Integer> found = new ArrayList<>();
        findSmallestUnder(dir, needed, found);
        Collections.sort(found);
        return found.get(0);
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
