package advent.of.code.day2;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day2 {
    private static final String OPPONENT_ROCK = "A";
    private static final String OPPONENT_PAPER = "B";
    private static final String OPPONENT_SCISSORS = "C";

    private static final String PLAYER_ROCK = "X";
    private static final String PLAYER_PAPER = "Y";
    private static final String PLAYER_SCISSORS = "Z";

    private static final int WINPTS = 6;
    private static final int TIEPTS = 3;
    private static final int LOSSPTS = 0;

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day2.txt");
        List<String> game = readIntoStringListUntilEOF(path);
        int score = calculateGameScore(game);
        System.out.println("Score in part 1: " + score);
        int score2 = calculateGameScore(pickMoves(game));
        System.out.println("Score in part 2: " + score2);
    }

    static int calculateGameScore(List<String> game) {
        return game.stream().mapToInt(Day2::calculateRoundScore).sum();
    }

    static int calculateRoundScore(String game) {
        String[] toks = game.split(" ");
        String opp = toks[0];
        String player = toks[1];
        int score = 0;
        switch(player) {
            case PLAYER_ROCK -> {
                switch(opp) {
                    // 3 points for tie
                    case OPPONENT_ROCK -> {
                        score += TIEPTS;
                    }
                    // 0 points for loss
                    case OPPONENT_PAPER -> {
                        score += LOSSPTS;
                    }
                    // 6 points for win
                    case OPPONENT_SCISSORS -> {
                        score += WINPTS;
                    }
                }
                // 1 point just for selecting rock
                return score + 1;
            }
            case PLAYER_PAPER -> {
                switch(opp) {
                    // 6 points for win
                    case OPPONENT_ROCK -> {
                        score += WINPTS;
                    }
                    // 3 points for tie
                    case OPPONENT_PAPER -> {
                        score += TIEPTS;
                    }
                    // 0 points for loss
                    case OPPONENT_SCISSORS -> {
                        score += LOSSPTS;
                    }
                }
                // 2 points just for selecting paper
                return score + 2;
            }
            case PLAYER_SCISSORS -> {
                switch(opp) {
                    // 0 points for loss
                    case OPPONENT_ROCK -> {
                        score += LOSSPTS;
                    }
                    // 6 points for win
                    case OPPONENT_PAPER -> {
                        score += WINPTS;
                    }
                    // 3 points for tie
                    case OPPONENT_SCISSORS -> {
                        score += TIEPTS;
                    }
                }
                // 3 points just for selecting scissors
                return score + 3;
            }
        }
        return score;
    }

    static List<String> pickMoves(List<String> strategy) {
        return strategy.stream().map(Day2::pickMove).collect(Collectors.toList());
    }

    static String pickMove(String round) {
        String[] toks;
        String opp;
        String result;
        String player;

        final String WIN = "Z";
        final String TIE = "Y";
        final String LOSE = "X";

        toks = round.split(" ");
        opp = toks[0];
        result = toks[1];
        player = "";
        switch(opp) {
            case OPPONENT_ROCK -> {
                switch(result) {
                    case WIN -> {
                        player = PLAYER_PAPER;
                    }
                    case TIE -> {
                        player = PLAYER_ROCK;
                    }
                    case LOSE -> {
                        player = PLAYER_SCISSORS;
                    }
                }
            }
            case OPPONENT_PAPER -> {
                switch(result) {
                    case WIN -> {
                        player = PLAYER_SCISSORS;
                    }
                    case TIE -> {
                        player = PLAYER_PAPER;
                    }
                    case LOSE -> {
                        player = PLAYER_ROCK;
                    }
                }
            }
            case OPPONENT_SCISSORS -> {
                switch(result) {
                    case WIN -> {
                        player = PLAYER_ROCK;
                    }
                    case TIE -> {
                        player = PLAYER_SCISSORS;
                    }
                    case LOSE -> {
                        player = PLAYER_PAPER;
                    }
                }
            }
        }
        return opp + " " + player;
    } 
}
