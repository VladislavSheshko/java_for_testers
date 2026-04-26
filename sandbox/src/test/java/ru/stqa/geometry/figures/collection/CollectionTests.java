package ru.stqa.geometry.figures.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionTests {

    @Test
    void arrayTests(){
        var arrey = new String[]{"a", "b", "c"};
        Assertions.assertEquals("a", arrey[0]);
    }


    //тест с использованием списков
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

    //тест с использованием множеств
    @Test
    void  setTests() {
        var set = Set.of("a", "b", "c");
        Assertions.assertEquals(3, set.size());

        //множество не может содержать повторяющиеся элементы
        //т.е такая конструкция некорректна
        //var set = Set.of("a", "b", "c", "a");
        //чтобы она стала валидной, необходимо сделать так
        //var set = Set.copyOf(List.of("a", "b", "c", "a"));
        //Чтобы сделать модифицируемый список, нужно вызвать конструктор
        //передав в качестве инициальзации список элементов
        var set1 = new HashSet<>(List.of("a", "b", "c", "a"));
        Assertions.assertEquals(3, set1.size());

        set1.add("a");  //если добавим элемент который там уже есть, размер множества не изменится
        Assertions.assertEquals(3, set1.size());
        set1.add("d");  //если добавим элемент которого еще нет размер изменится
        Assertions.assertEquals(4, set1.size());
    }
}
