package Cinema.Room.REST.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CinemaController {
    Cinema cinema = new Cinema();

    @GetMapping("/seats")
    public ResponseEntity<Cinema> getSeats() {
        return new ResponseEntity<>(cinema, HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {
        if (seat.getColumn() > 9 || seat.getRow() > 9 ||
                seat.getColumn() < 1 || seat.getRow() < 1) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        List<Seat> available_seats = cinema.getAvailable_seats();
        for (Seat tmp : available_seats) {
            if (tmp.getColumn() == seat.getColumn() && tmp.getRow() == seat.getRow()) {
                cinema.getAvailable_seats().remove(tmp);
                OrderedSeat result = new OrderedSeat(tmp);
                cinema.addOrderedSeat(result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        List<OrderedSeat> orderedSeats = cinema.getOrderedSeats();
        for (OrderedSeat orderedSeat : orderedSeats) {
            if (orderedSeat.getToken().equals(token.getToken())) {
                cinema.getOrderedSeats().remove(orderedSeat);
                cinema.getAvailable_seats().add(orderedSeat.getTicket());
                return new ResponseEntity<>(Map.of("returned_ticket", orderedSeat.getTicket()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
        }
        Map<String, Integer> statistic = new HashMap<>();
        int number_of_available_seats = cinema.getAvailable_seats().size();
        int number_of_purchased_tickets = cinema.getOrderedSeats().size();
        int current_income = 0;
        for (OrderedSeat orderedSeat : cinema.getOrderedSeats()) {
            current_income += orderedSeat.getTicket().getPrice();
        }
        statistic.put("current_income", current_income);
        statistic.put("number_of_available_seats", number_of_available_seats);
        statistic.put("number_of_purchased_tickets", number_of_purchased_tickets);
        return new ResponseEntity<>(statistic, HttpStatus.OK);
    }
}

