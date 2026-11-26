import java.util.*;

public interface Solver {
    String getName();
    Results solve(Maze maze);
}

record Results(List<Cell> visitedOrder, List<Cell> shortestPath, int cost) {}

class BFS implements Solver {
    @Override
    public String getName() { return "Breadth-First Search"; }

    @Override
    public Results solve(Maze maze) {
        Cell start = maze.getStart();
        Cell end = maze.getEnd();

        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        List<Cell> visitedOrder = new ArrayList<>();
        Map<Cell, Cell> cameFrom = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell currCell = queue.remove();
            visitedOrder.add(currCell);

            if (currCell.equals(end)) {
                List<Cell> path = new ArrayList<>();
                int totalCost = 0;
                Cell step = end;

                while (step != null && !step.equals(start)) {
                    path.add(step);
                    totalCost += step.getWeight();
                    step = cameFrom.get(step);
                }
                path.add(start);
                Collections.reverse(path);

                return new Results(visitedOrder, path, totalCost);
            }

            for (Cell n : maze.getWalkableNeighbors(currCell)) {
                if (visited.add(n)) { // Returns true only if n was not already in visited
                    cameFrom.put(n, currCell);
                    queue.offer(n);
                }
            }
        }
        return new Results(visitedOrder, List.of(), 0);
    }
}

class Dijkstra implements Solver {
    private record Node(Cell cell, int cost) {}

    @Override
    public String getName() { return "Dijkstra's Algorithm"; }

    @Override
    public Results solve(Maze maze) {
        Cell start = maze.getStart();
        Cell end = maze.getEnd();

        Map<Cell, Cell> cameFrom = new HashMap<>();
        Map<Cell, Integer> cost = new HashMap<>();
        Set<Cell> visited = new HashSet<>();
        List<Cell> visitedOrder = new ArrayList<>();

        PriorityQueue<Node> pqueue = new PriorityQueue<>(Comparator.comparingInt(Node::cost));
        cost.put(start, 0);
        pqueue.add(new Node(start, 0));

        while (!pqueue.isEmpty()) {
            Cell currCell = pqueue.poll().cell();
            if (!visited.add(currCell)) continue;
            visitedOrder.add(currCell);

            if (currCell.equals(end)) {
                List<Cell> path = new ArrayList<>();
                Cell step = end;

                while (step != null && !step.equals(start)) {
                    path.add(step);
                    step = cameFrom.get(step);
                }
                path.add(start);
                Collections.reverse(path);

                return new Results(visitedOrder, path, cost.get(end));
            }

            for (Cell n : maze.getWalkableNeighbors(currCell)) {
                if (visited.contains(n)) continue;

                int newCost = cost.get(currCell) + n.getWeight();
                if (newCost < cost.getOrDefault(n, Integer.MAX_VALUE)) {
                    cost.put(n, newCost);
                    cameFrom.put(n, currCell);
                    pqueue.offer(new Node(n, newCost));
                }
            }
        }

        return new Results(visitedOrder, List.of(), 0);
    }
}