import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import user.*;
import concert.*;
import booking.*;
import exception.*;

/**
 * Engine that manages program execution, I/O Handling and CMD Args
 *
 * @author Yoshikazu Fujisaka
 */
public class TicketManagementEngine {

    private static final String defaultPath = "assets/venue_default.txt";
    private static final Scanner KEYBOARD = User.scanner;
    private static final int EXIT = 0;
    private static final char VIP = 'V';
    private static final char SEATING = 'S';
    private static final char STANDING = 'T';
    private static final int BRACKET_LENGTH = 3;
    private static final String CUSTOMER_MODE = "--customer";
    private static final String ADMIN_MODE = "--admin";
    private static final int MINIMUM_CONCERT_LENGTH = 8;
    private static final int MINIMUM_BOOKING_LENGTH = 10;
    private static final int MINIMUM_CUSTOMER_LENGTH = 3;

    /**
     * Main method that starts the application.
     *
     * @param args Command line arguments for user mode and file paths.
     */
    public static void main(String[] args) {

        TicketManagementEngine tme = new TicketManagementEngine();

        // handle the args to extract user mode and file names
        String mode = args[0];
        String customerId = null;
        String password = null;
        ArrayList<String> filePaths = new ArrayList<String>();
        ArrayList<String> venuePaths = new ArrayList<String>();
        ArrayList<Concert> concerts = new ArrayList<Concert>();
        HashMap<String, Venue> venues = new HashMap<String, Venue>();
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        Admin admin = null;
        Customer customer = null;
        String customerPath = null;
        String concertPath = null;
        String bookingPath = null;

        if (mode.equals(CUSTOMER_MODE)) {
            int index = 1;
            if (tme.isNumeric(args[index])) {
                customerId = args[index++];
                password = args[index++];
            }
            while (index < args.length) {
                filePaths.add(args[index]);
                index++;
            }
            customerPath = filePaths.get(0);
            concertPath = filePaths.get(1);
            bookingPath = filePaths.get(2);
            try {
                for (int i = 3; i < filePaths.size(); i++) {
                    venuePaths.add(filePaths.get(i));
                }
                if (customerId == null) { // if id and password is missing
                    customer = tme.createNewCustomer(customerPath);
                    tme.writeToCustomerFile(customer, customerPath);
                } else {
                    customer = tme.loadCustomer(customerId, password, customerPath);
                }
                if (customer != null) {
                    // load the data
                    concerts = tme.loadConcerts(concertPath);
                    bookings = tme.loadBookings(bookingPath);
                    venues = tme.loadVenues(concerts, venuePaths, tme);

                    tme.displayMessage(mode, customer.getName());

                    // run the user menu
                    boolean runLoop = true;
                    while (runLoop) {
                        System.out.println("Select a concert or 0 to exit");
                        customer.showTimings(concerts, venues, bookings);
                        System.out.print("> ");
                        int concertId = KEYBOARD.nextInt();
                        if (concertId == EXIT) {
                            runLoop = false;
                            System.out.println("Exiting customer mode");
                            // save the booking/customer/concert data back to files
                            customer.writeToBookingFile(bookings, bookingPath);
                        } else {
                            customer.mainMenu(concerts, venues, bookings, concertId);

                        }
                    }
                }
            } catch (IncorrectPasswordException e) {
                System.out.println(e.getMessage());
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (mode.equals(ADMIN_MODE)) {
            for (int i = 1; i < args.length; i++) {
                filePaths.add(args[i]);
            }
            customerPath = filePaths.get(0);
            concertPath = filePaths.get(1);
            bookingPath = filePaths.get(2);
            try {
                tme.isExist(customerPath);
                for (int i = 3; i < filePaths.size(); i++) {
                    venuePaths.add(filePaths.get(i));
                }
                // load the data
                concerts = tme.loadConcerts(concertPath);
                bookings = tme.loadBookings(bookingPath);
                venues = tme.loadVenues(concerts, venuePaths, tme);

                tme.displayMessage(mode); // admin dose not have name.

                // run the user menu
                admin = new Admin();
                admin.mainMenu(concerts, venues, bookings);
                // save the booking/customer/concert data back to files
                admin.updateConcert(concerts, concertPath);
            } catch (FileNotFoundException e) {
                System.out.println(customerPath + " (No such file or directory)");
            }
        } else {
            System.out.println("Invalid user mode. Terminating program now.");
        }
    }

    /**
     * Displays a welcome message based on the mode.
     *
     * @param mode The mode of the user (customer or admin)
     * @param name Customer name (if applicable)
     */
    public void displayMessage(String mode, String... name) {
        if (mode.equals(CUSTOMER_MODE)) {
            System.out.println("Welcome " + name[0] + " to Ticket Management System");
        } else if (mode.equals(ADMIN_MODE)) {
            System.out.println("Welcome to Ticket Management System Admin Mode.");
        } else {
            System.out.println("Invalid user mode. Terminating program now.");
        }
        System.out.print("\n" +
                " ________  ___ _____ \n" +
                "|_   _|  \\/  |/  ___|\n" +
                "  | | | .  . |\\ `--. \n" +
                "  | | | |\\/| | `--. \\\n" +
                "  | | | |  | |/\\__/ /\n" +
                "  \\_/ \\_|  |_/\\____/ \n" +
                "                    \n" +
                "                    \n");
    }

    /**
     * Checks if a file exists.
     *
     * @param customerPath The path to the customer file.
     * @throws FileNotFoundException If the file does not exist.
     */
    public void isExist(String customerPath) throws FileNotFoundException {
        new FileReader(customerPath);
    }

    /**
     * Loads a customer from a file based on their ID and password.
     *
     * @param customerId   The customer's ID.
     * @param password     The customer's password.
     * @param customerPath The path to the customer file.
     * @return The loaded customer object.
     * @throws IncorrectPasswordException If the password is incorrect.
     * @throws NotFoundException          If the customer is not found.
     */
    public Customer loadCustomer(String customerId, String password, String customerPath)
            throws IncorrectPasswordException, NotFoundException {
        BufferedReader br = null;
        try {
            // find corresponding and candidate(maxId+1) concertId
            br = new BufferedReader(new FileReader(customerPath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                if (info.length < MINIMUM_CUSTOMER_LENGTH) {
                    try {
                        throw new InvalidLineException("Invalid Customer Files. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                if (!isNumeric(info[0])) {
                    try {
                        throw new InvalidLineException("Customer Id is in incorrect format. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                if (info[0].equals(customerId)) {
                    if (info[2].equals(password)) {
                        String name = info[1];
                        return new Customer(customerId, name, password);
                    } else {
                        throw new IncorrectPasswordException("Incorrect Password. Terminating Program");
                    }
                }
            }
            throw new NotFoundException("Customer does not exist. Terminating Program");
        } catch (FileNotFoundException e) {
            System.out.println(customerPath + " (No such file or directory)");
        } catch (IOException e) {
            System.out.println(customerPath + " (No such file or directory)");
        }
        return null;
    }

    /**
     * Creates a new customer and adds them to the customer file.
     *
     * @param customerPath The path to the customer file.
     * @return New customer object.
     */
    public Customer createNewCustomer(String customerPath) {
        BufferedReader br = null;
        int nextId = 1;
        String name = null;
        String password = null;
        String line;
        try {
            // find corresponding and candidate(maxId+1) concertId
            br = new BufferedReader(new FileReader(customerPath));
            while ((line = br.readLine()) != null) {
                nextId++;
            }
            System.out.print("Enter your name: ");
            name = KEYBOARD.nextLine();
            System.out.print("Enter your password: ");
            password = KEYBOARD.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println(customerPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(customerPath + "(No such file or directory)");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Customer(nextId + "", name, password);
    }

    /**
     * Checks if a string is numeric.
     *
     * @param s The string to check.
     * @return True if the string is numeric.
     */
    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Loads concerts from a CSV file.
     *
     * @param concertCsvPath The path to the concert CSV file.
     * @return A list of concerts.
     */
    public ArrayList<Concert> loadConcerts(String concertCsvPath) {
        BufferedReader br = null;
        ArrayList<Concert> concerts = new ArrayList<Concert>();
        try {
            br = new BufferedReader(new FileReader(concertCsvPath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < MINIMUM_CONCERT_LENGTH) {
                    try {
                        throw new InvalidLineException("Invalid Concert Files. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                if (!isNumeric(parts[0])) {
                    try {
                        throw new InvalidLineException("Concert Id is in incorrect format. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                concerts.add(new Concert(line));
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println(concertCsvPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(concertCsvPath + "(No such file or directory)");
        }
        return concerts;
    }

    /**
     * Loads venues from a txt file.
     *
     * @param concerts   A list of concerts.
     * @param venuePaths A list of venue file paths.
     * @param tme        An instance of TicketManagementEngine.
     * @return A map of concert IDs to venues.
     */
    public HashMap<String, Venue> loadVenues(ArrayList<Concert> concerts, ArrayList<String> venuePaths,
            TicketManagementEngine tme) {
        HashMap<String, Venue> venues = new HashMap<String, Venue>();

        for (int i = 0; i < concerts.size(); i++) {
            boolean found = false;
            String venue = concerts.get(i).getVenue();
            for (String venuePath : venuePaths) {
                int index = venuePath.indexOf("_");
                String venueOnPath = venuePath.substring(index + 1, venuePath.length() - ".txt".length());
                if ((venue.equals(venueOnPath.toUpperCase()))) {
                    venues.put(concerts.get(i).getConcertId(),
                            tme.loadVenueHelper(venuePath, concerts.get(i).getConcertId()));
                    found = true;
                }
            }
            if (found == false) {
                venues.put(concerts.get(i).getConcertId(),
                        tme.loadVenueHelper(defaultPath, concerts.get(i).getConcertId()));
            }
        }
        return venues;
    }

    /**
     * Helper method to load a venue to open and read a file.
     *
     * @param venuePath The path to the venue file.
     * @param id        The concert ID.
     * @return The loaded venue.
     */
    public Venue loadVenueHelper(String venuePath, String id) {
        ArrayList<String> layout = new ArrayList<String>();
        BufferedReader br = null;
        int rowVip = 0;
        int rowSeating = 0;
        int rowStanding = 0;
        int numCol = 0;
        int leftCol = 0;
        int rightCol = 0;
        int midCol = 0;
        boolean checked = false;
        boolean isLeftColChecked = false;
        boolean isMidColChecked = false;
        boolean isRightColChecked = false;

        try {
            br = new BufferedReader(new FileReader(venuePath));
            String line;
            while ((line = br.readLine()) != null) {
                layout.add(line);
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (!checked) {
                    String[] parts = line.split(" ");
                    leftCol = parts[1].length() / BRACKET_LENGTH; // equals 3 to reduce length of "[]"
                    midCol = parts[2].length() / BRACKET_LENGTH;
                    rightCol = parts[3].length() / BRACKET_LENGTH;
                    checked = true;
                }

                if (line.charAt(0) == VIP) {
                    rowVip++;
                } else if (line.charAt(0) == SEATING) {
                    rowSeating++;
                } else if (line.charAt(0) == STANDING) {
                    rowStanding++;
                } else {
                    try {
                        throw new InvalidLineException("Invalid Zone Type. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println(venuePath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(venuePath + "(No such file or directory)");
        }
        Venue venue = new Venue(id, rowVip, rowSeating, rowStanding, leftCol, midCol, rightCol, layout);
        return venue;

    }

    /**
     * Loads bookings from a CSV file.
     *
     * @param bookingPath The path to the booking file.
     * @return A list of bookings.
     */
    public ArrayList<Booking> loadBookings(String bookingPath) {
        ArrayList<Booking> bookings = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(bookingPath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < MINIMUM_BOOKING_LENGTH) {
                    try {
                        throw new InvalidLineException("Invalid booking Files. Skipping this line.");
                    } catch (InvalidLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                if (!isNumeric(data[0])) {
                    try {
                        throw new InvalidFormatException("Booking Id is in incorrect format. Skipping this line.");
                    } catch (InvalidFormatException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                if (!isNumeric(data[4]) || Integer.parseInt(data[4]) == 0) {
                    try {
                        throw new InvalidFormatException("Incorrect Number of Tickets. Skipping this line.");
                    } catch (InvalidFormatException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                String bookingId = data[0];
                String customerId = data[1];
                String customerName = data[2];
                String concertId = data[3];
                int totalTickets = Integer.parseInt(data[4]);

                Booking booking = new Booking(bookingId, customerId, customerName, concertId, totalTickets);

                int index = 5;
                for (int i = 0; i < totalTickets; i++) {
                    String ticketId = data[index];
                    String rowNumber = data[index + 1];
                    String seatNumber = data[index + 2];
                    String zoneType = data[index + 3];
                    double price = Double.parseDouble(data[index + 4]);
                    booking.addTicketDetail(ticketId, rowNumber, seatNumber, zoneType, price);
                    index += 5;
                }
                bookings.add(booking);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println(bookingPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(bookingPath + "(No such file or directory)");
        }

        return bookings;
    }

    /**
     * Writes a new customer to the customer file.
     *
     * @param customer     The customer to write.
     * @param customerPath The path to the customer file.
     */
    public void writeToCustomerFile(Customer customer, String customerPath) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(customerPath, true));
            pw.println(customer.getCustomerId() + "," + customer.getName() + "," + customer.getPassword());
            pw.flush();
        } catch (FileNotFoundException e) {
            System.err.println(customerPath + "(No such file or directory)");
        } catch (IOException e) {
            System.err.println(customerPath + "(No such file or directory)");
        }
        pw.close();
    }
}
