package ru.stqa.geometry.figures;

import java.util.Objects;

public record Square(
        double a
) {

    public Square {
        if (a <= 0) {
            throw new IllegalArgumentException("Сторона квадрата должна быть > 0");
        }
    }

    public static void printSquareArea(Square s) {
        System.out.println("Площадь квадрата = " + s.area());
    }

    public double area() {
        // Площадь квадрата: S = a², округляем до 2х знаков после запятой
        return Math.round(Math.pow(this.a, 2) * 100.0) / 100.0;
    }

    public static void printSquarePerimeter(Square s) {
        System.out.println("Периметр квадрата = " + s.perimeter());
    }

    public double perimeter() {
        // Периметр квадрата: P = 4 * a
        return 4 * this.a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Double.compare(this.a, square.a) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}
