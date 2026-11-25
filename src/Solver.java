import java.util.Queue;
import java.util.List;

public interface Solver {
    String getName();
    Results solve(Maze maze);
}

record Results(List<Cell> visitedOrder, List<Cell> shortestPath, int totalCost) {}

class BFS implements Solver {
    @Override
    public String getName() { return "Breadth-First Search"; }

    @Override
    public Results solve(Maze maze) {
        return null;
    }
}

class Dijkstra implements Solver {
    @Override
    public String getName() { return "Dijkstra's Algorithm"; }

    @Override
    public Results solve(Maze maze) {
        return null;
    }
}
