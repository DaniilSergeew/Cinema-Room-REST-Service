package Cinema.Room.REST.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private final int total_rows;
    private final int total_columns;
    private final List<Seat> available_seats;
    @JsonIgnore
    private final List<OrderedSeat> orderedSeats;

    Cinema() {
        this.orderedSeats = new ArrayList<>();
        this.available_seats = new ArrayList<>();
        this.total_rows = 9;
        this.total_columns = 9;
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

    public List<OrderedSeat> getOrderedSeats() {
        return orderedSeats;
    }

    public void addOrderedSeat(OrderedSeat orderedSeat) {
        this.orderedSeats.add(orderedSeat);
    }
}
