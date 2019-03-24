package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieOperation {
    //TODO:均没有考虑到同域的问题
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int time){
        setCookie(request, response, name, value, time,"/");
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int time, String path){
        //deleteCookie(request,response,name,path);
        Cookie newCookie=new Cookie(name,value);
        newCookie.setPath(path);
        newCookie.setMaxAge(time);
        response.addCookie(newCookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
        deleteCookie(request, response, name,"/");
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name,String path){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                //TODO:get不到path，应该判断path是否相同才对
                if(cookie.getName().equals(name)){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
}
