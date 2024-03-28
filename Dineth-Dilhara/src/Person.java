import java.util.Scanner;
public class Person {
    private String name;
    private String surName;
    private String mail;
    // Create a Constructor
    public Person(){
        this.setName();
        this.setSurName();
        this.setMail();
    }
    // create getters and setters to access properties of person class
    private void setName() {
        this.name = capitalizeInput(askInput("First Name"));
    }
    public String getName() {
        return name;
    }
    private void setSurName() {
        this.surName = capitalizeInput(askInput("Surname"));
    }
    public String getSurName() {
        return surName;
    }
    private void setMail() {
        this.mail = validateEmail(askInput("Email Address"));
    }
    public String getMail() {
        return mail;
    }
    public void personInfo() {// to display person information
        System.out.printf("%s %s (%s)%n", getName(), getSurName(), getMail());
    }
    private String capitalizeInput(String input){// change input name surname first letter to Uppercase
        if (input.isEmpty()){
            return input;
        } else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
    }
    private String askInput(String field) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter " + field + ": ");
        return scanner.next();
    }
    private String validateEmail(String email) {// use regex expressions for email validation
        if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return email;
        } else {
            System.out.println("Invalid email address. Please enter again.");
            return validateEmail(askInput("Email Address"));
        }
    }
}
