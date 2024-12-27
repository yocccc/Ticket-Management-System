package user;

import concert.*;
import booking.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * an admin action such as updating ticket prices, viewing bookings and viewing
 * total payment.
 *
 * @author Yoshikazu Fujisaka
 */
public class Admin extends User {
    private static final int SHOW_TIMING = 1;
    private static final int UPDATE_TICKET_COST = 2;
    private static final int VIEW_BOOKING = 3;
    private static final int VIEW_TOTAL_PAYMENT = 4;
    private static final int QUIT_MAIN = 5;

    /**
     * Displays the main menu for admin and handles user input to perform actions.
     *
     * @param concerts List of concerts.
     * @param venues   Map of venue names to Venue objects.
     * @param bookings List of bookings.
     * @param id       optional IDs.
     */
    @Override
    public void mainMenu(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings,
            int... id) {
        boolean runLoop = true;
        int option;
        while (runLoop) {
            this.printMenu();
            option = scanner.nextInt();
            switch (option) {
                case SHOW_TIMING:
                    super.showTimings(concerts, venues, bookings);
                    break;
                case UPDATE_TICKET_COST:
                    int concertId = selectConcert(concerts, venues, bookings);
                    if (concertId != 0)
                        this.updateTicket(concerts.get(concertId - 1));
                    break;
                case VIEW_BOOKING:
                    concertId = selectConcert(concerts, venues, bookings);
                    if (concertId != 0)
                        super.showBooking(concerts.get(concertId - 1), super.findBooking(bookings, concertId + ""));
                    break;
                case VIEW_TOTAL_PAYMENT:
                    concertId = selectConcert(concerts, venues, bookings);
                    if (concertId != 0)
                        showTotalPayment(super.findBooking(bookings, concertId + ""));
                    break;
                case QUIT_MAIN:
                    System.out.println("Exiting admin mode");
                    runLoop = false;
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    /**
     * Prints the admin menu.
     */
    @Override
    public void printMenu() {
        System.out.println("Select an option to get started!");
        System.out.println("Press 1 to view all the concert details");
        System.out.println("Press 2 to update the ticket costs");
        System.out.println("Press 3 to view booking details");
        System.out.println("Press 4 to view total payment received for a concert");
        System.out.println("Press 5 to exit");
        System.out.print("> ");
    }

    /**
     * Updates the ticket prices for a specific concert.
     *
     * @param concert The concert for which to update the ticket prices.
     */
    public void updateTicket(Concert concert) {
        super.showTicketCost(concert);

        System.out.print("Enter the zone : VIP, SEATING, STANDING: ");
        String zone = scanner.next();
        System.out.println();
        System.out.print("Left zone price: ");
        double leftPrice = scanner.nextDouble();
        System.out.print("Centre zone price: ");
        double centrePrice = scanner.nextDouble();
        System.out.print("Right zone price: ");
        double rightPrice = scanner.nextDouble();
        switch (zone) {
            case ("SEATING"):
                concert.SetSeatingPrice(leftPrice, centrePrice, rightPrice);
                break;
            case ("VIP"):
                concert.SetVipPrice(leftPrice, centrePrice, rightPrice);
                break;
            case ("STANDING"):
                concert.SetStandingPrice(leftPrice, centrePrice, rightPrice);
                break;
        }
    }

    /**
     * select a concert from the list of concerts.
     *
     * @param concerts List of concerts.
     * @param venues   Map of venue names to Venue objects.
     * @param bookings List of bookings.
     * @return The ID of the selected concert.
     */
    public int selectConcert(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings) {
        System.out.println("Select a concert or 0 to exit");
        super.showTimings(concerts, venues, bookings);
        System.out.print("> ");
        int concertId = scanner.nextInt();
        return concertId;
    }

    /**
     * Shows the total payment received for a specific concert.
     *
     * @param bookings List of bookings for the concert.
     */
    public void showTotalPayment(ArrayList<Booking> bookings) {
        double totalPayment = 0;
        for (Booking b : bookings) {
            for (TicketDetail td : b.getTicketDetails()) {
                totalPayment += td.getPrice();
            }
        }
        System.out.println("Total Price for this concert is AUD " + totalPayment);
    }

    /**
     * Writes the concert price to a concert file.
     *
     * @param updateConcerts List of concerts to be updated.
     * @param concertPath    Path to the concert file.
     */
    public void updateConcert(ArrayList<Concert> updateConcerts, String concertPath) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(concertPath));

            for (Concert c : updateConcerts) {
                String standingPrice = "STANDING" + ":" + c.getStandingPrice()[0] + ":" + c.getStandingPrice()[1] + ":"
                        + c.getStandingPrice()[2];
                String seatingPrice = "SEATING" + ":" + c.getSeatingPrice()[0] + ":" + c.getSeatingPrice()[1] + ":"
                        + c.getSeatingPrice()[2];
                String vipPrice = "VIP" + ":" + c.getVipPrice()[0] + ":" + c.getVipPrice()[1] + ":"
                        + c.getVipPrice()[2];
                pw.print(c.getConcertId() + "," + c.getDate() + "," + c.getTiming() + "," + c.getArtist() + ","
                        + c.getVenue() + "," + standingPrice + "," + seatingPrice + "," + vipPrice);
                pw.println();
                pw.flush();
            }
        } catch (FileNotFoundException e) {
            System.err.println(concertPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(concertPath + "(No such file or directory)");
        }
        pw.close();
    }
}
