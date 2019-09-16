package forkjoin;

import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class RandomString {
    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int minStringSize;
    private final int maxStringSize;

    public RandomString(int minStringSize, int maxStringSize) {
        this.minStringSize = minStringSize;
        this.maxStringSize = maxStringSize;
    }

    public String nextString() {
        return Stream.generate(() -> (int) (Math.random() * ALPHA_STRING.length()))
                .map(ALPHA_STRING::charAt)
                .map(String::valueOf)
                .limit(getRandomStringSize())
                .collect(joining());
    }

    private long getRandomStringSize() {
        Random random = new Random();
        return  random.nextInt(maxStringSize - minStringSize + 1) + minStringSize;
    }
}
