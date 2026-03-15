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

    @Test
    void cannotCreateTriangleWithNegativeSide(){
        try{
            new Triangle(-5,7,-9);
            Assertions.fail();
        }catch (IllegalArgumentException exception){
         // ОК
        }
    }

    @Test
    void cannotCreateTriangleWithBrokenInequality(){
        try{
            new Triangle(1, 2, 4);  // 1+2=3 <4 → нарушение
            Assertions.fail();
        }catch (IllegalArgumentException exception){
            // ОК
        }
    }
}
