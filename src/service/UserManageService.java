package service;

import bean.User;
import dao.IUserDAO;
import dao.impl.UserDAO;

public class UserManageService {
    private static UserManageService instance=new UserManageService();

    private UserManageService() {
    }

    public static UserManageService getInstance(){
        return instance;
    }

    public User login(int id,String password){
        User bean=new User(id,password);
        IUserDAO dao= UserDAO.getInstance();
        return dao.login(bean);
    }

    public User register(String name,String password){
        User bean=new User(name,password);
        IUserDAO dao=UserDAO.getInstance();
        return dao.register(bean);
    }


}
