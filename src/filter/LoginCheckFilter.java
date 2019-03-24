package filter;

import utils.CookieOperation;
import utils.PropertiesReader;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        HttpServletResponse response=(HttpServletResponse)servletResponse;
        HttpServletRequest request=(HttpServletRequest)servletRequest;//强制转换
        Cookie[] cookies=request.getCookies();
        int time=Integer.parseInt(PropertiesReader.getInstance().getProperty("timeForKeepingLogin"));
        if(cookies!=null){//最开始cookie没生成，会抛错误出来
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("zyq_token")){
                    String token=request.getSession().getId()+"";
                    if(token.equals(cookie.getValue())){
                        CookieOperation.setCookie(request,response,"zyq_loginFlag","1",time);
                        request.setAttribute("loginFlag",1);
                    }
                    else{
                        CookieOperation.setCookie(request,response,"zyq_loginFlag","0",time);
                        CookieOperation.deleteCookie(request,response,"zyq_token");//删除该"zyq_token"cookie
                        request.setAttribute("loginFlag",0);
                    }
                }
            }
        }
        //CookieOperation.setCookie(request,response,"zyq_loginFlag","0",time);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

}
