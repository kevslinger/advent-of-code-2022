package advent.of.code.day21;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.lang.IllegalStateException;

class YellerMonkey {
    private String name;
    private String yell;
    private String operation;
    private int result;

    YellerMonkey(String line) {
        String regex1 = "([a-z]+): ([0-9]+)"; // Their name and their number
        Matcher matcher = Pattern.compile(regex1).matcher(line);
        matcher.find();
        try {
            yell = matcher.group(2);
            name = matcher.group(1);
            result = Integer.parseInt(matcher.group(2));
        } catch (IllegalStateException e) {
            String regex2 = "([a-z]+): ([a-z]{4}) (\\+|\\-|\\*|\\/) ([a-z]{4})";
            matcher = Pattern.compile(regex2).matcher(line);
            matcher.find();
            name = matcher.group(1);
            yell = matcher.group(2) + " " + matcher.group(3) + " " + matcher.group(4);
            operation = matcher.group(3);
        }
    }

    String getName() {
        return name;
    }

    String getYell() {
        return yell;
    }

    String getOperation() {
        return operation;
    }

    int getResult() {
        return result;
    }

    public String toString() {
        return name + ": " + yell;
    }
}