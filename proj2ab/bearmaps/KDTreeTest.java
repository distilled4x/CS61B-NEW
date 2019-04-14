package bearmaps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Random;



public class KDTreeTest {
    int max = 50;
    pointTest testVals = new pointTest(max, 100000);
    List<Point> pointSet = testVals.getPoints();

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

}
