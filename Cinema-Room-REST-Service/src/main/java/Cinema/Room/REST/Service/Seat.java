package Cinema.Room.REST.Service;

public class Seat {
    private final int row;
    private final int column;
    private final int price;

    public Seat(int i, int j) {
        this.row = i;
        this.column = j;
        this.price = this.row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }
}
