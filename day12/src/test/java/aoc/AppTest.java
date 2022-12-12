/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    App classUnderTest;
    App app;


    @BeforeEach
    void createSubject() throws IOException {

        classUnderTest = new App();

        app = new App();
    }

    @Test
    void part1Solution() throws IOException {
        List<String> input = parseInput("input_test.txt");
        assertEquals(31, classUnderTest.getSolutionPart1(input));
    }

    @Test
    void part2Solution() throws IOException {
        List<String> input = parseInput("input_test.txt");
        assertEquals(29, classUnderTest.getSolutionPart2(input));
    }


    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .collect(Collectors.toList());
    }
}
