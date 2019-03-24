package utils;



import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPUtil {
    private static Properties properties=new Properties();
    private static DataSource dataSource=null;

    static{
        try{
            File confFile=new File(PropertiesReader.class.getResource("/conf/dbcp.properties").getFile());
            InputStream in=new FileInputStream(confFile);
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //try{
        //    dataSource= BasicDataSourceFactory.createDataSource(properties);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    public static Connection getConnection(){
        if(dataSource==null){
            connect();
        }
        Connection conn=null;
        try{
            conn=dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{//TODO:选择是否自动提交
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void connect(){
        try{
            dataSource= BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
