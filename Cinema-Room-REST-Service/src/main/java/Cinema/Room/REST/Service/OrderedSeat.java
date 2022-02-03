package Cinema.Room.REST.Service;

import java.util.UUID;

public class OrderedSeat {
    private final Seat ticket;
    private final UUID token;

    OrderedSeat(Seat ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID();
    }

    public Seat getTicket() {
        return ticket;
    }

    public UUID getToken() {
        return token;
    }
}
