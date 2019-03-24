package servlet;

import bean.User;
import com.sun.xml.internal.bind.v2.TODO;
import service.UserManageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.catalina.servlets.DefaultServlet;
import utils.CookieOperation;
import utils.PropertiesReader;

//@WebServlet("/index.jsp/*")
public class LoginRegisterServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path="";//定义错误路径//"loginResponsePage.jsp"
        String status=req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        //截取了最后一个字符串
        if(status!=null){
            if("login".equals(status)){
                path=this.loginProcess(req,resp);
            }
            else if("register".equals(status)){
                path=this.registerProcess(req,resp);
            }
            else if("logout".equals(status)){
                //TODO
                path=logoutProcess(req,resp);
            }
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private String loginProcess(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String msg="";
        String url="";
        int status;
        String id=req.getParameter("id");
        String pw=req.getParameter("password");
        if(!"".equals(id)&&!"".equals(pw)){
            UserManageService userManageService=UserManageService.getInstance();
            User user=userManageService.login(Integer.parseInt(id),pw);
            boolean result=(user==null?false:true);
            if(result){
                msg="登陆成功";
                url="html/loginResponsePage.jsp";
                status=1;
                setToken(req, resp, user);
            }else{
                msg="id不正确或密码不正确";
                url="html/index.jsp";
                status=0;
                clearToken(req, resp);
            }
        }else{//再次判断id或密码是否为空
            msg="id或密码不能为空";
            url="html/index.jsp";
            status=0;
            clearToken(req, resp);
        }

        req.setAttribute("msg",msg);
        req.setAttribute("url",url);
        req.setAttribute("status",status);
        return url;
    }

    private String registerProcess(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //TODO:不断刷新则可不断注册
        String msg="";
        String url="";
        int status;
        String name=req.getParameter("name");
        String pw=req.getParameter("password");
        User user;
        if(!"".equals(name)&&!"".equals(pw)){
            UserManageService userManageService=UserManageService.getInstance();
            user=userManageService.register(name,pw);
            boolean result=(user==null?false:true);
            if(result){
                msg="注册成功";
                url="html/registerResponsePage.jsp";
                status=1;
                setToken(req, resp, user);
                req.setAttribute("id",user.getId());
                req.setAttribute("name",user.getName());
            }else{
                msg="注册失败";
                url="html/index.jsp";
                status=0;
                clearToken(req, resp);
            }
        }else{//再次判断id或密码是否为空
            msg="姓名或密码不能为空";
            url="html/registerResponsePage.jsp";
            status=0;
            clearToken(req, resp);
        }
        req.setAttribute("msg",msg);
        req.setAttribute("url",url);
        req.setAttribute("status",status);
        return url;
    }

    private String logoutProcess(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String msg="登出成功";
        String url="html/index.jsp";
        int status=0;
        clearToken(req,resp);
        req.setAttribute("msg",msg);
        req.setAttribute("url",url);
        req.setAttribute("status",status);
        return url;
    }

    private void setToken(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HttpSession session=req.getSession();
        session.setAttribute("user",user);
        int time=Integer.parseInt(PropertiesReader.getInstance().getProperty("timeForKeepingLogin"));
        session.setMaxInactiveInterval(time);
        String token=session.getId()+"";
        CookieOperation.setCookie(req,resp,"zyq_token",token,time);
    }

    private void clearToken(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO:有问题
        HttpSession session=req.getSession();
        session.invalidate();//清除session
        CookieOperation.deleteCookie(req,resp,"zyq_token");
        int time=Integer.parseInt(PropertiesReader.getInstance().getProperty("timeForKeepingLogin"));
        CookieOperation.setCookie(req,resp,"zyq_loginFlag","0",time);
    }
}
