import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("""
                                ___________________________________________
                                Welcome to the Plane Management application
                                ___________________________________________
                            """);
        initializeSeats();
        deleteAllFilesAtBegin();
        displayMenu();
    }
    static Ticket[][] tickets = new Ticket[4][];// 2D array call ticket to store ticket information
    static int[][] seatStructure = new int[4][];// 2D array to store and update seat availability
    private static void initializeSeats() {
        // initialize each row in jagged 2D arrays that hold ticket details and seat availability
        for (int i = 0; i < 4; i++) {
            seatStructure[i] = new int[seatPerRow[i]];
            tickets[i] = new Ticket[seatPerRow[i]];
        }
    }
    static int[] seatPerRow = {14,12,12,14};// helps to initialize seat in jagged array
    static char[] rows = {'A','B','C','D'};// to validate row letter
    static double totalPrice = 0;// variable to store total sales
    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        // display menu
        System.out.print("""
                \n*****************************************************
                *                 MENU OPTIONS                      *
                *****************************************************
                   01. Buy a Seat
                   02. Cancel a Seat
                   03. Find First Available Seat
                   04. Show Seat Planning
                   05. Print ticket information and total sales
                   06. Search ticket
                   00. Quit
                *****************************************************
                """);

        int option = 0;
        boolean isValidInput = false;

        while (!isValidInput) {// ask input from user until entered correct option
            try {
                System.out.print("Please select an option : ");
                option = scanner.nextInt();

                if (option >= 0 && option <= 6) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid option Please Try Again !");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
        switch (option) {// switch case to call right method
            case 1 -> buySeat();
            case 2 -> cancelSeat();
            case 3 -> find_first_available();
            case 4 -> show_seating_plan();
            case 5 -> printTicketInfo();
            case 6 -> searchTicket();
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Please select again.");
        }
        displayMenu();
    }
    public static void buySeat() {
        Ticket ticket = new Ticket();// create reference variable to access Ticket class
        String rowLetter = ticket.getRowLetter();
        int seatNumber = ticket.getSeatNumber();

        // convert unicode to access rows in seatStructure 2D array
        char row = Character.toUpperCase(rowLetter.charAt(0));
        int rowArrayIndex = row - 'A';

        if (seatStructure[rowArrayIndex][seatNumber - 1] == 0) {// check availability of seat
            seatStructure[rowArrayIndex][seatNumber - 1] = 1;

            Person person = new Person();// Create reference variable to access Person class
            System.out.print("\nSeat number " + seatNumber + " in Row " + rowLetter + " is received for ");
            person.personInfo();

            ticket.setRowLetter(rowLetter);
            ticket.setSeatNumber(seatNumber);
            ticket.setPerson(person);

            totalPrice += ticket.getPriceTag();//Update total price when ticket has been sold

            tickets[rowArrayIndex][seatNumber - 1] = ticket;//store ticket details in ticket Array

            ticket.save();// Save a text file when a seat is booked

        } else {
            System.out.println("Seat in a Row " + rowLetter + ", the seat number " + seatNumber + " is already taken.");
        }
    }
    public static void cancelSeat(){
        Ticket ticket = new Ticket();// create reference variable to access Ticket class
        String rowLetter = ticket.getRowLetter();
        int seatNumber = ticket.getSeatNumber();

        // convert unicode to access rows in seatStructure 2D array
        char row = Character.toUpperCase(rowLetter.charAt(0));
        int rowArrayIndex = row - 'A';

        if (seatStructure[rowArrayIndex][seatNumber-1]==1) {// check availability of seat
            seatStructure[rowArrayIndex][seatNumber-1]=0;

            System.out.println("You have successfully canceled Seat number " + seatNumber + " in row " + rowLetter );

            tickets[rowArrayIndex][seatNumber-1]= null;//Update ticket details in ticket Array to null

            totalPrice-= ticket.getPriceTag();//Update total price when ticket has been canceled

            ticket.delFile();// Delete a text file when a seat is canceled
        } else {
            System.out.println("Seat in a Row " + rowLetter + ",the  seat number " + seatNumber + " is available at the moment.");
        }
    }
    public static void show_seating_plan() {// Show seaplane according to seatStructure
        System.out.println("\nSeat Plan");
        for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i] + " : ");
            for (int seat : seatStructure[i]) {
                System.out.print(seat == 0 ? "O " : "X ");// display O for 0 and X for 1 in seatStructure
            }
            System.out.println();
        }
    }
    public static void printTicketInfo() {// print every ticket information
        for (Ticket[] rowTickets : tickets) {
            for (Ticket ticket : rowTickets) {
                if (ticket != null) {// ignore null ticket in ticket array
                    ticket.ticketInfo();
                }
            }
        }
        System.out.println("Total Price is :" +totalPrice);// display updated total price
    }
    public static void searchTicket() {
        String rowLetter = Ticket.validateRow();
        int seatNumber = Ticket.setValidSeatNum(rowLetter);

        // convert unicode to access rows in seatStructure 2D array
        char row = Character.toUpperCase(rowLetter.charAt(0));
        int rowArrayIndex = row - 'A';

        Ticket ticket = tickets[rowArrayIndex][seatNumber - 1];

        if (ticket != null) {
            ticket.ticketInfo();// display details of ticket
        } else {
            System.out.println("Seat " + seatNumber + " in Row " + rowLetter + " is available at the moment.");
        }
    }
    public static void find_first_available() {// find first available seat using linear search
        for (int i = 0; i < rows.length; i++) {
            char row = rows[i];
            int[] seatRow = seatStructure[i];
            for (int j = 0; j < seatRow.length; j++) {
                if (seatRow[j] == 0) {
                    System.out.println("First available seat in row " + row + " is seat number " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }
    public static void deleteAllFilesAtBegin() {// delete all text file when program is start
        for (char j : rows) {
            for (int k = 0; k < seatStructure[0].length; k++) {
                if (j == 'B' || j == 'C') {
                    if (k == 12 || k == 13) {
                        continue;
                    }
                }
                String filename = (j + String.valueOf(k + 1));// generate all text files can be existed

                File file = new File(filename + ".txt");
                if (file.exists()) {// check whether file is existing
                    file.delete();// delete file
                }
            }
        }
    }

}
