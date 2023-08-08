package advent.of.code.day2;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

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
        int score = calculateScore(game);
        System.out.println("Score in part 1: " + score);
        int score2 = calculateScore(pickMoves(game));
        System.out.println("Score in part 2: " + score2);
    }

    static ArrayList<String> pickMoves(List<String> strategy) {
        var game = new ArrayList<String>();

        String[] toks;
        String opp;
        String result;
        String player;

        final String WIN = "Z";
        final String TIE = "Y";
        final String LOSE = "X";

        for (String round: strategy) {
            toks = round.split(" ");
            opp = toks[0];
            result = toks[1];
            player = "";
            switch(opp) {
                case OPPONENT_ROCK:
                    switch(result) {
                        case WIN:
                            player = PLAYER_PAPER;
                            break;
                        case TIE:
                            player = PLAYER_ROCK;
                            break;
                        case LOSE:
                            player = PLAYER_SCISSORS;
                            break;
                        default:
                            break;
                    }
                    break;
                case OPPONENT_PAPER:
                    switch(result) {
                        case WIN:
                            player = PLAYER_SCISSORS;
                            break;
                        case TIE:
                            player = PLAYER_PAPER;
                            break;
                        case LOSE:
                            player = PLAYER_ROCK;
                            break;
                        default:
                            break;
                    }
                    break;
                case OPPONENT_SCISSORS:
                    switch(result) {
                        case WIN:
                            player = PLAYER_ROCK;
                            break;
                        case TIE:
                            player = PLAYER_SCISSORS;
                            break;
                        case LOSE:
                            player = PLAYER_PAPER;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            game.add(opp + " " + player);
        } 
        return game;
    } 

    static int calculateScore(List<String> game) {
        int score = 0;
        String[] toks;
        String opp;
        String player;
        for (String round: game) {
            toks = round.split(" ");
            opp = toks[0];
            player = toks[1];

            switch(player) {
                case PLAYER_ROCK:
                    switch(opp) {
                        // 3 points for tie
                        case OPPONENT_ROCK:
                            score += TIEPTS;
                            break;
                        // 0 points for loss
                        case OPPONENT_PAPER:
                            score += LOSSPTS;
                            break;
                        // 6 points for win
                        case OPPONENT_SCISSORS:
                            score += WINPTS;
                            break;
                        default:
                            break;
                    }
                    // 1 point just for selecting rock
                    score += 1;
                    break;
                case PLAYER_PAPER:
                    switch(opp) {
                        // 6 points for win
                        case OPPONENT_ROCK:
                            score += WINPTS;
                            break;
                        // 3 points for tie
                        case OPPONENT_PAPER:
                            score += TIEPTS;
                            break;
                        // 0 points for loss
                        case OPPONENT_SCISSORS:
                            score += LOSSPTS;
                            break;
                        default:
                            break;
                    }
                    // 2 points just for selecting paper
                    score += 2;
                    break;
                case PLAYER_SCISSORS:
                    switch(opp) {
                        // 0 points for loss
                        case OPPONENT_ROCK:
                            score += LOSSPTS;
                            break;
                        // 6 points for win
                        case OPPONENT_PAPER:
                            score += WINPTS;
                            break;
                        // 3 points for tie
                        case OPPONENT_SCISSORS:
                            score += TIEPTS;
                            break;
                        default:
                            break;
                    }
                    // 3 points just for selecting scissors
                    score += 3;
                    break;
                default:
                    break;
                }
            }

        return score;
    }
}