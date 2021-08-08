package ServletDemo;

import cn.database.UserService;
import cn.database.UserServiceImpl;
import cn.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String gender=request.getParameter("gender");
        String checkCode_actual = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        String checkCode_expected = (String) session.getAttribute("checkCode");
        session.removeAttribute("registerError");
        if(checkCode_actual==null||!checkCode_actual.equalsIgnoreCase(checkCode_expected)){
            session.setAttribute("registerError","验证码错误!");
            session.setAttribute("username",username);
            session.setAttribute("password",password);
            session.setAttribute("gender",gender);
            response.sendRedirect(request.getContextPath()+"/register.jsp");
            return;
        }
        UserService userService=new UserServiceImpl();
        boolean isAdded = userService.register(new User(-1, username, password, gender.equals("male") ? User.Gender.MALE: User.Gender.FEMALE));
        if(isAdded){
            session.setAttribute("registerResult","注册成功!");
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("gender");
            session.removeAttribute("registerError");
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else {
            session.setAttribute("registerResult","注册失败!");
            session.removeAttribute("registerError");
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }
}
