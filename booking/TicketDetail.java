package booking;
/**
 * Class that represents the details of a ticket including the ticket ID, row number, seat number, zone type, and price.
 *
 *@author Yoshikazu Fujisaka
 */

public class TicketDetail {
    private String ticketId;
    private String rowNumber;
    private String seatNumber;
    private String zoneType;
    private double price;

    /**
     * Constructor to initialize a TicketDetail object.
     *
     * @param ticketId   The ID of the ticket.
     * @param rowNumber  The row number of the seat.
     * @param seatNumber The seat number.
     * @param zoneType   The type of zone (e.g., VIP, SEATING, STANDING).
     * @param price      The price of the ticket.
     */
    public TicketDetail(String ticketId, String rowNumber, String seatNumber, String zoneType, double price) {
        this.ticketId = ticketId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.zoneType = zoneType;
        this.price = price;
    }

    /**
     * Gets the ticket ID.
     *
     * @return The ID of the ticket.
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Gets the row number of the seat.
     *
     * @return The row number of the seat.
     */
    public String getRowNumber() {
        return rowNumber;
    }

    /**
     * Gets the seat number.
     *
     * @return The seat number.
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Gets the zone type.
     *
     * @return The type of zone (e.g., VIP, SEATING, STANDING).
     */
    public String getZoneType() {
        return zoneType;
    }

    /**
     * Gets the price of the ticket.
     *
     * @return The price of the ticket.
     */
    public double getPrice() {
        return price;
    }
}
