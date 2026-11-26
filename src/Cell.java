import java.util.Objects;

public class Cell {
    private final int row;
    private final int col;
    private boolean wall;
    private int weight;
    private boolean visited;

    public Cell(int row, int col, boolean wall, int weight) {
        this.row = row;
        this.col = col;
        this.wall = wall;
        this.weight = weight;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean getVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }

    public boolean isWall() {return wall; }
    public void setWall(boolean wall) { this.wall = wall; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() { return Objects.hash(row, col); }

    @Override
    public String toString() { return "Cell(" + row + "," + col + ")"; }
}
