package concert;

/**
 * Class that stores concert details including the ID, date, timing, artist,
 * venue,
 *
 * @author Yoshikazu Fujisaka
 */
public class Concert {

    private String concertId;
    private String date;
    private String timing;
    private String artist;
    private String venue;

    private double[] standingPrice = new double[3];
    private double[] seatingPrice = new double[3];
    private double[] vipPrice = new double[3];

    /**
     * Constructor that initializes a concert object with details.
     *
     * @param info A string containing concert details, separated by commas.
     */
    public Concert(String info) {
        String[] details = info.split(",");
        this.concertId = details[0];
        this.date = details[1];
        this.timing = details[2];
        this.artist = details[3];
        this.venue = details[4];
        this.standingPrice = convertToDoubleArray(details[5].split(":"));
        this.seatingPrice = convertToDoubleArray(details[6].split(":"));
        this.vipPrice = convertToDoubleArray(details[7].split(":"));
    }

    /**
     * Converts an array of strings to an array of doubles.
     *
     * @param strings An array of strings representing prices.
     * @return An array of doubles with parsed prices.
     */
    public static double[] convertToDoubleArray(String[] strings) {
        double leftPrice = Double.parseDouble(strings[1]);
        double middlePrice = Double.parseDouble(strings[2]);
        double rightPrice = Double.parseDouble(strings[3]);
        return new double[] { leftPrice, middlePrice, rightPrice };
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
     * Gets the concert date.
     *
     * @return The concert date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the concert timing.
     *
     * @return The concert timing.
     */
    public String getTiming() {
        return timing;
    }

    /**
     * Gets the artist name.
     *
     * @return The artist name.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the venue name.
     *
     * @return The venue name.
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Gets the seating prices.
     *
     * @return An array containing the seating prices for left, middle, and right
     *         seats.
     */
    public double[] getSeatingPrice() {
        return seatingPrice;
    }

    /**
     * Gets the standing prices.
     *
     * @return An array containing the standing prices for left, middle, and right
     *         seats.
     */
    public double[] getStandingPrice() {
        return standingPrice;
    }

    /**
     * Gets the VIP prices.
     *
     * @return An array containing the VIP prices for left, middle, and right seats.
     */
    public double[] getVipPrice() {
        return vipPrice;
    }

    /**
     * Sets the seating prices.
     *
     * @param leftPrice  The price for left seats.
     * @param midPrice   The price for middle seats.
     * @param rightPrice The price for right seats.
     */
    public void SetSeatingPrice(double leftPrice, double midPrice, double rightPrice) {
        seatingPrice[0] = leftPrice;
        seatingPrice[1] = midPrice;
        seatingPrice[2] = rightPrice;
    }

    /**
     * Sets the standing prices.
     *
     * @param leftPrice  The price for left standing area.
     * @param midPrice   The price for middle standing area.
     * @param rightPrice The price for right standing area.
     */
    public void SetStandingPrice(double leftPrice, double midPrice, double rightPrice) {
        standingPrice[0] = leftPrice;
        standingPrice[1] = midPrice;
        standingPrice[2] = rightPrice;
    }

    /**
     * Sets the VIP prices.
     *
     * @param leftPrice  The price for left VIP seats.
     * @param midPrice   The price for middle VIP seats.
     * @param rightPrice The price for right VIP seats.
     */
    public void SetVipPrice(double leftPrice, double midPrice, double rightPrice) {
        vipPrice[0] = leftPrice;
        vipPrice[1] = midPrice;
        vipPrice[2] = rightPrice;
    }

}
