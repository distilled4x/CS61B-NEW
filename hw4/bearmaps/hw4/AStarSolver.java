package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double timeSpent;
    private HashMap<vertexNode, Double> weights;
    private vertexNode next;
    private int states;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<vertexNode<Vertex>> vertices = new ArrayHeapMinPQ<>();
        Stopwatch sw = new Stopwatch();
        this.weights = new HashMap<>();
        this.outcome = SolverOutcome.UNSOLVABLE;
        this.next = null;
        this.timeSpent = 0;
        this.states = 0;
        boolean timesUp = false;

        vertexNode<Vertex> startNode = new vertexNode<>(start);
        vertices.add(startNode, input.estimatedDistanceToGoal(start, end));
        weights.put(startNode, 0.0);

        while (vertices.size() > 0) {
            next = vertices.removeSmallest();
            Vertex x = (Vertex) next.v;


            if (timeSpent > timeout) {
                timesUp = true;
                return;
            }

            if (x.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }

            states++;

            for(WeightedEdge<Vertex> edge : input.neighbors(x)) {
                Vertex to = edge.to();
                double weight = edge.weight();
                double distance = Double.MAX_VALUE;

                vertexNode toNode = new vertexNode(to, next);
                double priority = weights.get(next) + weight;

                if(vertices.contains(toNode)) {
                    distance = weights.get(toNode);
                }

                if (weights.get(next) + weight < distance) {

                    if (vertices.contains(toNode)) {
                        vertices.changePriority(toNode, priority + input.estimatedDistanceToGoal((Vertex) toNode.v, end));
                    } else {
                        vertices.add(toNode, priority + input.estimatedDistanceToGoal((Vertex) toNode.v, end));
                    }
                    weights.put(toNode, priority);
                }
            }

            timeSpent = sw.elapsedTime();
        }

        if (timesUp) {
            outcome = SolverOutcome.TIMEOUT;
        }
    }

    private class vertexNode<Vertex> {
        private Vertex v;
        private vertexNode parent;

        public vertexNode(Vertex v, vertexNode parent) {
            this.v = v;
            this.parent = parent;
        }

        public vertexNode(Vertex v) {
            this(v, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof vertexNode)) return false;
            vertexNode that = (vertexNode) o;
            return Objects.equals(v, that.v);
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        vertexNode solution = next;
        List<Vertex> solutionList = new LinkedList<>();

        if (outcome != SolverOutcome.SOLVED) {
            return solutionList;
        }

        ((LinkedList<Vertex>) solutionList).addFirst((Vertex) solution.v);

        while (solution.parent != null) {
            solution = solution.parent;
            ((LinkedList<Vertex>) solutionList).addFirst((Vertex) solution.v);
        }

        return solutionList;
    }

    @Override
    public double solutionWeight() {
        if (outcome != SolverOutcome.SOLVED) {
            return 0;
        }

        return weights.get(next);
    }

    @Override
    public int numStatesExplored() {
        return states;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
