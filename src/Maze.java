import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Maze {
    private final Cell[][] grid;
    private final int rows;
    private final int columns;
    private final Cell start;
    private final Cell end;

    private enum Direction {
        DOWN(1, 0),
        UP(-1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        final int dRow, dCol;
        Direction(int dRow, int dCol) { this.dRow = dRow; this.dCol = dCol; }
    }

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Cell[rows][columns];
        generateMaze();

        this.start = grid[1][1];
        this.end = grid[rows - 2][columns - 2];
        this.start.setWeight(1);
        this.end.setWeight(1);
    }

    private void generateMaze() {
        Random random = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                grid[r][c] = new Cell(r, c, true, 1);
            }
        }
        carvePaths(1, 1, random);
        addLoops(0.12, random); // multiple routes to compare algos
    }

    private void carvePaths(int row, int col, Random random) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions, random);

        grid[row][col].setWall(false);
        grid[row][col].setWeight(random.nextInt(4) == 0 ? 5 : 1); // 25% mud tiles (cost 5)

        for (Direction d : directions) {
            int nextRow = row + d.dRow * 2;
            int nextCol = col + d.dCol * 2;

            if (isInBounds(nextRow, nextCol) && grid[nextRow][nextCol].isWall()) {
                Cell between = grid[row + d.dRow][col + d.dCol];
                between.setWall(false);
                between.setWeight(1);
                carvePaths(nextRow, nextCol, random);
            }
        }
    }

    private void addLoops(double chance, Random random) {
        for (int r = 2; r < rows - 2; r++) {
            for (int c = 2; c < columns - 2; c++) {
                if (grid[r][c].isWall() && random.nextDouble() < chance) {
                    boolean verticalPassage = !grid[r - 1][c].isWall() && !grid[r + 1][c].isWall();
                    boolean horizontalPassage = !grid[r][c - 1].isWall() && !grid[r][c + 1].isWall();
                    if (verticalPassage || horizontalPassage) {
                        grid[r][c].setWall(false);
                        grid[r][c].setWeight(1);
                    }
                }
            }
        }
    }

    public List<Cell> getWalkableNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        for (Direction d : Direction.values()) {
            int nr = cell.getRow() + d.dRow;
            int nc = cell.getCol() + d.dCol;
            if (isInBounds(nr, nc) && !grid[nr][nc].isWall()) {
                neighbors.add(grid[nr][nc]);
            }
        }
        return neighbors;
    }

    public boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < columns;
    }

    public Cell getCell(int r, int c) { return grid[r][c]; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public Cell getStart() { return start; }
    public Cell getEnd() { return end; }
}
