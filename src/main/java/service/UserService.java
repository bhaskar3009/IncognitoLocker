package service;

import java.sql.SQLException;

import dao.UserDAO;
import model.User;
//user eexits krta hai ya nhi
public class UserService {
    //insert ho raha hai ya nhi
    public static Integer addUser(User user){
        try{
            if(UserDAO.isExist(user.getEmail())){
                return 1;
            }
            else{
                UserDAO.addUser(user);
                return 0;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
