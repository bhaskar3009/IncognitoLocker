package view;

import java.sql.SQLException;
import java.util.List;
//import Scanner
import java.util.Scanner;
import java.io.*;
import dao.DataDAO;
import model.Data;

public class UserView {
    private String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {

        do {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            System.out.println("Enter your choice");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {

                case 1: {
                    try {
                        List<Data> files = DataDAO.getFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data f : files) {
                            System.out.println(f.getId() + " - " + f.getName());
                        }
                    } catch (SQLException e) {

                        e.printStackTrace();
                    }
                }
                    break;
                case 2: {
                    System.out.print("Enter file path:");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data d = new Data(0, f.getName(), path, email);
                    try {
                        DataDAO.hideFiles(d);
                    } catch (SQLException | IOException e) {

                        e.printStackTrace();
                    }
                }
                    break;
                case 3: {
                    List<Data> files;
                    try {
                        files = DataDAO.getFiles(this.email);

                        System.out.println("ID - File Name");
                        for (Data f : files) {
                            System.out.println(f.getId() + " - " + f.getName());
                        }
                        System.out.print("Enter the id of the file to be unhidden: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidID = false;
                        for (Data f : files) {
                            if (f.getId() == id) {
                                isValidID = true;
                                break;
                            }
                        }
                        if (isValidID) {
                            DataDAO.unhide(id);
                        } else {
                            System.out.println("Wrong ID");
                        }
                    } catch (SQLException | IOException e) {

                        e.printStackTrace();
                    }
                }
                    break;
                case 0: {
                    System.exit(0);
                }
            }

        } while (true);
    }
}
