import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Maze {

    private final int[][] maze;
    private final int rows;
    private final int columns;

    private enum Direction {
        DOWN(0,1),
        UP(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.maze = new int[rows][columns];
        generateMaze();
    }

    private void generateMaze() {
        // 1) make grid with only blocks 2. 'carve' out a path
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.maze[row][column] = 1;
            }
        }
        carvePaths(1,1);
    }

    private void carvePaths(int row, int column) { //choose random direction
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        maze[row][column] = 0; // carve start
        for  (Direction d : directions) {
            int nextRow = row + d.dx*2;
            int nextColumn = column + d.dy*2;

            if (isInBounds(nextRow, nextColumn) && maze[nextRow][nextColumn] == 1 ) {
                maze[row + d.dx][column + d.dy] = 0;
                carvePaths(nextRow, nextColumn);
            }
        }

    }

    private boolean isInBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public void printMaze() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if  (maze[r][c] == 1) { System.out.print("##"); }
                else { System.out.print("  "); }
            }
            System.out.println();
        }
    }

}
