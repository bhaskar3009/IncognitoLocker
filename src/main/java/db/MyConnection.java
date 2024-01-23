package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection=null;
    public static Connection getConnection(){
        try {

            //load class
            Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filehider?allowPublicKeyRetrieval=true&useSSL=false", "root", "*#BDChavan@2001#*");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("DB connected successfully!");
        return connection;
    }

    public static void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
