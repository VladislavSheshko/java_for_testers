package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void calCalculateArea(){
        var triangle = new Triangle(10,10,10);
        Assertions.assertEquals(43.3, triangle.area());
    }

    @Test
    void calCalculatePerimeter(){
        Assertions.assertEquals(21, new Triangle(5,7,9).perimeter());
    }
}
