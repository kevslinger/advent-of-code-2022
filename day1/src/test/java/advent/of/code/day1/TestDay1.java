package advent.of.code.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoIntQueue;


public class TestDay1 {
    private Queue<Integer> calories; 

    @BeforeEach
    public void setup() {
        calories = readIntoIntQueue(TestDay1.class.getResourceAsStream("/day1_test.txt"));
    }

    @Test
    public void testPart1() {
        assertEquals(24000, -1 * calories.poll());
    }

    @Test
    public void testPart2() {
        assertEquals(45000, -1 * calories.poll() - calories.poll() - calories.poll());
    }
    
}
