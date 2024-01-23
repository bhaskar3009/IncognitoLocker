package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.MyConnection;
import model.User;

public class UserDAO {
    public static boolean isExist(String email) throws SQLException {
        Connection c = MyConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getString("email").equals(email)) {
                return true;
            }
        }
        return false;
    }

    // if users doesn't exist
    public static int addUser(User user) throws SQLException {
        Connection c = MyConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users values(default,?,?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();
    }

}
