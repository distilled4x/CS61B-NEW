package bearmaps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


public class KDTreeTest {
    int max = 50;
    pointTest testVals = new pointTest(max, 100000);
    List<Point> pointSet = testVals.getPoints();

    ArrayList<Point> nearFast = new ArrayList<>();
    ArrayList<Point> nearSlow = new ArrayList<>();

    NaivePointSet naiveTest = new NaivePointSet(pointSet);
    KDTree KDTest = new KDTree(pointSet);

    Random r = new Random();

    long startTime;
    long endTime;

    @Before
    public void recordStartTime() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void recordEndTime() {
        endTime = System.currentTimeMillis();
        System.out.println("Last testcase execution time in millisecond : " + (endTime - startTime));
        System.out.println();
    }

    @Test
    public void KDTime() {
        System.out.println("Start Fast Test");
        for (int i = 0; i < 1000; i++) {
            double x = r.nextDouble() * max;
            double y = r.nextDouble() * max;
            KDTest.nearest(x,y);
        }
    }

    @Test
    public void naiveTime() {
        System.out.println("Start Naive Test");
        for (int i = 0; i < 1000; i++) {
            double x = r.nextDouble() * max;
            double y = r.nextDouble() * max;
            naiveTest.nearest(x,y);
        }
    }

    @Test
    public void comparePoints() {
        System.out.println("Start Compare Test");
        for (int i = 0; i < 1000; i++) {
            double x = r.nextDouble() * max;
            double y = r.nextDouble() * max;
            nearSlow.add(naiveTest.nearest(x,y));
            nearFast.add(KDTest.nearest(x,y));
        }

        for (int i = 0; i < 1000; i++) {
            assertEquals(nearFast.get(i), nearSlow.get(i));
        }
    }

}
