package advent.of.code.day22;

class Position {
    private int row;
    private int column;
    private int direction;
    private int cubeSide;

    Position(int row, int column, int direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
        this.cubeSide = -1;
    }

    Position(int row, int column, int direction, int cubeSide) {
        this.row = row;
        this.column = column;
        this.direction = direction;
        this.cubeSide = cubeSide;
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

    int getCubeSide() {
        return cubeSide;
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

    void setCubeSide(int cubeSide) {
        this.cubeSide = cubeSide;
    }

    void rotate(int direction) {
        // Java does not handle negative modulus the same way python does.
        this.direction = ((this.direction + direction) % 4 + 4) % 4;
    }

    @Override
    public String toString() {
        if (cubeSide >= 0) {
            return "(" + (row + 1) + "," + (column + 1) + ") in cube " + cubeSide + " with direction " + direction;
        }
        return "(" + (row + 1) + ", " + (column + 1) + ") " + direction; 
    }

    @Override
    public boolean equals(Object b) {
        if (b instanceof Position) {
            Position p = (Position)b;
            return row == p.getRow() && column == p.getColumn() && direction == p.getDirection();
        } else {
            return false;
        }
        
    }
}
