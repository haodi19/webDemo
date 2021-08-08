package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/showUsersServlet")
public class ShowUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService=new UserServiceImpl();
        List<User> allUsers = userService.getAllUsers();
        HttpSession session = request.getSession();
        session.setAttribute("allUsers",allUsers);
        String referer = request.getHeader("referer");
        if(referer!=null){
            if(referer.contains("/login.jsp")){
                User user = (User) request.getAttribute("user");
                session.setAttribute("loginUser",user);
            }
        }
        response.sendRedirect(request.getContextPath()+"/homepage.jsp");
    }
}
