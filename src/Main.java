public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze(15, 15);

        Solver bfs = new BFS();
        Solver dijkstra = new Dijkstra();

        MazeUI.printToTerminal(maze, bfs.getName(), bfs.solve(maze));
        MazeUI.printToTerminal(maze, dijkstra.getName(), dijkstra.solve(maze));
    }
}