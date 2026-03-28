package ru.stqa.geometry.figures.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTests {

    @Test
    void arrayTests(){
        var arrey = new String[]{"a", "b", "c"};
        Assertions.assertEquals("a", arrey[0]);
    }

    @Test
    void listTests(){
        var list = new ArrayList<>();
        Assertions.assertEquals(0, list.size());
        //инициализация списка через List.of, создает не модифицируемый список
        //обойти можно с помощью такой конструкции
        var list2 = new ArrayList<>(List.of("a", "b", "c"));
        Assertions.assertEquals(3, list2.size());
        Assertions.assertEquals("a", list2.get(0));

        list2.set(0, "d");
        Assertions.assertEquals("d", list2.get(0));
    }
}
