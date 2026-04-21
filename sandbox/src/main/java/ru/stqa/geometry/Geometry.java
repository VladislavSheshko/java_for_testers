package ru.stqa.geometry;

import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;

import java.util.List;
import java.util.function.Consumer;

public class Geometry {
    public static void main(String[] args) {
        var squares = List.of(new Square(7.0), new Square(5.0), new Square(3.0));
        for (Square square : squares) {
            Square.printSquareArea(square);
        }
        //Вариант 1
//        Consumer<Square> print = (square) -> {
//            Square.printSquareArea(square);
//        };
//        squares.forEach(print);
        //Вариант 2
        //1. Если параметр 1 как в нашем случае, круглые скобки можно не указывать
        //2. Тк тело состоит из 1 строчки, фигурные скобки тоже можно убрать
//        Consumer<Square> print = square -> Square.printSquareArea(square);
//        squares.forEach(print);
        //Вариант 3
        //Когда у нас функция представляет собой вызов како-то другой
        //функции мы можем в переменную присвоить эту самую другую функцию
//        Consumer<Square> print = Square::printSquareArea;
//        squares.forEach(print);
        //Вариант 4
        //Переменная print не так важна нам можно еще упростить
        squares.forEach(Square::printSquareArea);

//        Triangle.printTriangleArea(new Triangle(10,10,10));
//        Triangle.printTrianglePerimeter(new Triangle(5,7,9));
    }
}
