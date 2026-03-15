package ru.stqa.geometry.figures;

import java.util.Objects;

public record Triangle(
        double a,
        double b,
        double c
) {

    public Triangle{
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Стороны треугольника должны быть > 0");
        }
        if (a + b <= c || b + c <= a || a + c <= b) {
            throw new IllegalArgumentException("Нарушено неравенство треугольника");
        }
    }

    public static void printTriangleArea(Triangle t) {
        System.out.println("Площадь треугольника = " + t.area());
    }

    public  double area() {
        // Вычисление полупериметра
        double p = (this.a + this.b + this.c) / 2.0;
        // Формула Герона: S = sqrt(p * (p - a) * (p - b) * (p - c)) и округляем до 2х знаков после запятой
        return Math.round(Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c)) * 100.0) / 100.0;
    }


    public static void printTrianglePerimeter(Triangle t) {
        System.out.println("Периметр треугольника = " + t.perimeter());
    }

    public double perimeter() {
        // Вычисление периметра
        return this.a + this.b + this.c;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (Double.compare(this.a, triangle.a) == 0 && Double.compare(this.b, triangle.b) == 0 && Double.compare(this.c, triangle.c) == 0)
                || (Double.compare(this.a, triangle.b) == 0 && Double.compare(this.b, triangle.c) == 0 && Double.compare(this.c, triangle.a) == 0)
                || (Double.compare(this.a, triangle.c) == 0 && Double.compare(this.b, triangle.a) == 0 && Double.compare(this.c, triangle.b) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
