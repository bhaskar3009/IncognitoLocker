package dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import db.MyConnection;
import model.Data;

public class DataDAO {
    public static List<Data> getFiles(String email) throws SQLException {
        Connection c = MyConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from data where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        List<Data> files = new ArrayList<>();
        while (rs.next()) {
            //based on columnindex
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String path = rs.getString(3);
            files.add(new Data(id, name, path));
        }
        return files;
    }

    public static int hideFiles(Data file) throws SQLException, IOException {
        Connection c = MyConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into data(name, path, email, bin_data) values(?, ?, ?, ?)");
        ps.setString(1, file.getName());

        //set path using ps
        ps.setString(2, file.getPath().toString()); // Assuming getPath() returns something other than String
        ps.setString(3, file.getEmail());

        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);
        ps.setCharacterStream(4, fr, f.length());
        int res=ps.executeUpdate();
        fr.close();
        f.delete();
        return res;
    }
    public static void unhide(int id) throws SQLException, IOException{
       
        Connection c = MyConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("select path, bin_data from data where id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob cb = rs.getClob("bin_data");

        Reader r = cb.getCharacterStream();

        FileWriter fw = new FileWriter(path);

        int i;

        while ((i = r.read()) != -1) {
            fw.write((char)i);
        }
        fw.close();
        //delete from db
        ps = c.prepareStatement("delete from data where id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("File unhidden sucessfully!");
    }
}
