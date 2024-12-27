package user;

import concert.*;
import booking.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class representing a user.
 * Implements functionalities for user actions related to concerts and bookings.
 *
 * @author Yoshikazu Fujisaka
 */
public abstract class User implements UserActions {
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the details of concert.
     *
     * @param concerts A list of concerts.
     * @param venues   A map of concert IDs to their corresponding venues.
     * @param bookings A list of bookings.
     */
    @Override
    public void showTimings(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings) {
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s%-15s%-15s%-15s%-30s%-15s%-15s%-15s\n", "#", "Date", "Artist Name", "Timing",
                "Venue Name", "Total Seats", "Seats Booked", "Seats Left");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------");

        for (Concert concert : concerts) {
            int numberOfBookedSeat = 0;

            for (Booking b : bookings) {
                if (concert.getConcertId().equals(b.getConcertId())) {
                    numberOfBookedSeat += b.getTotalTickets();
                }
            }
            System.out.printf("%-5s%-15s%-15s%-15s%-30s%-15s%-15s%-15s\n", concert.getConcertId(), concert.getDate(),
                    concert.getArtist(), concert.getTiming(), concert.getVenue(),
                    venues.get(concert.getConcertId()).getTotalSeats(), numberOfBookedSeat,
                    venues.get(concert.getConcertId()).getTotalSeats() - numberOfBookedSeat);
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays the ticket costs for different zones in a concert.
     *
     * @param concert The concert for the price to be displayed.
     */
    @Override
    public void showTicketCost(Concert concert) {
        System.out.printf("---------- %8s ----------%n", "STANDING");
        showTicketCostHelper(concert.getStandingPrice());

        System.out.printf("---------- %8s ----------%n", "SEATING");
        showTicketCostHelper(concert.getSeatingPrice());

        System.out.printf("---------- %8s ----------%n", "VIP");
        showTicketCostHelper(concert.getVipPrice());
    }

    /**
     * Helper method to display ticket costs for a specific zone.
     *
     * @param price An array containing the prices for left, center, and right zone.
     */
    @Override
    public void showTicketCostHelper(double[] price) {
        System.out.println("Left Seats:   " + price[0]);
        System.out.println("Center Seats: " + price[1]);
        System.out.println("Right Seats:  " + price[2]);
        System.out.println("------------------------------");
    }

    /**
     * Finds bookings for a specific concert.
     *
     * @param bookings  A list of all bookings.
     * @param concertId concertId that corresponds to booking.
     * @return A list of bookings for the specified concert.
     */
    @Override
    public ArrayList<Booking> findBooking(ArrayList<Booking> bookings, String concertId) {
        ArrayList<Booking> correspondingBookings = new ArrayList<Booking>();
        for (Booking booking : bookings) {
            if (booking.getConcertId().equals(concertId)) {
                correspondingBookings.add(booking);
            }
        }
        return correspondingBookings;
    }

    /**
     * Displays booking details for a specific concert.
     *
     * @param concert               The concert for which booking details are to be
     *                              displayed.
     * @param correspondingBookings A list of bookings for the specified concert.
     */
    @Override
    public void showBooking(Concert concert, ArrayList<Booking> correspondingBookings) {
        if (correspondingBookings.size() > 0) {
            System.out.println("Bookings");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s%-15s%-15s%-10s%-15s%-15s%-10s%n", "Id", "Concert Date", "Artist Name", "Timing",
                    "Venue Name", "Seats Booked", "Total Price");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------");

            for (Booking b : correspondingBookings) {
                System.out.printf("%-5s%-15s%-15s%-10s%-15s%-15s%-10s%n", b.getBookingId(), concert.getDate(),
                        concert.getArtist(), concert.getTiming(), concert.getVenue(), b.getTotalTickets(),
                        calculateTotalPrice(b));
            }
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            showTicketInfo(correspondingBookings);
            System.out.println();
        } else {
            System.out.println("No Bookings found for this concert");
            System.out.println();
        }
    }

    /**
     * Displays detailed ticket information for a list of bookings.
     *
     * @param bookings A list of bookings.
     */
    @Override
    public void showTicketInfo(ArrayList<Booking> bookings) {
        System.out.println("Ticket Info");
        for (Booking b : bookings) {
            System.out.printf("############### Booking Id: %s ####################%n", b.getBookingId());
            System.out.printf("%-5s%-15s%-15s%-10s%-10s%n", "Id", "Aisle Number", "Seat Number", "Seat Type", "Price");
            System.out.println("##################################################");
            for (TicketDetail td : b.getTicketDetails()) {
                System.out.printf("%-5s%-15s%-15s%-10s%-10s%n", td.getTicketId(), td.getRowNumber(), td.getSeatNumber(),
                        td.getZoneType(), td.getPrice());
            }
            System.out.println("##################################################");
            System.out.println();
        }
    }

    /**
     * Calculates the total price for a booking.
     *
     * @param booking The booking for which the total price is to be calculated.
     * @return The total price of the booking.
     */
    @Override
    public double calculateTotalPrice(Booking booking) {
        double totalPrice = 0;
        for (TicketDetail td : booking.getTicketDetails()) {
            totalPrice += td.getPrice();
        }
        return totalPrice;
    }
}
