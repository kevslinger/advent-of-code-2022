package advent.of.code.day9;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position p = (Position)o;
            return p.getX() == x && p.getY() == y;
        }
        return false;
    }
    
    public int hashCode() {
        return Integer.valueOf(x).hashCode() * 31 + Integer.valueOf(y).hashCode();
    }

    public String toString() {
        return x + ", " + y;
    }
}
