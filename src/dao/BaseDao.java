package dao;

import utils.DBCPUtil;
import java.sql.*;
import java.util.ArrayList;

public class BaseDao {
    protected Connection conn;
    protected PreparedStatement pstmt;
    protected ResultSet rs;

    protected ResultSet executeQuery(String query, ArrayList<Object> parameters){
        conn= DBCPUtil.getConnection();
        try{
            pstmt=conn.prepareStatement(query);
            if(parameters!=null&&parameters.size()>0){
                for(int i=0;i<parameters.size();++i){
                    pstmt.setObject(i+1,parameters.get(i));//i+1!!!!
                }
            }
            rs=pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //交由调用该方法的上一方法关闭连接，因为他还要查看结果！！！
        return rs;
    }
    protected int executeUpdate(String query,ArrayList<Object> parameters){
        int result=0;
        conn= DBCPUtil.getConnection();
        try{
            pstmt=conn.prepareStatement(query);
            if(parameters!=null&&parameters.size()>0){
                for(int i=0;i<parameters.size();++i){
                    pstmt.setObject(i+1,parameters.get(i));
                }
            }
            result=pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void close(){
        try{
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
                conn=null;//不设置为null的话conn还是会存在即使是关闭的
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
