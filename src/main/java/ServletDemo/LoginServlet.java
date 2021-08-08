package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String checkCode_actual = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        String checkCode_expected = (String) session.getAttribute("checkCode");
        session.removeAttribute("checkCode");
        if(checkCode_actual==null||!checkCode_actual.equalsIgnoreCase(checkCode_expected)){
            session.setAttribute("loginError","验证码错误!");
            session.setAttribute("login_username",username);
            session.setAttribute("login_password",password);
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        UserService userService=new UserServiceImpl();
        User user = userService.login(new User(-1, username, password, null));
        if(user==null){
            session.setAttribute("loginError","用户名或密码错误!");
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else {
            request.setAttribute("user",user);
            session.removeAttribute("login_username");
            session.removeAttribute("login_password");
            request.getRequestDispatcher("/showUsersWithPagingServlet?currentPage=1&itemsPerPage=5").forward(request,response);
        }
    }
}
