package concert;

import java.util.ArrayList;

/**
 * class to represents layout of venue
 *
 * @author Yoshikazu Fujisaka
 */
public class Venue {

    // define what data fields will be used to define the venue layout

    private String id;
    private int numberOfRow;
    private int numberOfColumn;
    private int totalSeats;
    private ArrayList<String> layout;
    private int numberOfLeftColumn;
    private int numberOfMiddleColumn;
    private int numberOfRightColumn;
    private int rowSeating;
    private int rowVip;
    private int rowStanding;

    /**
     * Constructor to initialize a Venue object.
     *
     * @param id          The venue ID.
     * @param rowVip      The number of rows for VIP seating.
     * @param rowSeating  The number of rows for Seating.
     * @param rowStanding The number of rows for Standing.
     * @param leftCol     The number of left columns.
     * @param midCol      The number of middle columns.
     * @param rightCol    The number of right columns.
     * @param layout      The layout of the venue as a list of strings.
     */
    public Venue(String id, int rowVip, int rowSeating, int rowStanding, int leftCol, int midCol, int rightCol,
            ArrayList<String> layout) {
        this.id = id;
        this.rowVip = rowVip;
        this.rowSeating = rowSeating;
        this.rowStanding = rowStanding;
        this.numberOfRow = rowVip + rowSeating + rowStanding;
        this.numberOfLeftColumn = leftCol;
        this.numberOfMiddleColumn = midCol;
        this.numberOfRightColumn = rightCol;
        this.numberOfColumn = leftCol + midCol + rightCol;
        this.totalSeats = numberOfRow * numberOfColumn;
        this.layout = layout;

    }

    /**
     * Gets the total number of seats.
     *
     * @return The total number of seats in the venue.
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * Gets the venue ID.
     *
     * @return The venue ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the layout of the venue.
     *
     * @return The layout of the venue as a list of strings.
     */
    public ArrayList<String> getLayout() {
        return layout;
    }

    /**
     * Gets the number of left columns.
     *
     * @return The number of left columns.
     */
    public int getNumberOfLeftColumn() {
        return numberOfLeftColumn;
    }

    /**
     * Gets the number of middle columns.
     *
     * @return The number of middle columns.
     */
    public int getNumberOfMiddleColumn() {
        return numberOfMiddleColumn;
    }

    /**
     * Gets the number of right columns.
     *
     * @return The number of right columns.
     */
    public int getNumberOfRightColumn() {
        return numberOfRightColumn;
    }

}
