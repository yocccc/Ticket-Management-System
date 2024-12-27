package booking;

import java.util.ArrayList;

/**
 * Class that represents a booking.
 * This class encapsulates the details of a booking including the booking ID,
 * customer ID, customer name, concert ID,
 * total number of tickets, and the list of ticket details.
 *
 *
 *
 * author Yoshikazu Fujisaka
 */
public class Booking {
    private String bookingId;
    private String customerId;
    private String customerName;
    private String concertId;
    private int totalTickets;
    private ArrayList<TicketDetail> ticketDetails;

    /**
     * Constructor to initialize a Booking object.
     *
     * @param bookingId    The ID of the booking.
     * @param customerId   The ID of the customer.
     * @param customerName The name of the customer.
     * @param concertId    The ID of the concert.
     * @param totalTickets The total number of tickets booked.
     */
    public Booking(String bookingId, String customerId, String customerName, String concertId, int totalTickets) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.concertId = concertId;
        this.totalTickets = totalTickets;
        this.ticketDetails = new ArrayList<>();
    }

    /**
     * Adds a ticket detail to the booking.
     *
     * @param ticketId   The ID of the ticket.
     * @param rowNumber  The row number of the seat.
     * @param seatNumber The seat number.
     * @param zoneType   The type of zone
     * @param price      The price of the ticket.
     */
    public void addTicketDetail(String ticketId, String rowNumber, String seatNumber, String zoneType, double price) {
        TicketDetail ticketDetail = new TicketDetail(ticketId, rowNumber, seatNumber, zoneType, price);
        this.ticketDetails.add(ticketDetail);
    }

    /**
     * Gets the concert ID.
     *
     * @return The concert ID.
     */
    public String getConcertId() {
        return concertId;
    }

    /**
     * Gets the list of ticket details.
     *
     * @return The list of ticket details.
     */
    public ArrayList<TicketDetail> getTicketDetails() {
        return ticketDetails;
    }

    /**
     * Gets the total number of tickets booked.
     *
     * @return The total number of tickets booked.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Gets the booking ID.
     *
     * @return The booking ID.
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Gets the customer ID.
     *
     * @return The customer ID.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets the customer name.
     *
     * @return The customer name.
     */
    public String getCustomerName() {
        return customerName;
    }
}
