package ru.stqa.geometry.figures;

public class Triangle {

    public static void printTriangleArea(double a, double b, double c) {
        System.out.println("Площадь треугольника = " + triangleArea(a, b, c));
    }

    public static double triangleArea(double a, double b, double c) {
        // Вычисление полупериметра
        double p = (a + b + c) / 2.0;
        // Формула Герона: S = sqrt(p * (p - a) * (p - b) * (p - c)) и округляем до 2х знаков после запятой
        return Math.round(Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 100.0) / 100.0;
    }


    public static void printTrianglePerimeter(double a, double b, double c) {
        System.out.println("Периметр треугольника = " + trianglePerimeter(a, b, c));
    }

    public static double trianglePerimeter(double a, double b, double c) {
        // Вычисление периметра
        return a + b + c;
    }
}
