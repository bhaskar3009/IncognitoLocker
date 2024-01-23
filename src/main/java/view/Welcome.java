package view;

import java.sql.SQLException;
import java.util.Scanner;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

public class Welcome {
    public void landingPage() {
        // java input fromm user Scanner
        Scanner sc = new Scanner(System.in);
        // welcome to File hider app
        System.out.println("Welcome to File Hider Application");
        // 3 instructions for login, signup and exit
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Signup");
        System.out.println("Press 0 to Exit");
        int ch = 0;

        // use try-catch to parse ch
        try {
            String input = sc.nextLine();

            if (!input.isEmpty()) {
                ch = Integer.parseInt(input);
            } else {
                System.out.println("Please enter a valid option.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        switch (ch) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 0:
                System.exit(0);
                break;

        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        // get otp from GenerateOTP class
        String getOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, getOTP);
        System.out.println("OTP has been sent to your email.");
        System.out.print("Enter OTP : ");
        String otp = sc.nextLine();
        if (otp.equals(getOTP)) {
            User user = new User(name, email);
            int res = UserService.addUser(user);
            switch (res) {
                case 0:
                    System.out.println("User registered!");
                    break;
                case 1:
                    System.out.println("user already existed!");
                    break;
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExist(email)) {
                System.out.println("User already exist");
                // get otp from GenerateOTP class
                String getOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, getOTP);
                System.out.println("OTP has been sent to your email.");
                System.out.print("Enter OTP : ");
                String otp = sc.nextLine();
                if (otp.equals(getOTP)) {
                    new UserView(email).home();
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
