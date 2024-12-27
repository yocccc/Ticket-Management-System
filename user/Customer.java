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
 * Customer class that extends the User class and implements customer actions.
 *
 * @author Yoshikazu Fujisaka
 */
public class Customer extends User {
    private String password;
    private String customerId;
    private String name;

    private static final int TICKET_COST = 1;
    private static final int VIEW_LAYOUT = 2;
    private static final int BOOK_SEATS = 3;
    private static final int BOOK_Details = 4;
    private static final int QUIT_MAIN = 5;
    private static final char VIP = 'V';
    private static final char SEATING = 'S';
    private static final char STANDING = 'T';

    public Customer(String customerId, String name, String password) {
        this.name = name;
        this.customerId = customerId;
        this.password = password;
    }

    /**
     * Displays the main menu for the customer and handles customer input.
     *
     * @param concerts A list of concerts.
     * @param venues   A map of concert IDs to their corresponding venues.
     * @param bookings A list of bookings.
     * @param id       The ID of the concert.
     */
    @Override
    public void mainMenu(ArrayList<Concert> concerts, HashMap<String, Venue> venues, ArrayList<Booking> bookings,
            int... id) {
        int concertId = id[0];
        Concert concert = concerts.get(concertId - 1);
        Venue venue = venues.get(concertId + "");
        boolean runLoop = true;
        int option;
        while (runLoop) {
            this.printMenu();
            option = scanner.nextInt();
            switch (option) {
                case TICKET_COST:
                    super.showTicketCost(concert);
                    break;
                case VIEW_LAYOUT:
                    showVenueLayout(venue, bookings, concertId + "");
                    break;
                case BOOK_SEATS:
                    showVenueLayout(venue, bookings, concertId + "");
                    System.out.print("Enter the aisle number: ");
                    String aisle = scanner.next();
                    System.out.print("Enter the seat number: ");
                    String seatNum = scanner.next();
                    System.out.print("Enter the number of seats to be booked: ");
                    int numTickets = scanner.nextInt();
                    bookings.add(this.selectSeat(concert, venue, bookings, aisle, seatNum, numTickets));
                    break;
                case BOOK_Details:
                    ArrayList<Booking> customerBooking = findBookingByCustomer(bookings, customerId, concertId + "");
                    super.showBooking(concerts.get(concertId - 1), customerBooking);
                    break;
                case QUIT_MAIN:
                    System.out.println("Exiting this concert");
                    runLoop = false;
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    /**
     * Displays the menu options for the customer.
     */
    @Override
    public void printMenu() {
        System.out.println("Select an option to get started!");
        System.out.println("Press 1 to look at the ticket costs");
        System.out.println("Press 2 to view seats layout");
        System.out.println("Press 3 to book seats");
        System.out.println("Press 4 to view booking details");
        System.out.println("Press 5 to exit");
        System.out.print("> ");
    }

    /**
     * Displays the venue layout with booked seats marked.
     *
     * @param venue     The venue whose layout is to be displayed.
     * @param bookings  A list of bookings.
     * @param concertId The ID of the concert.
     */
    public void showVenueLayout(Venue venue, ArrayList<Booking> bookings, String concertId) {
        ArrayList<Booking> correspondingBookings = super.findBooking(bookings, concertId);
        int size = 0;
        ArrayList<ArrayList<TicketDetail>> allTicketDetails = new ArrayList<ArrayList<TicketDetail>>();
        for (Booking b : correspondingBookings) {
            allTicketDetails.add(b.getTicketDetails());
            size += b.getTotalTickets();
        }

        for (String eachRow : venue.getLayout()) {
            if (eachRow.trim().isEmpty()) {
                System.out.println();
                continue;
            }
            for (ArrayList<TicketDetail> allTd : allTicketDetails) {
                for (TicketDetail td : allTd) {
                    String rowNumber = td.getRowNumber();
                    String seatNumber = td.getSeatNumber();
                    String type = td.getZoneType();

                    if (type.equals("VIP")) {
                        if (eachRow.substring(0, 2).equals("V" + rowNumber)) {
                            eachRow = eachRow.replace("[" + seatNumber + "]", "[X]");
                        }
                    } else if (type.equals("SEATING")) {
                        if (eachRow.substring(0, 2).equals("S" + rowNumber)) {
                            eachRow = eachRow.replace("[" + seatNumber + "]", "[X]");
                        }
                    } else if (type.equals("STANDING")) {
                        if (eachRow.substring(0, 2).equals("T" + rowNumber)) {
                            eachRow = eachRow.replace("[" + seatNumber + "]", "[X]");
                        }
                    }
                }
            }
            System.out.println(eachRow);
        }
    }

    /**
     * Books seats for a concert and creates a new booking.
     *
     * @param concert    The concert for which seats are to be booked.
     * @param venue      The venue of the concert.
     * @param bookings   A list of existing bookings.
     * @param aisle      The aisle number.
     * @param seatNum    The starting seat number.
     * @param numTickets The number of seats to be booked.
     * @return The new booking.
     */
    public Booking selectSeat(Concert concert, Venue venue, ArrayList<Booking> bookings, String aisle, String seatNum,
            int numTickets) {
        int max = 0;
        for (Booking b : bookings) {
            int bookingId = Integer.parseInt(b.getBookingId());
            String customerId = b.getCustomerId();
            String concertId = b.getConcertId();

            if (this.customerId.equals(customerId) && concert.getConcertId().equals(concertId)) {
                if (max < bookingId)
                    max = bookingId;
            }
        }

        int nextId = max + 1;
        String[][] ticketDetail = new String[numTickets][5];

        int seatNumInteger = Integer.parseInt(seatNum);
        Booking newBooking = new Booking(nextId + "", this.customerId, this.name, concert.getConcertId(), numTickets);

        int ticketIdOffset = 1;
        for (int i = 0; i < numTickets; i++) {
            String type = "";
            double[] priceList = new double[3];
            switch (aisle.charAt(0)) {
                case (STANDING):
                    type = "STANDING";
                    priceList = concert.getStandingPrice();
                    break;
                case (SEATING):
                    type = "SEATING";
                    priceList = concert.getSeatingPrice();
                    break;
                case (VIP):
                    type = "VIP";
                    priceList = concert.getVipPrice();
                    break;
            }
            double price = 0;
            if (seatNumInteger + i <= venue.getNumberOfLeftColumn()) {
                price = priceList[0];
            } else if (seatNumInteger + i <= venue.getNumberOfLeftColumn() + venue.getNumberOfMiddleColumn()) {
                price = priceList[1];
            } else {
                price = priceList[2];
            }

            newBooking.addTicketDetail(ticketIdOffset + i + "", aisle.charAt(1) + "", (seatNumInteger + i) + "", type,
                    price);
        }

        return newBooking;
    }

    /**
     * Finds bookings by a specific customer for a specific concert.
     *
     * @param bookings   A list of bookings.
     * @param customerId The ID of the customer.
     * @param concertId  The ID of the concert.
     * @return A list of bookings by the customer for the concert.
     */
    public ArrayList<Booking> findBookingByCustomer(ArrayList<Booking> bookings, String customerId, String concertId) {
        ArrayList<Booking> bookingByCustomer = new ArrayList<Booking>();

        for (Booking b : bookings) {
            if (b.getCustomerId().equals(customerId) && b.getConcertId().equals(concertId)) {
                bookingByCustomer.add(b);
            }
        }
        return bookingByCustomer;
    }

    /**
     * Writes the booking data to a file.
     *
     * @param bookings    A list of bookings.
     * @param bookingPath The path to the booking file.
     */
    public void writeToBookingFile(ArrayList<Booking> bookings, String bookingPath) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(bookingPath));
            for (Booking b : bookings) {
                String bookingId = b.getBookingId();
                String customerId = b.getCustomerId();
                String customerName = b.getCustomerName();
                String concertId = b.getConcertId();
                int totalTickets = b.getTotalTickets();
                ArrayList<TicketDetail> ticketDetail = b.getTicketDetails();
                pw.print(bookingId + "," + customerId + "," + customerName + "," + concertId + "," + totalTickets);
                for (int i = 0; i < totalTickets; i++) {
                    pw.print(",");
                    pw.print(ticketDetail.get(i).getTicketId());
                    pw.print(",");
                    pw.print(ticketDetail.get(i).getRowNumber());
                    pw.print(",");
                    pw.print(ticketDetail.get(i).getSeatNumber());
                    pw.print(",");
                    pw.print(ticketDetail.get(i).getZoneType());
                    pw.print(",");
                    pw.print((int) ticketDetail.get(i).getPrice());
                }
                pw.println();
                pw.flush();
            }
        } catch (FileNotFoundException e) {
            System.err.println(bookingPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(bookingPath + "(No such file or directory)");
        }
        pw.close();
    }

    /**
     * Gets the customer's name.
     *
     * @return The customer's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the customer's password.
     *
     * @return The customer's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the customer's ID.
     *
     * @return The customer's ID.
     */
    public String getCustomerId() {
        return this.customerId;
    }
}
