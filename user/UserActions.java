package user;

import concert.*;
import booking.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that defines actions for a user.
 * This interface provides methods for displaying menus, showing concert
 * timings, ticket costs,booking information, and calculating total prices.
 *
 * author Yoshikazu Fujisaka
 */
public interface UserActions {

    /**
     * Displays the main menu for the user and handles user input.
     *
     * @param concerts  A list of concerts.
     * @param venues    A map of concert IDs to their corresponding venues.
     * @param bookings  A list of bookings.
     * @param concertId The ID of the concert.
     */
    void mainMenu(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings,
            int... concertId);

    /**
     * Displays the menu options for the user.
     */
    void printMenu();

    /**
     * Displays the timings and availability of concerts.
     *
     * @param concerts A list of concerts.
     * @param venues   A map of concert IDs to their corresponding venues.
     * @param bookings A list of bookings.
     */
    void showTimings(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings);

    /**
     * Displays the ticket costs for different zones in a concert.
     *
     * @param concert The concert whose ticket costs are to be displayed.
     */
    void showTicketCost(Concert concert);

    /**
     * Helper method to display ticket costs for a specific zone.
     *
     * @param price An array containing the prices for left, center, and right seats
     *              in the zone.
     */
    void showTicketCostHelper(double[] price);

    /**
     * Finds bookings for a specific concert.
     *
     * @param bookings  A list of all bookings.
     * @param concertId The ID of the concert for which bookings are to be found.
     * @return A list of bookings for the specified concert.
     */
    ArrayList<Booking> findBooking(ArrayList<Booking> bookings, String concertId);

    /**
     * Displays booking details for a specific concert.
     *
     * @param concert               The concert for which booking details are to be
     *                              displayed.
     * @param correspondingBookings A list of bookings for the specified concert.
     */
    void showBooking(Concert concert, ArrayList<Booking> correspondingBookings);

    /**
     * Displays detailed ticket information for a list of bookings.
     *
     * @param bookings A list of bookings.
     */
    void showTicketInfo(ArrayList<Booking> bookings);

    /**
     * Calculates the total price for a booking.
     *
     * @param booking The booking for which the total price is to be calculated.
     * @return The total price of the booking.
     */
    double calculateTotalPrice(Booking booking);
}
