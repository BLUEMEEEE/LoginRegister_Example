package dao.impl;

import bean.User;
import dao.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends BaseDao implements dao.IUserDAO {
    private static UserDAO instance=new UserDAO();
    private UserDAO(){}
    public static UserDAO getInstance(){
        return instance;
    }
    @Override
    public User login(User bean) {
        String query="select * from user where id = ?";
        ArrayList<Object> parameters=new ArrayList<Object>();
        parameters.add(bean.getId());
        ResultSet rs= super.executeQuery(query,parameters);
        try{
            while(rs.next()){//遍历时，判断是否有下一个结果
                String pw=rs.getString(3);//里面的数字代表第几个属性，从1开始记
                if(pw.equals(bean.getPassword())){
                    bean.setName(rs.getString(2));
                    return bean;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();//别忘了
        return null;
    }

    @Override
    public User register(User bean) {
        String queryForInsert="insert into user(userName,pw) values(?,?)";//不用引号？？？？
        ArrayList<Object> parametersForInsert=new ArrayList<Object>();
        parametersForInsert.add(bean.getName());
        parametersForInsert.add(bean.getPassword());
        int result=executeUpdate(queryForInsert,parametersForInsert);
        if(result==0){
            super.close();
            return null;
        }
        else{
            String queryForSelect="SELECT LAST_INSERT_ID()";
            ResultSet rs=executeQuery(queryForSelect,null);
            try{
                while(rs.next()){//遍历时，判断是否有下一个结果
                    int id=rs.getInt(1);//里面的数字代表第几个属性，从1开始记
                    bean.setId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                conn.commit();
                super.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return bean;
        }
    }

    //TODO:待调试
    @Override
    public int updateName(User bean) {
        int result=0;
        String query="update user set username=? where id=?";
        ArrayList<Object> parameters=new ArrayList<Object>();
        parameters.add(bean.getName());
        parameters.add(bean.getId());
        result=executeUpdate(query,parameters);
        try{
            conn.commit();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //TODO:待调试
    @Override
    public int updatePw(User bean) {
        int result=0;
        String query="update user set pw=? where id=?";
        ArrayList<Object> parameters=new ArrayList<Object>();
        parameters.add(bean.getPassword());
        parameters.add(bean.getId());
        result=executeUpdate(query,parameters);
        try{
            conn.commit();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
