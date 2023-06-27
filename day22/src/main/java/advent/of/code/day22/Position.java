package advent.of.code.day22;

class Position {
    private int row;
    private int column;
    private int direction;

    Position(int row, int column, int direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    int getDirection() {
        return direction;
    }

    void setRow(int newRow) {
        row = newRow;
    }

    void setColumn(int newColumn) {
        column = newColumn;
    }

    void setDirection(int newDirection) {
        direction = newDirection;
    }

    void rotate(int direction) {
        // Java does not handle negative modulus the say way python does.
        this.direction = ((this.direction + direction) % 4 + 4) % 4;
    }

    public String toString() {
        return "(" + (row + 1) + ", " + (column + 1) + ") " + direction; 
    }

    public boolean equals(Position b) {
        return row == b.getRow() && column == b.getColumn() && direction == b.getDirection();
    }
}
