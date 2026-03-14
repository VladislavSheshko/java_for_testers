package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void calCalculateArea(){
        Assertions.assertEquals(43.3,Triangle.triangleArea(10,10, 10));
    }

    @Test
    void calCalculatePerimeter(){
        Assertions.assertEquals(21,Triangle.trianglePerimeter(5,7, 9));
    }
}
