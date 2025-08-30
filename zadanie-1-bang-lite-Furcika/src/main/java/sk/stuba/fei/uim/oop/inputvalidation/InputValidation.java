package sk.stuba.fei.uim.oop.inputvalidation;

import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Objects;

public class InputValidation {
    public int checkAnswer(ArrayList<Integer> goodAnswer, int answer) {
        try {
            boolean contains = false;
            for (Integer integer : goodAnswer) {
                if (answer == integer) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                throw new InvalidCardIndexException("Invalid input, try again!");
            }
        } catch (InvalidCardIndexException e) {
            answer = ZKlavesnice.readInt(e.getMessage());
            answer = checkAnswer(goodAnswer, answer);
        }
        return answer;
    }

    public String checkAnswer(String[] goodAnswer, String answer) {
        try {
            boolean contains = false;
            for (String s : goodAnswer) {
                if (Objects.equals(answer, s)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                throw new InvalidCardIndexException("Invalid input, try again!");
            }
        } catch (InvalidCardIndexException e) {
            answer = ZKlavesnice.readString(e.getMessage());
            answer = checkAnswer(goodAnswer, answer);
        }
        return answer;
    }
}

