package app;

public class TicketCalculator {

    public static String getTicketPrice(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        } else if (age < 18) {
            return "Price: $5";
        } else if (age <= 59) {
            return "Price: $10";
        } else {
            return "Price: $6";
        }
    }
}