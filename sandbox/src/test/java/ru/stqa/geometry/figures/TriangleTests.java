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

    @Test
    void testEquality(){
        var t1 = new Triangle(5,5,5);
        var t2 = new Triangle(5,5,5);
        Assertions.assertEquals(t1, t2);
    }

    @Test
    void testNonEquality(){
        var t1 = new Triangle(5,5,5);
        var t2 = new Triangle(4,3,5);
        Assertions.assertNotEquals(t1, t2);
    }

    @Test
    void testEquality2(){
        var t1 = new Triangle(3,4,5);
        var t2 = new Triangle(4,5,3);
        Assertions.assertEquals(t1, t2);
    }

    @Test
    void testEquality4(){
        var triangle = new Triangle(2, 3, 4);
        var triangle1 = new Triangle(2, 4, 3);
        Assertions.assertEquals(triangle, triangle1);
    }
}
