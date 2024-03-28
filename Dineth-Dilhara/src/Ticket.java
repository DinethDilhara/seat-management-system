import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Ticket {
    private String rowLetter;
    private int seatNumber;
    private double priceTag;
    private Person person;
    public Ticket() {// Create a Constructor
        this.rowLetter = validateRow();
        this.seatNumber = setValidSeatNum(rowLetter);
        this.priceTag = setPriceTag(seatNumber);
        this.person = null;
    }
    // create getters and setters to access properties of person class
    public void setPerson(Person person){
        this.person = person;
    }
    public void setRowLetter(String rowLetter) {
        this.rowLetter = rowLetter;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
        this.priceTag = setPriceTag(seatNumber);

    }
    private double setPriceTag(int seatNumber) {// select price according to seat number
        if (seatNumber >= 1 && seatNumber <= 5) {
            return 200.00;
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150.00;
        } else {
            return 180.00;
        }
    }
    public String getRowLetter() {
        return rowLetter;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public double getPriceTag() {
        return priceTag;
    }
    public Person getPerson() {
        return person;
    }
    public static String validateRow() {// check validation of row letter
        Scanner scanner = new Scanner(System.in);
        String rowLetter;

        while (true) {
            System.out.print("Enter row (A, B, C, D): ");
            rowLetter = scanner.next().toUpperCase();

            if (rowLetter.matches("[A-D]")) {
                return rowLetter;
            } else {
                System.out.println("Invalid Row , Please try again !");
            }
        }
    }
    public static int setValidSeatNum(String rowLetter) {// check validation of seat number
        boolean isNum = false;
        int seatNumber = 0;

        while (!isNum) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Seat Number: ");
            try {
                seatNumber = scanner.nextInt();
                if (rowLetter.equals("A") || rowLetter.equals("D")) {
                    if (seatNumber >= 1 && seatNumber <= Main.seatStructure[0].length) {
                        isNum = true;
                    } else {
                        System.out.println("Invalid seat number, Please try again !");
                    }
                } else {
                    if (seatNumber >= 1 && seatNumber <= Main.seatStructure[1].length) {
                        isNum = true;
                    } else {
                        System.out.println("Invalid seat number, Please try again !");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine();
            }
        }
        return seatNumber;
    }
    public void ticketInfo() { // display ticket information details
        String info = """
        Ticket Information:
        -------------------
        Row Letter: %s
        Seat Number: %d
        Price Tag: $%.2f
        Person Information:
        Name: %s
        Surname: %s
        Email: %s
        -------------------
        """;

        System.out.printf(info, getRowLetter(), getSeatNumber(), getPriceTag(),
                getPerson().getName(), getPerson().getSurName(), getPerson().getMail());
    }
    public void save() { // save a text file with ticket details
        try{
            FileWriter file = new FileWriter(getRowLetter()+getSeatNumber()+".txt");
            file.write("Ticket information:\n");
            file.write("Row "+getRowLetter()+"\n");
            file.write("Seat "+getSeatNumber()+"\n");
            file.write("Price £"+getPriceTag()+"\n\n");

            file.write("Person information:\n");
            file.write("Name "+getPerson().getName()+"\n");
            file.write("Surname "+getPerson().getSurName()+"\n");
            file.write("Email "+getPerson().getMail()+"\n");
            file.close();

        }
        catch (IOException e){
            System.out.println("Error while writing to a file");
        }
    }
    public void delFile(){// delete file when cancel a ticket
        File file = new File(getRowLetter()+getSeatNumber()+".txt");
        file.delete();
    }


}
