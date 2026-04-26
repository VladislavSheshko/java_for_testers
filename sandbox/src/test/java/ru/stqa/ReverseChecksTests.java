package ru.stqa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReverseChecksTests {
    @Test
    void testSqrt() {
        //Вариант 1: мы точно знаем какой результат ожидается
        var input = 4.0;
        var result = Math.sqrt(input);
        Assertions.assertEquals(2.0, result);

        //Вариант 2: мы примерно знаем результат и используем метод обратного сравнения
        var input1 = 5.0;
        var result1 = Math.sqrt(input1);
        var reverse = result1 * result1;
        Assertions.assertEquals(input1, reverse, 0.0001);
    }

    @Test
    void testSort() {
        var input = new ArrayList<>(List.of(3, 7, 4, 9, 0));
        input.sort(Integer::compareTo);
        for (int i = 0; i < input.size() - 1; i++) {
            Assertions.assertTrue(input.get(i) <= input.get(i + 1));
        }
    }
}
