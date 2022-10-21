
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

    public boolean equals(Object e) {
        if (e == null) { return false; }
        if (e instanceof Point<?>) { 
            Point<?> point = (Point<?>) e; 
            return (this.x == point.getX()) && (this.y == point.getY());
        } else {
            return false;
        }
    }
}
