package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class PropertiesReader {
    private static PropertiesReader instance=null;
    private ArrayList<Properties> list=null;

    private PropertiesReader(){
        //打开文件
        File file=new File(PropertiesReader.class.getResource("/conf").getFile());//相对路径//与该.java文件所在位置为准
        //System.out.println(file.exists());
        String fileList[]=file.list();
        list=new ArrayList<Properties>();
        for(int i=0;i<fileList.length;++i){
            String path=file.getPath()+"\\"+fileList[i];
            File tempFile=new File(path);
            //System.out.println(tempFile.exists());
            try{
                InputStream in = new FileInputStream(tempFile);
                Properties prop=new Properties();
                prop.load(in);
                list.add(prop);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static PropertiesReader getInstance(){
        if(instance==null){
            instance=new PropertiesReader();
        }
        return instance;
    }

    public String getProperty(String key){
        for(Properties prop:list){
            String value=prop.getProperty(key);
            if(value!=null)return value;
        }
        return null;
    }
}
