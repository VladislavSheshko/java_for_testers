package ru.stqa.geometry;

import ru.stqa.geometry.figures.Triangle;

public class  Geometry {
    public static void main(String[] args) {
        Triangle.printTriangleArea(new Triangle(10,10,10));
        Triangle.printTrianglePerimeter(new Triangle(5,7,9));
    }
}
