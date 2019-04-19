package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    ArrayHeapMinPQ<Vertex> vertices;
    SolverOutcome outcome;
    HashMap<Vertex, Double> fringe;
    AStarGraph<Vertex> input;
    Vertex end;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.vertices = new ArrayHeapMinPQ<>();
        this.fringe = new HashMap<>();
        this.outcome = SolverOutcome.UNSOLVABLE;
        this.input = input;
        this.end = end;
        Stopwatch sw = new Stopwatch();
        boolean timesUp = false;

        vertices.add(start, input.estimatedDistanceToGoal(start, end));
        fringe.put(start, input.estimatedDistanceToGoal(start, end));
        Vertex p = start;

        while (vertices.size() > 0) {
            if (vertices.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                //other stuff
                return;
            }

            p = vertices.removeSmallest();
            for (WeightedEdge<Vertex> v : input.neighbors(p)) {
                vertices.add(v.to(), Double.MAX_VALUE);
                fringe.put(v.to(), Double.MAX_VALUE);
                relax(v);
            }
        }





        if (timesUp) {
            outcome = SolverOutcome.TIMEOUT;
        }
    }


    // relaxes priorities in queue
    private void relax(WeightedEdge<Vertex> v) {
        Vertex p = v.from();
        Vertex q = v.to();
        double w = v.weight();

        if (fringe.get(p) + w < fringe.get(q)) {
            double priority = fringe.get(p) + w + input.estimatedDistanceToGoal(q, end);

            if (vertices.contains(q)) {
                vertices.changePriority(q, priority);
            } else {
                vertices.add(q, priority);
            }
            fringe.put(q, priority);
        }



    }




    @Override
    public SolverOutcome outcome() {
        return null;
    }

    @Override
    public List<Vertex> solution() {
        return null;
    }

    @Override
    public double solutionWeight() {
        return 0;
    }

    @Override
    public int numStatesExplored() {
        return 0;
    }

    @Override
    public double explorationTime() {
        return 0;
    }
}
