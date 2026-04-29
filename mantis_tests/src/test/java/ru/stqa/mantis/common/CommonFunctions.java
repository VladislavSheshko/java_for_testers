package ru.stqa.mantis.common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
    //    public static String randomString(int n) {
//        var rnd = new Random();
//        var result = "";
//        for (int i = 0; i < n; i++) {
//            result = result + (char) ('a' + rnd.nextInt(26));
//        }
//        return result;
//    }
    // реализация такого же функционала как в коде выше,
    // только с использованием функционального программирования
    public static String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        var result = Stream.generate(randomNumbers)
                .limit(n)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
        return result;
    }
}
