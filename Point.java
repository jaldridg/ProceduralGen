
public class Point <E> {
    private E x;
    private E y;
    
    public Point(E x, E y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point<E> point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public E getX() {
        return x;
    }

    public E getY() {
        return y;
    }

    public boolean equals(Point<E> point) {
        return (this.x == point.getX()) && (this.y == point.getY());
    }
}
