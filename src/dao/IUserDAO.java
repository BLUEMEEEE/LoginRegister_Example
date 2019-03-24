package dao;

import bean.User;

public interface IUserDAO {
    public User login(User bean);
    public User register(User bean);
    public int updateName(User bean);
    public int updatePw(User bean);
}
