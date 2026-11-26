import java.util.List;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze(15, 15);

        Solver bfs = new BFS();
        Solver dijkstra = new Dijkstra();

        Results bfsResult = bfs.solve(maze);
        Results dijkstraResult = dijkstra.solve(maze);

        printSummary(bfs.getName(), maze, bfsResult);
        printSummary(dijkstra.getName(), maze, dijkstraResult);
    }

    private static void printSummary(String name, Maze maze, Results res) {
        System.out.println("=== " + name + " ===");
        System.out.println("Path Length (steps): " + (res.shortestPath().size() - 1));
        System.out.println("Total Terrain Cost:  " + res.cost());
        System.out.println("Cells Visited:       " + res.visitedOrder().size());

        List<Cell> path = res.shortestPath();
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getColumns(); c++) {
                Cell cell = maze.getCell(r, c);
                if (cell.equals(maze.getStart())) System.out.print("S ");
                else if (cell.equals(maze.getEnd())) System.out.print("E ");
                else if (cell.isWall()) System.out.print("# ");
                else if (path.contains(cell)) System.out.print("* ");
                else if (cell.getWeight() > 1) System.out.print("~ ");
                else System.out.print(". ");
            }
            System.out.println();
        }
        System.out.println();
    }
}